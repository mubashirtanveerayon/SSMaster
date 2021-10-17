package ui;

import additionalwindows.CustomFrame;
import componentresizer.ComponentResizer;
import listener.Listener;
import parameter.Values;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class UI {

    public static final int CAPTBTN_NORMAL_Y = 130;

    public static final int CAPTBTN_CON_CAPT_Y = 160;

    public JFrame window;

    public JMenuBar menuBar;

    public JMenu file, quickSnap,options;

    public JMenuItem newCapture,open, snapIn3, snapIn5,preference,exit;

    public JButton capture;

    ButtonGroup btngrp;

    public JRadioButton fullscreen,custom;

    public JCheckBox contCapture;

    public JSpinner fps;

    public JTextField duration;

    public JLabel fpsLabel,durationLabel;

    public UI(JFrame window_){
        window = window_;
        initComponents();
        addComponents();
        conCaptDetails(false);
    }

    public void initComponents(){
        menuBar = new JMenuBar();

        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);//alt + f to open file menu
        quickSnap = new JMenu("Quick-Capture");
        quickSnap.setMnemonic(KeyEvent.VK_Q); //alt + q to open quicksnap menu
        options = new JMenu("Options");

        //file
        newCapture = new JMenuItem("Capture");
        newCapture.setMnemonic(KeyEvent.VK_Z); // z to capture
        open = new JMenuItem("Open");
        exit = new JMenuItem("Exit");

        //edit
        snapIn3 = new JMenuItem("Capture in 3 seconds"); // a to capture in 3
        snapIn5 = new JMenuItem("Capture in 5 seconds"); // s to capture in 5
        snapIn3.setMnemonic(KeyEvent.VK_A);
        snapIn5.setMnemonic(KeyEvent.VK_S);

        //options
        preference  = new JMenuItem("Preference");

        fullscreen = new JRadioButton("Fullscreen");
        custom = new JRadioButton("Custom");

        Font font = new Font("Arial",Font.BOLD,15);

        fullscreen.setBounds(50,30,120,30);
        fullscreen.setFont(font);
        fullscreen.setFocusable(false);
        if(Values.fullscreen){
            fullscreen.setSelected(true);
        }else{
            custom.setSelected(true);
        }

        custom.setBounds(175,30,120,30);
        custom.setFont(font);
        custom.setFocusable(false);
        btngrp = new ButtonGroup();

        contCapture = new JCheckBox("Continuous capture");
        contCapture.setBounds(50,70,190,30);
        contCapture.setFont(font);
        contCapture.setFocusable(false);
        contCapture.setSelected(Values.continuous);

        SpinnerNumberModel sModel = new SpinnerNumberModel(Values.fps,1,60,1);
        fps = new JSpinner(sModel);
        fps.setFont(font);
        ((JSpinner.DefaultEditor) fps.getEditor()).getTextField().setEditable(false);
        fps.setSize(50,20);

        duration = new JTextField(String.valueOf(Values.duration));
        duration.setFont(font);
        duration.setSize(60,20);
        duration.setHorizontalAlignment(JTextField.CENTER);
        duration.setToolTipText("in seconds");

        fpsLabel = new JLabel("FPS :");
        fpsLabel.setFont(font);
        fpsLabel.setSize(60,20);

        durationLabel = new JLabel("Duration :");
        durationLabel.setFont(font);
        durationLabel.setSize(100,20);

        fps.setLocation(80,120);
        duration.setLocation(215,120);
        fpsLabel.setLocation(30,120);
        durationLabel.setLocation(135,120);

        capture = new JButton("Capture");
        capture.setBounds(80,CAPTBTN_NORMAL_Y,100,30);
        capture.setFont(font);
        capture.setFocusable(false);

    }

    public void addComponents(){
        btngrp.add(fullscreen);
        btngrp.add(custom);

        file.add(newCapture);
        file.add(open);
        file.add(exit);
        quickSnap.add(snapIn3);
        quickSnap.add(snapIn5);
        options.add(preference);

        menuBar.add(file);
        menuBar.add(quickSnap);
        menuBar.add(options);

        window.setJMenuBar(menuBar);
        window.add(fullscreen);
        window.add(custom);
        window.add(contCapture);
        window.add(capture);
        window.add(fps);
        window.add(duration);
        window.add(fpsLabel);
        window.add(durationLabel);
    }

    public void conCaptDetails(boolean show){
        if(show){
            window.setSize(window.getWidth(),Window.CON_CAPT_HEIGHT);
            capture.setLocation(capture.getWidth(),CAPTBTN_CON_CAPT_Y);
            capture.setText("Start");
        }else{
            window.setSize(window.getWidth(),Window.NORMAL_HEIGHT);
            capture.setLocation(capture.getWidth(),CAPTBTN_NORMAL_Y);
            capture.setText("Capture");
        }
        fps.setVisible(show);
        duration.setVisible(show);
        fpsLabel.setVisible(show);
        durationLabel.setVisible(show);
    }

}
