package additionalwindows;

import componentresizer.ComponentResizer;
import listener.Listener;
import parameter.Values;

import javax.swing.*;
import java.awt.*;

public class CustomFrame extends JFrame {

    public ComponentResizer resizer;

    public CustomFrame(){
        setUndecorated(true);
        setCursor(new Cursor(Cursor.MOVE_CURSOR));

        setBounds(Values.customFrame[0], Values.customFrame[1], Values.customFrame[2], Values.customFrame[3]);
        setOpacity(0.7f);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.red));

        resizer = new ComponentResizer();

         add(label);

        resizer.registerComponent(this);
    }

}
