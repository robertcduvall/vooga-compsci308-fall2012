package games.spruillGame.levels;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import vooga.shooter.gameObjects.Enemy;
import games.spruillGame.Game.Game;


public class Level3 extends Level {

    private static final String[] ENEMY_IMAGEPATH = {
            "games/spruillGame/images/asteroid.gif",
            "games/spruillGame/images/asteroid2.gif" };
    private static final int NUMBER_OF_ENEMIES = 200;
    private static final Dimension ENEMY_DIMENSION = new Dimension(20, 17);
    private static final int ENEMY_DAMAGE = 1;

    private Game myGame;
    private Level myNextLevel;

    /**
     * the first level of the game
     * 
     * @param game
     */
    public Level3 (Game game) {
        super();
        myGame = game;
        setNextLevel(new Level4(myGame));
    }

    public void startLevel () {
        Random rand = new Random();
        for (int j = 0; j < NUMBER_OF_ENEMIES; j++) {
            myGame.addEnemy(new Enemy(new Point((int) (((float) myGame
                    .getCanvasDimension().width / (float) NUMBER_OF_ENEMIES)
                    * j), 50),
                    ENEMY_DIMENSION, myGame.getCanvasDimension(),
                    ENEMY_IMAGEPATH[rand.nextInt(2)], new Point(
                            rand.nextInt(4) - 2, rand.nextInt(5) + 1),
                    ENEMY_DAMAGE));
        }

    }

    @Override
    public boolean winningConditionsMet () {
        Boolean areAsteroidsStillThere = false;
        System.out.println("level3\t" + myGame.getEnemies().size());
        for (Enemy e : myGame.getEnemies()) {
            if (e.getBottom() < myGame.getCanvasDimension().height
                    && !e.isDead()) areAsteroidsStillThere = true;
        }
        return !areAsteroidsStillThere;
    }

}