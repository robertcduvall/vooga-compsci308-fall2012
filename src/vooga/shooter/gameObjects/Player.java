package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

public class Player extends Sprite{

    /**
     * Constructs a player for the user to control.
     * @param position the center of the sprite image
     * @param size the size of the sprite image
     * @param image the image to use
     * @param health the starting health
     */
    public Player (Point position, Dimension size, Image image, int health) {
        super(position, size, image, health);
    }

    /**
     * This method is called after the player's position
     * is updated and will include anything extra that the player
     * needs to do when being updated (e.g. stop when at a wall).
     */
    @Override
    public void continueUpdate () {
        
    }
}
