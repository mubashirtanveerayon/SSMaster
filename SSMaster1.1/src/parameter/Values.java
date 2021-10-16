package parameter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class Values {

    public static boolean alwaysOnTop = false;

    public static int[] customFrame = new int[4];

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

}
