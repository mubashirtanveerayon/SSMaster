package capture;

import io.IO;
import listener.Listener;
import parameter.Values;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class Capture implements Runnable{

    public Listener listener;
    public long totalFrames;
    public long completed;
    public int qc;
    public boolean isPrefWindowVisible;

    public Capture(Listener listener){
        this.listener = listener;
        completed = 0;
        totalFrames = 0;
        isPrefWindowVisible = false;
        qc = 0;
    }

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
        int prevDelay = Values.delay;
        if(qc!=0){
            Values.delay = qc;
        }
        try{
            sleep(Values.DEFAULT_DELAY+Values.delay*1000L);
        }catch(Exception ex){
            System.out.println(ex);
        }
        if(Values.continuous){
            completed = 0;
            while(completed<totalFrames){
                try{
                    sleep((long)1000f/Values.fps);
                }catch(Exception ex){
                    System.out.println(ex);
                }
                if(Values.fullscreen){
                    IO.saveImage(captureScreenshot());
                }else{
                    IO.saveImage(captureScreenshot(Values.customFrame));
                }
                completed ++;
            }
            if(isPrefWindowVisible){
                listener.pw.setVisible(true);
            }
            listener.cf.setVisible(!Values.fullscreen);
            listener.window.setVisible(true);
        }else{
            if(Values.fullscreen){
                IO.saveImage(captureScreenshot());
            }else{
                IO.saveImage(captureScreenshot(Values.customFrame));
            }
        }
        if(isPrefWindowVisible){
            listener.pw.setVisible(true);
        }
        listener.cf.setVisible(!Values.fullscreen);
        listener.window.setVisible(true);
        completed = 0;
        totalFrames = 0;
        Values.delay = prevDelay;
        qc = 0;
    }
}
