package listener;

import additionalwindows.CustomFrame;
import additionalwindows.PreferenceWindow;
import additionalwindows.PreviewWindow;
import parameter.Values;
import ui.UI;
import ui.Window;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class Listener extends KeyAdapter implements ActionListener, ChangeListener, MouseListener,MouseMotionListener {

    public UI ui;
    public JFileChooser fc;
    public PreviewWindow imageWindow;
    public CustomFrame cf;
    public PreferenceWindow pw;

    public int x,y;

    Window window;

    public Listener(Window window){
        this.window = window;
        fc = new JFileChooser(".");
        initWindows();
        registerComponentListener();
    }

    public void initWindows(){
        ui = new UI(window);
        cf = new CustomFrame();
        imageWindow = new PreviewWindow();
        pw = new PreferenceWindow();
    }

    public void registerComponentListener(){
        //ui
        ui.newCapture.addActionListener(this);
        ui.open.addActionListener(this);
        ui.snapIn3.addActionListener(this);
        ui.snapIn5.addActionListener(this);
        ui.exit.addActionListener(this);
        ui.preference.addActionListener(this);
        ui.custom.addActionListener(this);
        ui.contCapture.addActionListener(this);
        ui.capture.addActionListener(this);
        ui.fullscreen.addActionListener(this);
        ui.duration.addKeyListener(this);
        ui.fps.addChangeListener(this);

        //customFrame
        cf.addMouseListener(this);
        cf.addMouseMotionListener(this);

        //preferenceWindow
        pw.changeDefaultSavePathButton.addActionListener(this);
        pw.alwaysOnTop.addActionListener(this);
        pw.defaultSavePath.addKeyListener(this);
        pw.delay.addChangeListener(this);
        pw.lookAndFeel.addActionListener(this);
        pw.format.addActionListener(this);
    }

    public void show(){
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == ui.contCapture){
            ui.conCaptDetails(ui.contCapture.isSelected());
        }else if(source == ui.fullscreen || source == ui.custom){
            Values.fullscreen = ui.fullscreen.isSelected();
            cf.setVisible(!Values.fullscreen);
        }else if(source == ui.open){
            fc.setDialogTitle("Open");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = fc.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                imageWindow.show(fc.getSelectedFile().getAbsolutePath());
            }
        }else if(source == ui.preference){
            pw.setVisible(true);
        }else if(source == ui.exit){
            System.exit(0);
        }





        if(source == pw.changeDefaultSavePathButton){
            fc.setDialogTitle("Save");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int response = fc.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                Values.defaultLocation = fc.getSelectedFile().getAbsolutePath();
                pw.defaultSavePath.setText(Values.defaultLocation);
            }
        }else if(source == pw.lookAndFeel){
            Values.theme = pw.lookAndFeel.getSelectedIndex();
            System.out.println(Values.theme);
        }else if(source == pw.format){
            Values.format = pw.format.getSelectedIndex();
        }
    }

    @Override
    public void keyTyped(KeyEvent ke){
       char c = ke.getKeyChar();
       boolean valid = (ui.duration.getText().length()<3&&(Character.isDigit(c))) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_UP || c == KeyEvent.VK_DOWN || c == KeyEvent.VK_RIGHT || c == KeyEvent.VK_LEFT;
       if(!valid){
            ke.consume();
       }
       if(ui.duration.getText().length() == 0||Integer.parseInt(ui.duration.getText())<5){
           ui.duration.setText("5");
       }
       Values.duration = Integer.parseInt(ui.duration.getText());
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        Object source = ce.getSource();
        if(source == ui.fps){
            Values.fps = (int)ui.fps.getValue();
        }else if(source == pw.delay){
            Values.delay = (int)pw.delay.getValue();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == cf){
            x = e.getX();
            y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getSource() == cf && !cf.resizer.isResizeCursor){
            cf.setLocation(e.getXOnScreen() - x, e.getYOnScreen() -y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
