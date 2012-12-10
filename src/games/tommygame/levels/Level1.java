package games.tommygame.levels;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;
import vooga.shooter.level_editor.Level;


/**
 * First level (initializes enemies, sets winning conditions)
 * 
 * @author Tommy Petrilak
 * 
 */
public class Level1 extends Level {

    private static final int MAX_NUM_ENEMIES = 2;
    private static final String ENEMY_IMAGEPATH = "vooga/shooter/images/alien.png";
    private static final int ENEMY_HEALTH = 1;
    private static final String ASTEROID_IMAGEPATH = "vooga/shooter/images/asteroid.gif";
    private static final int ASTEROID_HEALTH = 3;
    private static final int FALLING_STARTING_HEIGHT = 25;
    private static final Dimension FALLING_OBJECT_DIMENSION = new Dimension(20, 17);
    private static final int THREE = 3;
    private static final int FOUR = 4;

    private Game myGame;
    private Level myNextLevel;
    private Random myRandom;
    private int myNumberOfEnemies;
    private String myFallingImagePath;
    private Point myFallingVelocity;
    private int myFallingObjectHealth;

    /**
     * The first level of the game
     * 
     * @param game pass myGame
     */
    public Level1 (Game game) {
        super();
        myGame = game;
        myRandom = new Random();
        setNextLevel(new Level2(myGame));
    }

    private int randomNumberOfEnemies () {
        int number = myRandom.nextInt(MAX_NUM_ENEMIES);
        while (number <= 0) {
            number = myRandom.nextInt(MAX_NUM_ENEMIES);
        }
        return number;
    }

    private String randomFallingImagePath () {
        ArrayList<String> possibleImages = new ArrayList<String>();
        possibleImages.add(ENEMY_IMAGEPATH);
        String imagePath = possibleImages.get(myRandom.nextInt(possibleImages.size()));
        return imagePath;
    }

    private Point randomFallingVelocity () {
        ArrayList<Integer> possibleVelocities = new ArrayList<Integer>();
        possibleVelocities.add(THREE);
        possibleVelocities.add(FOUR);
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

    @Override
    public void startLevel () {
        myNumberOfEnemies = randomNumberOfEnemies();
        for (int i = 0; i < myNumberOfEnemies; i++) {
            String imagePath = randomFallingImagePath();
            myGame.addEnemy(new Enemy(randomStartingPosition(), FALLING_OBJECT_DIMENSION, myGame
                    .getCanvasDimension(), imagePath, randomFallingVelocity(),
                                      fallingHealth(imagePath)));
        }
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty();
    }

}
