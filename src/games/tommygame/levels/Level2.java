package games.tommygame.levels;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;


/**
 * Second level (initializes enemies, sets winning conditions)
 * 
 * @author Tommy Petrilak
 * 
 */
public class Level2 extends Level {

    private static final String ENEMY_IMAGEPATH = "vooga/shooter/images/alien.png";
    private static final int ENEMY_HEALTH = 1;
    private static final String ASTEROID_IMAGEPATH = "vooga/shooter/images/asteroid.gif";
    private static final int ASTEROID_HEALTH = 3;
    private static final int FALLING_STARTING_HEIGHT = 25;
    private static final Dimension ENEMY_DIMENSION = new Dimension(20, 17);

    private Game myGame;
    private Level myNextLevel;
    private Random myRandom;
    private int numberOfEnemies;
    private int numberOfWaves;
    private String fallingImagePath;
    private Point fallingVelocity;
    private int fallingHealth;

    /**
     * the first level of the game
     * 
     * @param game
     */
    public Level2 (Game game) {
        super();
        myGame = game;
        myRandom = new Random();
        setNextLevel(new Level3(myGame));
    }

    private int randomNumberOfEnemies () {
        return myRandom.nextInt(5);
    }

    private String randomFallingImagePath () {
        ArrayList<String> possibleImages = new ArrayList<String>();
        possibleImages.add(ASTEROID_IMAGEPATH);
        possibleImages.add(ENEMY_IMAGEPATH);
        return possibleImages.get(myRandom.nextInt(possibleImages.size()));
    }

    private Point randomFallingVelocity () {
        return new Point(0, myRandom.nextInt(6));
    }

    private int fallingHealth (String imagePath) {
        if (imagePath.equals(ASTEROID_IMAGEPATH)) { return ASTEROID_HEALTH; }
        return ENEMY_HEALTH;
    }

    private Point randomStartingPosition () {
        return new Point(myRandom.nextInt(myGame.getCanvasDimension().width),
                         FALLING_STARTING_HEIGHT);
    }

    public void startLevel () {
        numberOfEnemies = randomNumberOfEnemies();
        for (int j = 0; j < numberOfEnemies; j++) {
            String imagePath = randomFallingImagePath();
            myGame.addEnemy(new Enemy(randomStartingPosition(), ENEMY_DIMENSION, myGame
                    .getCanvasDimension(), imagePath, randomFallingVelocity(),
                                      fallingHealth(imagePath)));
        }
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }

}
