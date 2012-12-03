
package vooga.turnbased;

import java.awt.Image;
import java.util.List;
import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * Main entrance of the game
 * For Testing the sample game
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
