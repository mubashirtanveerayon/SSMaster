package additionalwindows;

import parameter.Values;

import javax.swing.*;
import java.io.File;

public class PreviewWindow extends JFrame {

    JLabel label;

    public PreviewWindow(){
        setIconImage(Values.LOGO.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        label = new JLabel();
        add(label);
    }

    public void show(String path)
    {
        File file = new File(path);
        setTitle(file.getName());
        label.setIcon(new ImageIcon(path));
        pack();
        setVisible(true);
    }

}
