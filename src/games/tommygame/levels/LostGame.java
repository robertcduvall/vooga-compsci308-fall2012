package games.tommygame.levels;

import java.awt.Dimension;
import java.awt.Graphics;
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
 * Level to appear when you have lost the game
 * 
 * @author Tommy Petrilak
 * 
 */

public class LostGame extends Level {

    private static final String ENEMY_IMAGEPATH = "vooga/shooter/images/alien.png";

    private Game myGame;
    private Level myNextLevel;
    private Random myRandom;
    private int numberOfEnemies;

    public LostGame (Game game) {
        super();
        myGame = game;
        myRandom = new Random();
        myNextLevel = null;
    }
    private int randomNumberOfEnemies () {
        return 30;
    }

    private String randomFallingImagePath () {
        ArrayList<String> possibleImages = new ArrayList<String>();
        possibleImages.add(ENEMY_IMAGEPATH);
        String imagePath = possibleImages.get(myRandom.nextInt(possibleImages.size()));
        return imagePath;
    }

    private Point randomFallingVelocity () {
        return new Point(0, 0);
    }

    private int fallingHealth (String imagePath) {
        return 100;
    }

    private Point randomStartingPosition () {
        return new Point(myRandom.nextInt(myGame.getCanvasDimension().width),
                         myRandom.nextInt(myGame.getCanvasDimension().height));
    }
    
    private Dimension randomDimension() {
        int heightWidth = myRandom.nextInt(100);
        return new Dimension(heightWidth, heightWidth);
    }
    public void startLevel () {
        numberOfEnemies = randomNumberOfEnemies();
        for (int i = 0; i < numberOfEnemies; i++) {
            String imagePath = randomFallingImagePath();
            myGame.addEnemy(new Enemy(randomStartingPosition(), randomDimension(), myGame
                    .getCanvasDimension(), imagePath, randomFallingVelocity(),
                                      fallingHealth(imagePath)));
        }
    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }

}
