package games.spruillGame.levels;

import games.spruillGame.Game.Game;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import vooga.shooter.gameObjects.Enemy;


public class Level1 extends Level {

    private static final String[] ENEMY_IMAGEPATH = {
            "games/spruillGame/images/asteroid.gif",
            "games/spruillGame/images/asteroid2.gif" };
    private static final int NUMBER_OF_ENEMIES = 20;
    private static final Dimension ENEMY_DIMENSION = new Dimension(20, 17);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;

    /**
     * the first level of the game
     * 
     * @param game
     */
    public Level1 (Game game) {
        super();
        myGame = game;
        setNextLevel(new Level2(myGame));
    }

    public void startLevel () {
        Random rand = new Random();
        for (int j = 0; j < NUMBER_OF_ENEMIES; j++) {
            myGame.addEnemy(new Enemy(new Point((int) ((float) myGame
                    .getCanvasDimension().width / (float) NUMBER_OF_ENEMIES)
                    * j, (int)(rand.nextInt(myGame.getCanvasDimension().height)/(float) NUMBER_OF_ENEMIES)),
                    ENEMY_DIMENSION, myGame.getCanvasDimension(),
                    ENEMY_IMAGEPATH[rand.nextInt(2)], new Point(
                            rand.nextInt(4) - 2, rand.nextInt(5) + 1),
                    ENEMY_DAMAGE));
        }

    }

    @Override
    public boolean winningConditionsMet () {
        Boolean areAsteroidsStillThere = false;
        for (Enemy e : myGame.getEnemies()) {
            if (e.getBottom() < myGame.getCanvasDimension().height
                    && !e.isDead()) areAsteroidsStillThere = true;
        }
        return !areAsteroidsStillThere;
    }

}
