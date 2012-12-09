package games.tommygame;

import games.tommygame.levels.Level1;
import javax.swing.JFrame;
import arcade.gamemanager.GameSaver;
import vooga.shooter.gameplay.Game;
import vooga.shooter.graphics.Canvas;
import vooga.shooter.graphics.DrawableComponent;

public class SpaceInvadersMain extends JFrame{
    
    public static void main(String [] args) {
        Game myGame = new Game();
        myGame.runGame(null, null);
    }
}
