/**
 * Main entrance of the game
 * 
 * @author Rex
 */
package vooga.turnbased;
import vooga.turnbased.gui.GameWindow;


/**
 * @param args arguments
 */
public class Main {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /**
     * main method
     */
    public void run () {
        GameWindow myGameWindow = new GameWindow(WIDTH, HEIGHT, "RPG");
        myGameWindow.addResourceBundle("GameSetting");
        myGameWindow.initializeCanvas();
    }
}
