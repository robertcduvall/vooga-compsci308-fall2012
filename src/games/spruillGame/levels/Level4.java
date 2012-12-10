package games.spruillGame.levels;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import vooga.shooter.gameObjects.Enemy;
import games.spruillGame.Game.Game;


public class Level4 extends Level {

    private static final String[] ENEMY_IMAGEPATH = {
            "games/spruillGame/images/asteroid.gif",
            "games/spruillGame/images/asteroid2.gif" };
    private static final int NUMBER_OF_ENEMIES = 400;
    private static final Dimension ENEMY_DIMENSION = new Dimension(20, 17);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    /**
     * the first level of the game
     * 
     * @param game
     */
    public Level4 (Game game) {
        super();
        myGame = game;
        setNextLevel(new WonGame(myGame));
    }

    public void startLevel () {
        Random rand = new Random();
        for (int j = 0; j < NUMBER_OF_ENEMIES; j++) {
            myGame.addEnemy(new Enemy(new Point((int) (((float) myGame
                    .getCanvasDimension().width / (float) NUMBER_OF_ENEMIES)
                    * j), rand.nextInt(30)+20),
                    ENEMY_DIMENSION, myGame.getCanvasDimension(),
                    ENEMY_IMAGEPATH[rand.nextInt(2)], new Point(
                            rand.nextInt(4) - 2, rand.nextInt(8) + 1),
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
