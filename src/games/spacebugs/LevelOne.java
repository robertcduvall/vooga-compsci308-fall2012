package games.spacebugs;

import java.awt.Dimension;
import java.awt.Point;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.intelligence.BounceAI;
import vooga.shooter.level_editor.Level;
/**
 * The first level of the Space Bugs game.
 * @author Stephen Hunt
 *
 */

public class LevelOne extends Level {
    
    private static final Dimension ENEMY_DIMENSION = new Dimension(20, 17);
    private static final Point ENEMY_VELOCITY = new Point(0, 5);
    private static final int ENEMY_DAMAGE = 1;

    private SpaceBugs myGame;

    public LevelOne (SpaceBugs game) {
        super();
        myGame = game;
        setNextLevel(new LevelTwo(myGame));
    }

    public void startLevel () {
        String imagePath = "vooga/shooter/images/alien.png";
        for (int i = 0; i < 5; i++) {
                Enemy newEnemy = new Enemy(new Point(100, 50+(50*i)), ENEMY_DIMENSION,
                                          myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                                          ENEMY_DAMAGE);
                newEnemy.setAI(new BounceAI(newEnemy, myGame.getPlayer()));
                myGame.addEnemy(newEnemy);
        }
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }

}
