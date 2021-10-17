package capture;

import io.IO;
import parameter.Values;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class Capture implements Runnable{

    public static BufferedImage captureScreenshot(int[] frame){
        int x = frame[0],y = frame[1],w = frame[2],h = frame[3];
        try{
            return new Robot().createScreenCapture(new Rectangle(x,y,w,h));
        }catch(Exception ex){
            System.out.println(ex);
            return null;
        }
    }

    public static BufferedImage captureScreenshot(){
        int[] rect = new int[]{Values.SCREEN_SIZE.x,Values.SCREEN_SIZE.y,Values.SCREEN_SIZE.width,Values.SCREEN_SIZE.height};
        return captureScreenshot(rect);
    }

    @Override
    public void run() {
        try{
            sleep(Values.delay*1000);
            if(Values.fullscreen){
                IO.saveImage(captureScreenshot());
            }else{
                IO.saveImage(captureScreenshot(Values.customFrame));
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}
