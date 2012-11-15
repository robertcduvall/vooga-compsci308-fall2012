
package vooga.turnbased;
<<<<<<< HEAD
import vooga.turnbased.gui.GameWindow;
=======
>>>>>>> master

/**
 * Main entrance of the game
 * 
 * @author Rex
 */

<<<<<<< HEAD
public class Main {
=======

class Main {
>>>>>>> master

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /**
     * static main method
     * 
     * @param args arguments
     */
<<<<<<< HEAD
    public static void main (String[] args) {
        GameWindow myGameWindow = new GameWindow("Turn-Based RPG", "GameSetting", WIDTH, HEIGHT);
=======
    public static void main(String[] args) {
        GameWindow myGameWindow = new GameWindow(WIDTH, HEIGHT,
                "Scrolling Games");
        myGameWindow.addResourceBundle("GameSetting");
        myGameWindow.initializeCanvas();
>>>>>>> master
    }
}
