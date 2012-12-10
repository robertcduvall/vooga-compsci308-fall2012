package games.spacebugs;

import java.awt.Dimension;
import java.awt.Point;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;
import vooga.shooter.level_editor.Level;

/**
 * The "level" for when the player has won.
 * @author Stephen Hunt
 *
 */

public class WinScreen extends Level{

    private SpaceBugs myGame;
    private static final Dimension ENEMY_DIMENSION = new Dimension(300, 200);
    private static final Point ENEMY_VELOCITY = new Point(0, 0);
    private static final int ENEMY_DAMAGE = 1;

    public WinScreen (SpaceBugs game) {
        super();
        myGame = game;
        setNextLevel(null);
    }

    public void startLevel () {
        String imagePath = "games/spacebugs/win.png";
        Enemy newEnemy = new Enemy(new Point(300, 150), ENEMY_DIMENSION,
                                   myGame.getCanvasDimension(), imagePath, ENEMY_VELOCITY,
                                   ENEMY_DAMAGE);
        myGame.addEnemy(newEnemy);
    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }
}
