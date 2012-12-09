package games.tommygame;

import games.tommygame.levels.Level1;
import javax.swing.JFrame;
import arcade.gamemanager.GameSaver;
import vooga.shooter.gameplay.Game;
import vooga.shooter.graphics.Canvas;
import vooga.shooter.graphics.DrawableComponent;


/**
 * The Main method for the Space Invaders game. The levels package shows the
 * versatility of the level design within the framework - as the levels store
 * pointers to each other, level design as well as order can be easily
 * manipulated.
 * 
 * This Main method calls the Game class in vooga.shooter.gameplay and uses the
 * characters/gameObjects stored there.
 * 
 * @author Tommy Petrilak
 */

public class SpaceInvadersMain extends JFrame {

    public static void main (String[] args) {
        Game myGame = new Game();
        myGame.runGame(null, null);
    }
}
