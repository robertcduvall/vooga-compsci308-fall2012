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
 * First level (initializes enemies, sets winning conditions)
 * 
 * @author Tommy Petrilak
 * 
 */
public class Level1 extends Level {

    private static final String ENEMY_IMAGEPATH = "vooga/shooter/images/alien.png";
    private static final int ENEMY_HEALTH = 1;
    private static final String ASTEROID_IMAGEPATH = "vooga/shooter/images/asteroid.gif";
    private static final int ASTEROID_HEALTH = 3;
    private static final int FALLING_STARTING_HEIGHT = 25;
    private static final Dimension FALLING_OBJECT_DIMENSION = new Dimension(20, 17);

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
    public Level1 (Game game) {
        super();
        myGame = game;
        myRandom = new Random();
        setNextLevel(new Level2(myGame));
    }

    private int randomNumberOfEnemies () {
        return myRandom.nextInt(2);
    }

    private String randomFallingImagePath () {
        ArrayList<String> possibleImages = new ArrayList<String>();
        possibleImages.add(ASTEROID_IMAGEPATH);
        possibleImages.add(ENEMY_IMAGEPATH);
        String imagePath = possibleImages.get(myRandom.nextInt(possibleImages.size()));
        return imagePath;
    }

    private Point randomFallingVelocity () {
        ArrayList<Integer> possibleVelocities = new ArrayList<Integer>();
        possibleVelocities.add(3);
        possibleVelocities.add(4);
        possibleVelocities.add(5);
        return new Point(0, possibleVelocities.get(myRandom.nextInt(possibleVelocities.size())));
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
        for (int i = 0; i < numberOfEnemies; i++) {
            String imagePath = randomFallingImagePath();
            myGame.addEnemy(new Enemy(randomStartingPosition(), FALLING_OBJECT_DIMENSION, myGame
                    .getCanvasDimension(), imagePath, randomFallingVelocity(),
                                      fallingHealth(imagePath)));
            System.out.println(imagePath + " " + fallingHealth(imagePath));
        }
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }

}
