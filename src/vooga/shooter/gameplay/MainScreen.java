package vooga.shooter.gameplay;

import java.awt.Dimension;
import java.awt.Point;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.level_editor.Level;
import games.tommygame.levels.Level1;


/**
 * First level (initializes enemies, sets winning conditions)
 * 
 * @author Tommy Petrilak
 * 
 */
public class MainScreen extends Level {

    private static final String IMAGEPATH = "vooga/shooter/images/background.gif";
    private static final Dimension ENEMY_DIMENSION = new Dimension(150, 150);
    private static final Point ENEMY_VELOCITY = new Point(0, 0);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    public MainScreen (Game game, Level nextLevel) {
        super();
        myGame = game;
        setNextLevel(nextLevel);
    }

    public void startLevel () {
        myGame.addEnemy(new Enemy(new Point(300, 200), ENEMY_DIMENSION,
                                  myGame.getCanvasDimension(), IMAGEPATH, ENEMY_VELOCITY,
                                  ENEMY_DAMAGE, null));
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }
}
