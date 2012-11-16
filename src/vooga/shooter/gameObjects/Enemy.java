package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import vooga.shooter.graphics.Canvas;

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
    public Enemy (Point position, Dimension size, Dimension bounds, Image image, Point velocity, int health) {
        super(position, size, bounds, image, velocity, health);
    }

    /**
     * This method is called after the enemy's position is updated.
     * Makes the enemy do something else after moving (e.g. fire a
     * shot).
     */
    protected void continueUpdate() {
        
    }

    /**
     * Describes what happens when the enemy
     * collides with a bullet.
     * @param b the bullet that the enemy is
     * colliding with
     */
    public void collide (Bullet b) {
        
    }

    /**
     * Describes what happens when the enemy
     * collides with a player (will only
     * happen in multiplayer).
     * @param p the player that this enemy
     * is colliding with
     */
    public void collide (Player p) {
        
    }

    /**
     * Describes what happens when this enemy
     * collides with another enemy.
     * @param e the enemy that this enemy is
     * colliding with
     */
    public void collide (Enemy e) {
        
    }

    /**
     * Returns the type of the sprite.
     * @return "enemy"
     */
    public String getType() {
        return "enemy";
    }

    /**
     * Paints bullets of enemy.
     */
    protected void continuePaint (Graphics pen) {
        
    }

    @Override
    void setMethods () {
        
    }
}
