import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import game.BubbleSpinner;

/**
 * The DesktopLauncher.
 */
public class DesktopLauncher {
    /**
     * The main function that launches the program.
     * @param arg The argument array for the main function.
     */
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1200;
        config.height = 675;
        config.resizable = true;
        config.title = "Bubble Island";
        new LwjglApplication(new BubbleSpinner(), config);
    }
}
