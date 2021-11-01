package additionalwindows;

import capture.Capture;

import parameter.Values;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Loader extends JFrame implements Runnable{

    public JLabel label;
    public JProgressBar progressBar;
    public JButton stopbtn;

    public Capture capture;
    public static boolean stop = false;

    public Loader(Capture capture_){
        super("Capturing screenshot...");
        setIconImage(Values.LOGO.getImage());
        setResizable(false);
        setBounds(Values.SCREEN_SIZE.width/2-310/2,Values.SCREEN_SIZE.height/2-200/2,310,200);
        setDefaultCloseOperation(0);
        setLayout(null);
        capture = capture_;
        initComponents();
    }

    public void initComponents(){
        Font font = new Font("Monospaced",Font.PLAIN,15);
        label = new JLabel("Capturing...");
        progressBar = new JProgressBar();
        stopbtn = new JButton("Stop");

        stopbtn.setFont(font);
        stopbtn.setBounds(120,120,70,25);
        stopbtn.setFocusable(false);
        stopbtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                stop = true;
            }
        });

        label.setBounds(10,20,280,20);
        label.setFont(font);

        progressBar.setBounds(10,60,280,30);
        progressBar.setFont(font);
        progressBar.setForeground(new Color(55, 151, 55));
        progressBar.setStringPainted(true);
        progressBar.setValue(50);

        add(label);
        add(progressBar);
        add(stopbtn);
    }

    @Override
    public void run(){
        setState(Frame.ICONIFIED);
        setVisible(true);
        stop = false;
        while(progressBar.getValue()<=100 && !stop){
            label.setText("Capturing... "+String.valueOf(capture.completed)+"/"+String.valueOf(capture.totalFrames));
            float value = ((float)capture.completed/(float)capture.totalFrames) * 100f;
            progressBar.setValue((int)value);
            progressBar.setString(String.valueOf((int)value)+"%");
        }
        setVisible(false);
    }

}
