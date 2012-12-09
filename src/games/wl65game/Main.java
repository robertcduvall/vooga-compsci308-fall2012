package games.wl65game;

import javax.swing.JFrame;
import arcade.gamemanager.GameSaver;
import games.wl65game.Game;
import vooga.shooter.graphics.*;

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
