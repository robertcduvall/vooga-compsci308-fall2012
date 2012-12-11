package games.tommygame.levels;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;


/**
 * This class represents the fourth level of the Space Invaders game. All
 * classes are constructed very similarly to this class, which shows the
 * versatility and ease with which the game designer can alter the gameplay.
 * 
 * Each level has a set of possible images for enemies (or potentially power
 * ups, if those were chosen to be implemented in the game), and the level is
 * contructed of a random distribution of these images, each of which is tied to
 * a "health", and starts at a random point on the screen, so that the game is
 * different every time it is played.
 * 
 * Rather than storing all of the levels in the Game class itself, each level
 * holds a pointer to the next level, so they are essentially stored in a
 * linked list of levels, and the order of levels can be easily manipulated -
 * the
 * previous level can also be stored, making it a doubly-linked list, so that if
 * the player were to lose one level, they could easily go back and redo the
 * level before, and then come back to this level, all without causing the coder
 * any hassle.
 * 
 * Everything from the images on the screen, to the number of enemies, to their
 * velocities is set randomly so that the game is constantly different when
 * played.
 * 
 * Finally, the winning conditions for the level are set within the level class,
 * so each level could be won by different determining factors, whether killing
 * all of the enemies, reaching a certain score, obtaining a specific power-up,
 * etc.
 * 
 * Everything in the level class is designed to be easily modified by the game
 * designer to allow for new features and to ensure unique gameplay every time.
 * 
 * @author Tommy Petrilak
 * 
 */
public class Level4 extends Level {

    private static final int MIN_NUM_ENEMIES = 2;
    private static final int MAX_NUM_ENEMIES = 4;
    private static final String ENEMY_IMAGEPATH = "vooga/shooter/images/alien.png";
    private static final int ENEMY_HEALTH = 1;
    private static final String INVADER_IMAGEPATH = "vooga/shooter/images/invader.jpg";
    private static final int INVADER_HEALTH = 3;
    private static final String ASTEROID_IMAGEPATH = "vooga/shooter/images/asteroid.gif";
    private static final int ASTEROID_HEALTH = 4;
    private static final int FALLING_STARTING_HEIGHT = 25;
    private static final Dimension FALLING_OBJECT_DIMENSION = new Dimension(20, 17);
    private static final int FIRST_POSSIBLE_VELOCITY = 2;
    private static final int SECOND_POSSIBLE_VELOCITY = 3;

    private Game myGame;
    private Level myNextLevel;
    private Random myRandom;
    private int myNumberOfEnemies;
    private Dimension myCanvasDimensions;

    /**
     * The second level of the game
     * 
     * @param game pass myGame
     */
    public Level4 (Game game) {
        super();
        myGame = game;
        myRandom = new Random();
        myCanvasDimensions = myGame.getCanvasDimension();
        setNextLevel(null);
    }

    private int randomNumberOfEnemies () {
        int number = myRandom.nextInt(MAX_NUM_ENEMIES);
        while (number <= MIN_NUM_ENEMIES) {
            number = myRandom.nextInt(MAX_NUM_ENEMIES);
        }
        return number;
    }

    private String randomFallingImagePath () {
        ArrayList<String> possibleImages = new ArrayList<String>();
        possibleImages.add(ASTEROID_IMAGEPATH);
        possibleImages.add(INVADER_IMAGEPATH);
        possibleImages.add(ENEMY_IMAGEPATH);
        String imagePath = possibleImages.get(myRandom.nextInt(possibleImages.size()));
        return imagePath;
    }

    private Point randomFallingVelocity () {
        ArrayList<Integer> possibleVelocities = new ArrayList<Integer>();
        possibleVelocities.add(FIRST_POSSIBLE_VELOCITY);
        possibleVelocities.add(SECOND_POSSIBLE_VELOCITY);
        return new Point(0, possibleVelocities.get(myRandom.nextInt(possibleVelocities.size())));
    }

    private int fallingHealth (String imagePath) {
        if (imagePath.equals(ASTEROID_IMAGEPATH)) {
            return ASTEROID_HEALTH;
        }
        else if (imagePath.equals(INVADER_IMAGEPATH)) { return INVADER_HEALTH; }
        return ENEMY_HEALTH;
    }

    private Point randomStartingPosition () {
        return new Point(myRandom.nextInt(myGame.getCanvasDimension().width),
                         FALLING_STARTING_HEIGHT);
    }

    /**
     * Calls various (private) random functions to initialize the level with
     * enemies.
     */
    public void startLevel () {
        myNumberOfEnemies = randomNumberOfEnemies();
        for (int i = 0; i < myNumberOfEnemies; i++) {
            String imagePath = randomFallingImagePath();
            myGame.addEnemy(new Enemy(randomStartingPosition(), FALLING_OBJECT_DIMENSION,
                                      myCanvasDimensions, imagePath, randomFallingVelocity(),
                                      fallingHealth(imagePath)));
        }
    }

    @Override
    public boolean winningConditionsMet () {
        return myGame.getEnemies().isEmpty() && myGame.getPlayer().getHealth() > 0;
    }

}
