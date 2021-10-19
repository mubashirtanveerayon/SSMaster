package additionalwindows;

import componentresizer.ComponentResizer;
import parameter.Values;

import javax.swing.*;
import java.awt.*;

public class CustomFrame extends JFrame {

    public ComponentResizer resizer;

    public CustomFrame(){
        setIconImage(Values.LOGO.getImage());
        setUndecorated(true);
        setCursor(new Cursor(Cursor.MOVE_CURSOR));

        setBounds(Values.customFrame[0], Values.customFrame[1], Values.customFrame[2], Values.customFrame[3]);
        try {
            setOpacity(0.7f);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.red));

        resizer = new ComponentResizer();

         add(label);

        resizer.registerComponent(this);
    }

}
