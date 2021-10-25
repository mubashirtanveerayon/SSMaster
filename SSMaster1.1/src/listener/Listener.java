package listener;

import additionalwindows.*;
import capture.Capture;
import io.IO;
import parameter.Values;
import ui.UI;
import ui.Window;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Listener extends KeyAdapter implements ActionListener, ChangeListener, MouseListener,MouseMotionListener,WindowListener {

    public UI ui;
    public JFileChooser fc;
    public CustomFrame cf;
    public PreferenceWindow pw;
    public About about;

    public Capture capture;

    public int x,y;

    public Window window;

    public boolean isPrefWindowVisible;

    IO io;

    public Loader loader;

    public Listener(Window window, IO io){
        this.window = window;
        this.io = io;
        fc = new JFileChooser(".");
        capture = new Capture(this);
        isPrefWindowVisible = false;
        initWindows();
        registerComponentListener();
        changeTheme();
    }

    public void initWindows(){
        ui = new UI(window);
        cf = new CustomFrame();
        pw = new PreferenceWindow();
        loader = new Loader(capture);
        about = new About();
    }

    public void registerComponentListener(){
        //window
        window.addWindowListener(this);
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
        ui.about.addActionListener(this);

        //customFrame
        cf.addMouseListener(this);
        cf.addMouseMotionListener(this);

        //preferenceWindow
        pw.changeDefaultSavePathButton.addActionListener(this);
        pw.alwaysOnTop.addActionListener(this);
        pw.defaultSavePath.addKeyListener(this);
        pw.delay.addChangeListener(this);
        pw.theme.addActionListener(this);
        pw.format.addActionListener(this);
        pw.openFile.addActionListener(this);
        pw.confirm.addActionListener(this);
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
        Values.theme = pw.theme.getSelectedIndex();
        Values.format = pw.format.getSelectedIndex();
        Values.alwaysOnTop = pw.alwaysOnTop.isSelected();
        Values.fps = (int)ui.fps.getValue();
        Values.duration = Integer.parseInt(ui.duration.getText());
        Values.openAfterCapture = pw.openFile.isSelected();
        Values.continuous = ui.contCapture.isSelected();
        cf.setVisible(!Values.fullscreen);
        changeTheme();
    }

    public Thread continuousCapture(){
        isPrefWindowVisible = pw.isVisible();
        window.setVisible(false);
        pw.setVisible(false);
        cf.setVisible(false);
        return new Thread(capture);
    }

    public void exit(){
        fetchData();
        io.write();
        System.exit(0);
    }

    public void changeTheme(){
        cf.setAlwaysOnTop(Values.alwaysOnTop);
        window.setAlwaysOnTop(Values.alwaysOnTop);
        pw.setAlwaysOnTop(Values.alwaysOnTop);
        about.window.setAlwaysOnTop(Values.alwaysOnTop);
        ui.conCaptDetails(Values.continuous);
        if(Values.theme == 0){
            window.getContentPane().setBackground(null);
            ui.menuBar.setForeground(null);
            ui.menuBar.setBackground(null);
            ui.file.setForeground(Color.black);
            ui.quickSnap.setForeground(Color.black);
            ui.options.setForeground(Color.black);
            ui.custom.setForeground(null);
            ui.custom.setBackground(null);
            ui.fullscreen.setForeground(null);
            ui.fullscreen.setBackground(null);
            ui.contCapture.setForeground(null);
            ui.contCapture.setBackground(null);
            ui.fpsLabel.setForeground(null);
            ui.durationLabel.setForeground(null);
            ui.fpsLabel.setBackground(null);
            ui.durationLabel.setBackground(null);
            pw.getContentPane().setBackground(null);
            for(JLabel label :pw.labels){
                label.setForeground(null);
            }
            pw.alwaysOnTop.setBackground(null);
            pw.alwaysOnTop.setForeground(null);
            pw.openFile.setBackground(null);
            pw.openFile.setForeground(null);
            about.window.getContentPane().setBackground(Color.white);
            about.label1.setForeground(null);
            about.label2.setForeground(null);
            about.linkLabel.setForeground(null);
        }else{
            window.getContentPane().setBackground(Values.DARK);
            ui.menuBar.setForeground(Color.white);
            ui.menuBar.setBackground(Values.DARK);
            ui.file.setForeground(Color.white);
            ui.quickSnap.setForeground(Color.white);
            ui.options.setForeground(Color.white);
            ui.custom.setForeground(Color.white);
            ui.custom.setBackground(Values.DARK);
            ui.fullscreen.setForeground(Color.white);
            ui.fullscreen.setBackground(Values.DARK);
            ui.contCapture.setForeground(Color.white);
            ui.contCapture.setBackground(Values.DARK);
            ui.fpsLabel.setForeground(Color.white);
            ui.durationLabel.setForeground(Color.white);
            ui.fpsLabel.setBackground(Values.DARK);
            ui.durationLabel.setBackground(Values.DARK);
            pw.getContentPane().setBackground(Values.DARK);
            for(JLabel label :pw.labels){
                label.setForeground(Color.white);
            }
            pw.alwaysOnTop.setBackground(Values.DARK);
            pw.alwaysOnTop.setForeground(Color.white);
            pw.openFile.setBackground(Values.DARK);
            pw.openFile.setForeground(Color.white);
            about.window.getContentPane().setBackground(Values.DARK);
            about.label1.setForeground(Color.white);
            about.label2.setForeground(Color.white);
            about.linkLabel.setForeground(Color.white);
        }
    }

    public Thread capture(){
        fetchData();
        Values.continuous = false;
        isPrefWindowVisible = pw.isVisible();
        pw.setVisible(false);
        window.setVisible(false);
        cf.setVisible(false);
        Thread thread = new Thread(){
            public void run() {
                try {
                    Thread.sleep(Values.DEFAULT_DELAY+Values.delay * 1000L);
                }catch(Exception ex){
                    System.out.println(ex);
                }
                if(Values.fullscreen) {
                    IO.saveImage(Capture.captureScreenshot());
                }else{
                    IO.saveImage(Capture.captureScreenshot(Values.customFrame));
                }
                Values.continuous = ui.contCapture.isSelected();
                show();
            }
        };
        return thread;
    }

    public Thread capture(int qc){
        fetchData();
        Values.continuous = false;
        isPrefWindowVisible = pw.isVisible();
        pw.setVisible(false);
        window.setVisible(false);
        cf.setVisible(false);
        Thread thread = new Thread(){
            public void run() {
                try {
                    Thread.sleep(Values.DEFAULT_DELAY+qc * 1000L);
                }catch(Exception ex){
                    System.out.println(ex);
                }
                if(Values.fullscreen) {
                    IO.saveImage(Capture.captureScreenshot());
                }else{
                    IO.saveImage(Capture.captureScreenshot(Values.customFrame));
                }
                Values.continuous = ui.contCapture.isSelected();
                show();
            }
        };
        return thread;
    }

    public void show(){
        cf.setVisible(!Values.fullscreen);
        pw.setVisible(isPrefWindowVisible);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == ui.open){
            fc.setDialogTitle("Open");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int response = fc.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                if(Values.previewWindow.isVisible()){
                    Values.previewWindow.dispose();
                }
                Values.previewWindow.show(fc.getSelectedFile().getAbsolutePath());
            }
        }else if(source == ui.preference){
            pw.setVisible(true);
            isPrefWindowVisible = true;
        }else if(source == ui.capture) {
            fetchData();
            if(ui.contCapture.isSelected()){
                continuousCapture().start();
                Thread thread = new Thread(loader);
                thread.start();
                capture.totalFrames = Values.fps*Values.duration;
            }else{
                capture().start();
            }
        }else if(source == ui.exit){
            exit();
        }else if(source == ui.newCapture){
            capture().start();
        }else if(source == ui.snapIn3){
            capture(3).start();
        }else if(source == ui.snapIn5){
            capture(5).start();
        }else if(source == pw.changeDefaultSavePathButton){
            fc.setDialogTitle("Save");
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int response = fc.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                Values.defaultLocation = fc.getSelectedFile().getAbsolutePath() + File.separator;
                pw.defaultSavePath.setText(Values.defaultLocation);
            }
        }else if(source == ui.contCapture || source == ui.fullscreen || source == ui.custom||source == pw.theme || source == pw.format || source == pw.alwaysOnTop || source == pw.openFile){
            fetchData();
        }else if(source == ui.about){
            about.show();
        }else if(source == pw.confirm){
            pw.setVisible(false);
            isPrefWindowVisible = false;
            fetchData();
            io.write();
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
            changeTheme();
            pw.setVisible(false);
            isPrefWindowVisible = false;
        }else if(e.getSource() == window){
            exit();
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
