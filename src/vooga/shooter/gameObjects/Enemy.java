package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import vooga.shooter.gameObjects.spriteUtilities.SpriteActionInterface;

/**
 * Represents an enemy sprite to be used in the game.
 * Enemies start with velocity and health.
 *
 * @author Jesse Starr
 * (add your own name as you edit)
 */
public class Enemy extends Sprite {

    /**
     * Constructs an enemy character for the game.
     * @param position the center of the image
     * @param size the size of the image
     * @param bounds the size of the canvas
     * @param image the image to use
     * @param velocity the starting velocity for the enemy
     * @param health the starting health of the enemy
     */
    public Enemy (Point position, Dimension size, Dimension bounds,
            Image image, Point velocity, int health) {
        super(position, size, bounds, image, velocity, health);
    }

    /**
     * This method is called after the enemy's position is updated.
     * Makes the enemy do something else after moving (e.g. fire a
     * shot).
     */
    protected void continueUpdate() {
        for (Bullet b : getBulletsFired()) {
            b.update();
        }
    }

    /**
     * Returns the type of the sprite.
     * @return "enemy"
     */
    public String getType() {
        return ENEMY_TYPE;
    }

    /**
     * Paints bullets of enemy.
     */
    protected void continuePaint (Graphics pen) {
        List<Bullet> deadBullets = new ArrayList<Bullet>();

        for (Bullet b : getBulletsFired()) {
            if (b.getImage() == null) {
                deadBullets.add(b);
            }
            else {
                b.paint(pen);
            }
        }

        getBulletsFired().removeAll(deadBullets);
    }

    @Override
    void setMethods () {
        //if the enemy is hit by a player's bullet then both
        //bullet and enemy die
        getMapper().addPair("hitbybullet", new SpriteActionInterface() {
            public void doAction(Object...o) {
                String bulletOwnerType = ((Bullet) o[0]).getOwner().getType();
                if ("player".equals(bulletOwnerType)) {
                    die();
                    ((Bullet) o[0]).die();
                }
            }
        });

        getMapper().addPair("hitbyplayer", new SpriteActionInterface() {
            public void doAction(Object...o) {
                die();
                ((Player) o[0]).die();
            }
        });

        //do nothing if an enemy intersects an enemy
        getMapper().addPair("hitbyenemy", this);
    }
}
