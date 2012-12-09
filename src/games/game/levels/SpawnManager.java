package games.game.levels;

import games.game.core.Factory;
import games.game.core.IUpdatable;
import games.game.gameobject.DamagableSprite;
import java.awt.Point;
import java.util.Random;
import javax.swing.JPanel;


/*
 * With a bit of trickery I think this class could have been generalized
 * to spawn anything, not just enemies.
 */

/**
 * This class randomly spawns enemies subject to certain criteria.
 * 
 * 1) The number of enemies already present already should be
 * smaller than a certain number.
 * 
 * 2) A certain amount of time has passed since the last spawned
 * enemy.
 * 
 * @author Paul Dannenberg
 * 
 */
public class SpawnManager implements IUpdatable {

    private static final int MAX_NUMBER_SPRITES = 5;
    private static final int SPAWN_INTERVAL = 2000;
    private static final int INITIAL_HIT_POINTS = 100;
    private int myLastSpawnTime;
    private JPanel myBoard;
    private Level myLevel;
    private Factory myFactory;

    /**
     * Creates a new object that will manage enemy spawning for the level.
     * 
     * @param board The display.
     * @param level The level for which this class will spawn enemies.
     * @param factory The factor that will be used to correctly instatiate
     *        enemies.
     */
    public SpawnManager(JPanel board, Level level, Factory factory) {
        myBoard = board;
        myLevel = level;
        spawnInitialEnemies();
    }

    /**
     * Fills the level as soon as it starts up.
     */
    private void spawnInitialEnemies() {
        while (myLevel.getSprites().size() < MAX_NUMBER_SPRITES) {
            myLevel.add(spawnEnemy());
        }
    }

    /**
     * Generates a spawn position at random at which an enemy will spawn.
     * 
     * @param board The JPanel on which the enemy will be painted.
     * @return The spawn location.
     */
    private Point generateSpawnLocation(JPanel board) {
        Random generator = new Random();
        int horizontalLocation = generator.nextInt() % board.getWidth();
        int verticalLocation = generator.nextInt() % board.getHeight();
        return new Point(horizontalLocation, verticalLocation);
    }

    /**
     * Creates a new enemy
     * 
     * @return The enemy.
     */
    private DamagableSprite spawnEnemy() {
        Point positionToSpawn = generateSpawnLocation(myBoard);
        return myFactory.createEnemy(positionToSpawn, INITIAL_HIT_POINTS);
    }

    /**
     * Determines whether or not another enemy should be spawned.
     * This is done by considering the time since the last spawn
     * and how many enemies are already on the map.
     * 
     * @param timeSinceLastSpawn The time elapsed since this object
     *        last spawned an enemy.
     * @return True if this object should proceed in spawning an
     *         enemy. False, otherwise.
     */
    private boolean isReadyToSpawn(int timeSinceLastSpawn) {
        return timeSinceLastSpawn > SPAWN_INTERVAL &&
                myLevel.getSprites().size() < MAX_NUMBER_SPRITES;
    }

    /**
     * Updates this object causing it to check whether enough time
     * has elapsed to spawn another enemy. If this is the case, another
     * enemy is spawned.
     */
    @Override
    public void update() {
        int timeSinceLastSpawn = (int) (System.currentTimeMillis() - myLastSpawnTime);
        if (isReadyToSpawn(timeSinceLastSpawn)) {
            spawnEnemy();
        }
    }

}
