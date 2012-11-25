package vooga.shooter.gameplay;

import javax.swing.JFrame;
import arcade.gamemanager.GameSaver;
import vooga.shooter.gameplay.Game;
import vooga.shooter.graphics.*;

public class Main extends JFrame{

    public static void main(String [] args) {
        Game myGame = new Game();
        myGame.runGame(null, null);
    }
    
}
