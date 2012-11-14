
package vooga.turnbased;
import vooga.turnbased.gui.GameWindow;

/**
 * Main entrance of the game
 * 
 * @author Rex
 */

public class Main {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    /**
     * static main method
     * 
     * @param args arguments
     */
    public static void main (String[] args) {
        GameWindow myGameWindow = new GameWindow("Turn-Based RPG", "GameSetting", WIDTH, HEIGHT);
    }
}
