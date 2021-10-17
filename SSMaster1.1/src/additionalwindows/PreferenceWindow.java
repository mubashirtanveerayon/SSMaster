package additionalwindows;

import parameter.Values;

import javax.swing.*;
import java.awt.*;

public class PreferenceWindow extends JFrame{

    String formats[];
    String themes[];
    String titles[];
    JLabel labels[];
    public JSpinner delay;
    public JTextField defaultSavePath;
    public JButton changeDefaultSavePathButton;
    public JCheckBox alwaysOnTop;
    public JComboBox lookAndFeel,format;

    public PreferenceWindow(){
        super("Preference");
        setDefaultCloseOperation(0);
        setBounds(Values.SCREEN_SIZE.width/2-370/2,Values.SCREEN_SIZE.height/2-350/2,370,350);
        setLayout(null);
        setResizable(false);
        initComponents();
    }

    public void initComponents(){
        titles = new String[]{"Delay :","Save Location :","Look and feel :","Save as :"};
        labels = new JLabel[titles.length];
        Font font = new Font("Arial",Font.BOLD,13);
        int x = 30,y = 50;
        for(int i=0;i<labels.length;i++){
            labels[i] = new JLabel(titles[i]);
            labels[i].setFont(font);
            labels[i].setBounds(x,y*i+y,100,20);
            add(labels[i]);
        }

        SpinnerNumberModel value = new SpinnerNumberModel(Values.delay,0,300,1);
        delay = new JSpinner(value);
        delay.setFont(font);
        delay.setBounds(150,y,45,20);
        ((JSpinner.DefaultEditor) delay.getEditor()).getTextField().setEditable(false);

        defaultSavePath = new JTextField(Values.defaultLocation);
        defaultSavePath.setBounds(150,y*2,170,20);
        defaultSavePath.setHorizontalAlignment(JTextField.CENTER);
        defaultSavePath.setFont(font);

        changeDefaultSavePathButton = new JButton("...");
        changeDefaultSavePathButton.setBounds(325,y*2,20,20);
        changeDefaultSavePathButton.setFocusable(false);

        themes = new String[]{"White","Dark"};
        lookAndFeel = new JComboBox(themes);
        lookAndFeel.setSelectedIndex(Values.theme);
        lookAndFeel.setFont(font);
        lookAndFeel.setBounds(150,y*3,80,20);

        formats = new String[]{"PNG","JPG"};
        format = new JComboBox(formats);
        format.setSelectedIndex(Values.format);
        format.setFont(font);
        format.setBounds(150,y*4,80,20);

        alwaysOnTop = new JCheckBox("Always On Top");
        alwaysOnTop.setFont(font);
        alwaysOnTop.setSelected(Values.alwaysOnTop);
        alwaysOnTop.setBounds(x,y*5,120,17);
        alwaysOnTop.setFocusable(false);

        add(alwaysOnTop);
        add(defaultSavePath);
        add(delay);
        add(changeDefaultSavePathButton);
        add(lookAndFeel);
        add(format);
    }

}
