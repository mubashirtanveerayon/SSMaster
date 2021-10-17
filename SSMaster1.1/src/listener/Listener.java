package listener;

import additionalwindows.CustomFrame;
import additionalwindows.PreferenceWindow;
import additionalwindows.PreviewWindow;
import capture.Capture;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.IO;
import parameter.Values;
import ui.UI;
import ui.Window;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.io.File;

public class Listener extends KeyAdapter implements ActionListener, ChangeListener, MouseListener,MouseMotionListener,WindowListener {

    public UI ui;
    public JFileChooser fc;
    public PreviewWindow imageWindow;
    public CustomFrame cf;
    public PreferenceWindow pw;

    public int x,y;

    Window window;
    IO io;

    public Listener(Window window, IO io){
        this.window = window;
        this.io = io;
        fc = new JFileChooser(".");
        initWindows();
        registerComponentListener();
        if(!Values.fullscreen){
            cf.setVisible(true);
        }
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
        pw.addWindowListener(this);
    }

    public void fetchData(){
        Values.fullscreen = ui.fullscreen.isSelected();
        Values.customFrame[0] = cf.getX();
        Values.customFrame[1] = cf.getY();
        Values.customFrame[2] = cf.getWidth();
        Values.customFrame[3] = cf.getHeight();
        Values.delay = (int)(pw.delay.getValue());
        Values.defaultLocation = pw.defaultSavePath.getText();
        Values.theme = pw.lookAndFeel.getSelectedIndex();
        Values.format = pw.format.getSelectedIndex();
        Values.alwaysOnTop = pw.alwaysOnTop.isSelected();
        Values.fps = (int)ui.fps.getValue();
        Values.duration = Integer.parseInt(ui.duration.getText());
    }

    public void capture(){
        boolean isPrefWindowVisible = pw.isVisible();
        window.setVisible(false);
        pw.setVisible(false);
        cf.setVisible(false);
        Capture capture = new Capture();
        Thread thread = new Thread(capture);
        thread.start();
        try{
            thread.join();
        }catch(Exception ex){
            System.out.println(ex);
        }
        if(!Values.fullscreen){
            cf.setVisible(true);
        }
        if(isPrefWindowVisible){
            pw.setVisible(true);
        }
        window.setVisible(true);
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
            fetchData();
        }else if(source == ui.open){
            fc.setDialogTitle("Open");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = fc.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                imageWindow.show(fc.getSelectedFile().getAbsolutePath());
            }
        }else if(source == ui.preference){
            pw.setVisible(true);
        }else if(source == ui.capture) {
            fetchData();
            capture();
        }else if(source == ui.exit){
            System.exit(0);
        }else if(source == ui.newCapture){
            fetchData();
            capture();
        }else if(source == ui.snapIn3){

        }else if(source == ui.snapIn5){

        }





        if(source == pw.changeDefaultSavePathButton){
            fc.setDialogTitle("Save");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int response = fc.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                Values.defaultLocation = fc.getSelectedFile().getAbsolutePath() + File.separator;
                pw.defaultSavePath.setText(Values.defaultLocation);
            }
        }else if(source == pw.lookAndFeel){
            Values.theme = pw.lookAndFeel.getSelectedIndex();
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

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(e.getSource() == pw){
            fetchData();
            io.write();
            pw.setVisible(false);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
