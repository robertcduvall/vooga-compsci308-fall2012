package vooga.shooter.gameplay;

import javax.swing.JFrame;
import arcade.gamemanager.GameSaver;
import vooga.shooter.gameplay.Game;
import vooga.shooter.graphics.*;


/**
 * Main class used to run Game for testing purposes.
 * 
 * @author Tommy Petrilak
 *
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

    /**
     * runs game
     * 
     * @param args command line arguments
     */
    public static void main(String [] args) {
        Game myGame = new Game();
        myGame.runGame(null, null);
    }
}
