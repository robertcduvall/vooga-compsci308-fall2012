package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

public class Bullet extends Sprite{
    private int myDamage;

    /**
     * Constructs a bullet to be shown on the screen.
     * @param position the starting position of the bullet
     * @param size the size of the image to use
     * @param image the image of the bullet
     * @param velocity the starting velocity of the bullet
     * @param damage the damage that the bullet will do (to enemy or player)
     */
    public Bullet (Point position, Dimension size, Image image, Point velocity,
            int damage) {
        super(position, size, image, velocity);
        myDamage = damage;
    }

    /**
     * This will be called after the bullet is done moving.
     * Will make the bullet do something extra after motion
     * (e.g. maybe some particle effects, or other graphical
     * stuff, or something else cool).
     */
    @Override
    public void continueUpdate () {
        
    }

    /**
     * Returns the damage this bullet will do to another sprite.
     * @return myDamage
     */
    public int getDamage() {
        return myDamage;
    }
}
