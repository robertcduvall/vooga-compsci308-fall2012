package games.par24game.levels;

import java.awt.Dimension;
import java.awt.Point;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;
import vooga.shooter.level_editor.Level;

/**
 * This screen is displayed if/when the player loses.
 * 
 * @author Tommy Petrilak
 * Modified by Patrick Royal
 * 
 */
public class LostGame extends Level {

    private static final String ENEMY_IMAGEPATH = "vooga/shooter/images/alien.png";
    private static final Dimension ENEMY_DIMENSION = new Dimension(60, 51);
    private static final Point ENEMY_VELOCITY = new Point(0, 0);
    private static final int ENEMY_DAMAGE = 1;
    private Game myGame;

    /**
     * Constructor for LostGame.
     * @param game the game lost
     */
    public LostGame (Game game) {
        super();
        myGame = game;
    }

    /**
     * Starts the "level".  In this case, only a single enemy is displayed.
     */
    public void startLevel () {
        myGame.addEnemy(new Enemy(new Point(myGame.getCanvasDimension().width / 2,
                myGame.getCanvasDimension().height / 2), ENEMY_DIMENSION,
                myGame.getCanvasDimension(),
                ENEMY_IMAGEPATH, ENEMY_VELOCITY, ENEMY_DAMAGE));
    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }

}
