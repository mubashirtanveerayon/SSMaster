package capture;

import parameter.Values;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class Capture implements Runnable{

    public static BufferedImage captureScreenshot(int x, int y, int w, int h){
        try{
            return new Robot().createScreenCapture(new Rectangle(x,y,w,h));
        }catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }

    public static BufferedImage captureScreenshot(){
        return captureScreenshot(Values.SCREEN_SIZE.x,Values.SCREEN_SIZE.y,Values.SCREEN_SIZE.width,Values.SCREEN_SIZE.height);
    }

    @Override
    public void run() {
        try{
            sleep(Values.delay*1000);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}
