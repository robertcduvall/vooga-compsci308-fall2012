package games.par24game;

import vooga.shooter.gameplay.Game;

/**
 * Main class used to run the SmartEnemyGame independent of the Arcade.
 * NOTE: For testing purposes only
 * 
 * @author Patrick Royal
 *
 */
public class SmartEnemyMain {

    /**
     * Standard main method
     * @param args arguments
     */
    public static void main(String [] args) {
        Game myGame = new Game();
        myGame.runGame(null, null);
    }
}
