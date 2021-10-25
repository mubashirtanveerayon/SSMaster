package additionalwindows;

import parameter.Values;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URL;

public class About extends MouseAdapter {

    public JLabel label1,label2,imgLabel,linkLabel;

    public static final int WIDTH = 430;
    public static final int HEIGHT = 245;


    public JFrame window;

    public About(){
        window = new JFrame("About");
        window.setIconImage(Values.LOGO.getImage());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setBounds(Values.SCREEN_SIZE.width/2-WIDTH/2,Values.SCREEN_SIZE.height/2-HEIGHT/2,WIDTH,HEIGHT);
        window.setLayout(null);
        window.setResizable(false);
        window.getContentPane().setBackground(Color.white);
        initComponents();
    }

    public void initComponents(){
        label1 = new JLabel("<html><font size=\"10\">SSMaster</font><br><font size=\"6\">version 1.1 (beta)</font></html>");
        label1.setBounds(20,0,210,85);
        label1.setForeground(Color.black);

        label2 = new JLabel("<html><font size=5>Created by Ayon</font><br><font size=4>Released on 29/10/2021</font>");
        label2.setBounds(20,130,180,50);
        label2.setForeground(Color.black);

        imgLabel = new JLabel(Values.LOGO);
        imgLabel.setBounds(200,0,Values.LOGO.getIconWidth(),Values.LOGO.getIconHeight());
        imgLabel.setToolTipText("Special thanks to AKid for the logo!");

        linkLabel = new JLabel("Source code!");
        linkLabel.setBounds(20,95,130,20);
        linkLabel.setFont(new Font("Monospaced",Font.PLAIN,17));
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));//new Cursor(Cursor.HAND_CURSOR)
        linkLabel.setBorder(BorderFactory.createLineBorder(Color.white));
        linkLabel.addMouseListener(this);

        window.add(label1);
        window.add(label2);
        window.add(imgLabel);
        window.add(linkLabel);
    }

    @Override
    public void mouseClicked(MouseEvent me){
        if(me.getSource() == linkLabel){
            try {
                Desktop.getDesktop().browse(new URI("https://www.github.com/mubashirtanveerayon/SSMasterv1.1"));
            }catch(Exception ex){
                System.out.println(ex);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me){
        if(me.getSource() == linkLabel){
            linkLabel.setBorder(BorderFactory.createLineBorder(Color.blue));
        }
    }

    @Override
    public void mouseExited(MouseEvent me){
        if(me.getSource() == linkLabel){
            linkLabel.setBorder(BorderFactory.createLineBorder(Color.white));
        }
    }

    public void show(){
        if(!window.isVisible()){
            window.setVisible(true);
        }
    }

}
