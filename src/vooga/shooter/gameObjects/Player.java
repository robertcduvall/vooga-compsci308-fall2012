package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

/**
 * Represents the player(s) to be used in the game.
 * Each player will start with health.
 * The player(s) can also be controlled using keys
 * that will direct their movement.
 *
 * @author Jesse Starr
 * (add your own name as you edit)
 */
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

    /**
     * Changes the player's velocity to add a -x component,
     * while keeping the current y velocity.
     * (This makes the player's sprite go left on the screen).
     */
    public void goLeft() {
        Point v = new Point(-10, getVelocity().y);
        setVelocity(v);
        update();
    }

    /**
     * Changes the player's velocity to add a +x component,
     * while keeping the current y velocity.
     * (This makes the player's sprite go right on the screen).
     */
    public void goRight() {
        Point v = new Point(10, getVelocity().y);
        setVelocity(v);
        update();
    }

    /**
     * Changes the player's velocity to add a -y component,
     * while keeping the current x velocity.
     * (This makes the player's sprite go up on the screen).
     */
    public void goUp() {
        Point v = new Point(getVelocity().x, -10);
        setVelocity(v);
        update();
    }

    /**
     * Changes the player's velocity to add a +y component,
     * while keeping the current x velocity.
     * (This makes the player's sprite go down on the screen).
     */
    public void goDown() {
        Point v = new Point(getVelocity().x, 10);
        setVelocity(v);
        update();
    }

    /**
     * Called if none of the keys that move the player are
     * pressed. (Makes the player sprite stop moving).
     */
    public void stopMove() {
        Point v = new Point(0, 0);
        setVelocity(v);
        update();
    }
}
