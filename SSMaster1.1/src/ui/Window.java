package ui;

import io.IO;
import listener.Listener;
import parameter.Values;

import javax.swing.JFrame;

public class Window extends JFrame {


    public static final int NORMAL_HEIGHT = 240;

    public static final int CON_CAPT_HEIGHT = 270;

    public Window(){
        super(Values.TITLE);
        setIconImage(Values.LOGO.getImage());
        setBounds(Values.SCREEN_SIZE.width/2-300/2,Values.SCREEN_SIZE.height/2-NORMAL_HEIGHT/2,300, NORMAL_HEIGHT);
        setDefaultCloseOperation(0);
        setLayout(null);
        setResizable(false);
    }

    public static void main(String[] args){
        IO io = new IO();
        io.read();
        Window window = new Window();
        Listener listener = new Listener(window,io);
        listener.show();
    }

}
