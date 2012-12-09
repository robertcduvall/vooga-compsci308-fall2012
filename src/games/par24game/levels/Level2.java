package games.par24game.levels;

import java.awt.Dimension;
import java.awt.Point;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameObjects.intelligence.ChaseAI;
import vooga.shooter.gameplay.Game;
import vooga.shooter.level_editor.Level;

/**
 * Level 2 is a bit tougher - the enemy will now follow you around with intent
 * to collide.
 * @author Patrick Royal
 *
 */
public class Level2 extends Level {
    private static final String ENEMY_IMAGEPATH = "vooga/shooter/images/alien.png";
    private static final int NUMBER_OF_STAGES = 1;
    private static final int NUMBER_OF_ENEMIES = 1;
    private static final Dimension ENEMY_DIMENSION = new Dimension(20, 17);
    private static final Point ENEMY_VELOCITY = new Point(0, 3);
    private static final int ENEMY_DAMAGE = 1;
    private static final int ENEMY_X_OFFSET = 300;
    private static final int ENEMY_OFFSET = 150;

    private Game myGame;
    @SuppressWarnings("unused")
    private Level myNextLevel;

    /**
     * Constructor for Level 2
     * @param game the game in which it will be used
     */
    public Level2 (Game game) {
        super();
        myGame = game;
        setNextLevel(new Level3(myGame));
    }

    /**
     * Generates the enemies, sets their behavior, and starts the level.
     */
    public void startLevel () {
        for (int i = 0; i < NUMBER_OF_STAGES; i++) {
            for (int j = 0; j < NUMBER_OF_ENEMIES; j++) {
                Enemy e = new Enemy(new Point(ENEMY_X_OFFSET + (ENEMY_OFFSET * j),
                        ENEMY_OFFSET), ENEMY_DIMENSION,
                        myGame.getCanvasDimension(), ENEMY_IMAGEPATH,
                        ENEMY_VELOCITY, ENEMY_DAMAGE);
                e.setAI(new ChaseAI(e, myGame.getPlayer()));
                myGame.addEnemy(e);
            }
        }

    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }
}
