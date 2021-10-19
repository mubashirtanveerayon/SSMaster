package parameter;

import additionalwindows.PreviewWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class Values {
    public static final ImageIcon LOGO = new ImageIcon(load("res/transparentlogo.png"));

    public static boolean alwaysOnTop = false;

    public static int[] customFrame = new int[]{0,0,300,300};

    public static int delay = 0;

    public static boolean fullscreen = true;

    public static int fps = 30;

    public static long duration = 60;

    public static final String PREFERENCE_FILE_NAME = "Preference.txt";

    public static final String TITLE = "SSMaster";

    public static String PREFERENCE_SAVE_PATH = System.getProperty("user.home") + File.separator + "Documents" + File.separator + TITLE + File.separator;

    public static String defaultLocation = System.getProperty("user.home") + File.separator + "Pictures" + File.separator;

    public static final String FILE_NAME = "frame";

    public static final Rectangle SCREEN_SIZE = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

    public static int theme = 0;//0 means white and 1 means black

    public static int format = 0;//0 means png and 1 means jpg;

    public static boolean continuous = false;

    public static boolean openAfterCapture = false;

    public static final int DEFAULT_DELAY = 200;

    public static PreviewWindow previewWindow = new PreviewWindow();

    public static URL load(String path) {
        URL input = Window.class.getResource(path);
        if (input == null) {
            input = Window.class.getResource("/" + path);
        }
        return input;
    }

}
