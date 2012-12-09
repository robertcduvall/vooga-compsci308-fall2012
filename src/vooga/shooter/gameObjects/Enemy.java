package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.gameObjects.intelligence.AI;
import vooga.shooter.gameObjects.spriteUtilities.SpriteActionInterface;


/**
 * Represents an enemy sprite to be used in the game.
 * Enemies start with velocity and health.
 * 
 * @author Jesse Starr
 * @author Stephen Hunt
 */
public class Enemy extends Sprite {
    
    AI myAI;

    /**
     * @return the myAI
     */
    protected AI getMyAI () {
        return myAI;
    }

    /**
     * @param myAI the myAI to set
     */
    protected void setMyAI (AI myAI) {
        this.myAI = myAI;
    }

    /**
     * Constructs an enemy character for the game.
     * 
     * @param position the center of the image
     * @param size the size of the image
     * @param bounds the size of the canvas
     * @param imagePath the path to the image file to use.
     *        A relative path starting at the src directory
     * @param velocity the starting velocity for the enemy
     * @param health the starting health of the enemy
     */
    public Enemy (Point position, Dimension size, Dimension bounds, String imagePath,
                  Point velocity, int health) {
        super(position, size, bounds, imagePath, velocity, health);
    }
    
    /**
     * Constructs an enemy character for the game.
     * 
     * @param position the center of the image
     * @param size the size of the image
     * @param bounds the size of the canvas
     * @param imagePath the path to the image file to use.
     *        A relative path starting at the src directory
     * @param velocity the starting velocity for the enemy
     * @param health the starting health of the enemy
     */
    public Enemy (Point position, Dimension size, Dimension bounds, String imagePath,
                  Point velocity, int health, AI ai) {
        super(position, size, bounds, imagePath, velocity, health);
        myAI = ai;
    }

    /**
     * This method is called after the enemy's position is updated.
     * Makes the enemy do something else after moving (e.g. fire a
     * shot).
     */
    protected void continueUpdate () {
        for (Bullet b : getBulletsFired()) {
            b.update();
        }
        if(myAI != null) {
            myAI.calculate();
        }
    }

    /**
     * Returns the type of the sprite.
     * 
     * @return "enemy"
     */
    public String getType () {
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

    /**
     * Sets the methods for enemies.
     */
    @Override
    void setMethods () {
        // if the enemy is hit by a player's bullet then both
        // bullet and enemy die
        getMapper().addPair(HIT_BY_BULLET, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                String bulletOwnerType = ((Bullet) o[0]).getOwner().getType();
                if (PLAYER_TYPE.equals(bulletOwnerType)) {
                    die();
                    ((Bullet) o[0]).die();
                }
            }
        });

        getMapper().addPair(HIT_BY_PLAYER, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                die();
                ((Player) o[0]).die();
            }
        });

        // do nothing if an enemy intersects an enemy
        getMapper().addPair(HIT_BY_ENEMY, this);
    }
    
    /**
     * Sets the AI of the Sprite.
     * @param newAI the AI to set.
     */
    public void setAI (AI newAI) {
        myAI = newAI;
    }
    
    /**
     * Sets the AI of the Sprite.
     * @param newAI the AI to set.
     */
    public AI getAI () {
        return myAI;
    }

}
