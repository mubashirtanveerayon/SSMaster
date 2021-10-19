package additionalwindows;

import javax.swing.*;
import java.awt.*;

public class About extends JFrame {

    public JPanel panel;

    public JLabel label;

    public About(){
        super("About");
        initComponents();
    }

    public void initComponents(){
        panel = new JPanel(new BorderLayout());
        
    }

}
