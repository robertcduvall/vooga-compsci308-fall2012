package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

/**
 * Represents an enemy sprite to be used in the game.
 * Enemies start with velocity and health.
 *
 * @author Jesse Starr
 * (add your own name as you edit)
 */
public class Enemy extends Sprite{

    /**
     * Constructs an enemy character for the game.
     * @param position the center of the image
     * @param size the size of the image
     * @param image the image to use
     * @param velocity the starting velocity for the enemy
     * @param health the starting health of the enemy
     */
    public Enemy (Point position, Dimension size, Image image, Point velocity, int health) {
        super(position, size, image, velocity, health);
    }

    /**
     * This method is called after the enemy's position is updated.
     * Makes the enemy do something else after moving (e.g. fire a
     * shot).
     */
    @Override
    public void continueUpdate () {
        
    }
}
