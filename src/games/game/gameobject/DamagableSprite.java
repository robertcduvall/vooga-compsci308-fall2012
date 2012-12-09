package games.game.gameobject;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;


/**
 * This class represents a game object that can be damaged. In the
 * context of the game it will represent an enemy.
 * 
 * @author Paul Dannenberg
 * 
 */
public class DamagableSprite extends Sprite {

    private int myCurrentHealth;
    private HealthBar myHealthBar;

    /**
     * Creates a new object.
     * 
     * @param image The image of the new object.
     * @param location The spawning location.
     * @param startingHealth The health at which this object starts.
     */
    public DamagableSprite(Image image, Point location, int startingHealth) {
        super(image, location);
        myCurrentHealth = startingHealth;
        myHealthBar = new HealthBar(startingHealth, this);
    }

    /**
     * Moves this object by a specified amount.
     * 
     * @param amountToMove The distance the sprite will move.
     */
    public void move(Point amountToMove) {
        getLocation().translate(amountToMove.x, amountToMove.y);
    }

    /**
     * Damages this object by a particular amount.
     * 
     * @param amount The quantity to damage this object by.
     */
    public void damage(int amount) {
        myCurrentHealth -= amount;
    }

    /**
     * The amount of health this object has remaining
     * before it dies.
     * 
     * @return The remaining health.
     */
    public int getHealth() {
        return myCurrentHealth;
    }

    /**
     * Finds out whether or not the sprite is still alive.
     * 
     * @return True if the object is still alive, false
     *         otherwise.
     */
    public boolean isAlive() {
        return myCurrentHealth > 0;
    }

    /**
     * Paints the sprite and its associated health bar.
     */
    @Override
    public void paint(Graphics2D pen) {
        super.paint(pen);
        myHealthBar.paint(pen);
    }

}
