package vooga.shooter.implementation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;


/**
 * First level (initializes enemies, sets winning conditions)
 * 
 * @author Tommy Petrilak
 * 
 */
public class LostGame extends Level {

    private static final int NUMBER_OF_STAGES = 1;
    private static final int NUMBER_OF_ENEMIES = 1;
    private static final Dimension ENEMY_DIMENSION = new Dimension(60, 51);
    private static final Point ENEMY_VELOCITY = new Point(0, 0);
    private static final int ENEMY_DAMAGE = 1;
    private static final String LOST_GAME = "YOU LOSE -- THE ALIENS WON";

    private Game myGame;
    private Level myNextLevel;

    public LostGame (Game game) {
        super();
        myGame = game;
        myNextLevel = null;
    }

    public void startLevel () {
        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("../images/alien.png"));
        Image enemyImage = imageIcon.getImage();
        myGame.addEnemy(new Enemy(new Point(myGame.getCanvasDimension().width / 2, myGame
                .getCanvasDimension().height / 2), ENEMY_DIMENSION, myGame.getCanvasDimension(),
                enemyImage, ENEMY_VELOCITY, ENEMY_DAMAGE));
    }

    public void paint (Graphics pen) {
        pen.drawString(LOST_GAME, myGame.getCanvasDimension().width / 2,
                       myGame.getCanvasDimension().height / 2);
    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }

}
