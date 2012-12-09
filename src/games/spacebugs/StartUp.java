package games.spacebugs;

import java.awt.Dimension;
import java.awt.Point;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.intelligence.BounceAI;
import vooga.shooter.level_editor.Level;
import games.spacebugs.LevelOne;

/**
 * The intro level that shows the instructions.
 * @author Stephen
 *
 */

public class StartUp extends Level {
    
    private static final Dimension ENEMY_DIMENSION = new Dimension(300, 200);
    private static final Point ENEMY_VELOCITY = new Point(0, 0);
    private static final int ENEMY_DAMAGE = 1;

    private SpaceBugs myGame;

    public StartUp (SpaceBugs game) {
        super();
        myGame = game;
        setNextLevel(new LevelOne(myGame));
    }

    public void startLevel () {
        String imagePath = "games/spacebugs/title.png";
        Enemy newEnemy = new Enemy(new Point(300, 150), ENEMY_DIMENSION,
                                   myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                                   ENEMY_DAMAGE);
        myGame.addEnemy(newEnemy);
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }
}
