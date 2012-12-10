package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import vooga.shooter.gameObjects.spriteUtilities.SpriteActionInterface;


/**
 * Represents the player(s) to be used in the game.
 * Each player will start with health.
 * Player is a singleton because only one instance is required and state should be maintained
 * independent of environment.
 * The player(s) can also be controlled using keys
 * that will direct their movement.
 * 
 * @author Jesse Starr
 *         Tommy Petrilak
 */
public class Player extends Sprite {
    private static final int SPRITE_MOVEMENT = 5;
    private static final String NO_KEY_PRESSED = "-1";
    
    /**
     * Constructs an enemy character for the game.
     * @param position the center of the image
     * @param size the size of the image
     * @param bounds the size of the canvas
     * @param imagePath the path to the image file to use.
     *          A relative path starting at the src directory
     * @param velocity the starting velocity for the player
     * @param health the starting health of the player
     */
    public Player (Point position, Dimension size, Dimension bounds,
        String imagePath, Point velocity, int health) {
        super(position, size, bounds, imagePath, velocity, health);
    }

    /**
     * This will set the methods for the player that will be called, whether
     * through KeyEvents or collisions
     */
    @Override
    public void setMethods () {
        getMapper().addPair(Integer.toString(KeyEvent.VK_LEFT), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                if (checkBounds(LEFT_BOUND)) {
                    setVelocity(-SPRITE_MOVEMENT, getVelocity().y);
                }
                else {
                    setVelocity(0, 0);
                }
            }
        });

        getMapper().addPair(Integer.toString(KeyEvent.VK_UP), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                if (checkBounds(TOP_BOUND)) {
                    setVelocity(getVelocity().x, -SPRITE_MOVEMENT);
                }
                else {
                    setVelocity(0, 0);
                }
            }
        });

        getMapper().addPair(Integer.toString(KeyEvent.VK_RIGHT), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                if (checkBounds(RIGHT_BOUND)) {
                    setVelocity(SPRITE_MOVEMENT, getVelocity().y);
                }
                else {
                    setVelocity(0, 0);
                }
            }
        });

        getMapper().addPair(Integer.toString(KeyEvent.VK_DOWN), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                if (checkBounds(BOTTOM_BOUND)) {
                    setVelocity(getVelocity().x, SPRITE_MOVEMENT);
                }
                else {
                    setVelocity(0, 0);
                }
            }
        });

        getMapper().addPair(Integer.toString(KeyEvent.VK_SPACE), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                fireBullet();
            }
        });

        getMapper().addPair(NO_KEY_PRESSED, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                setVelocity(0, 0);
            }
        });

        getMapper().addPair(HIT_BY_BULLET, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                decreaseHealth(((Bullet) o[0]).getDamage());
                ((Bullet) o[0]).die();
            }
        });

        getMapper().addPair(HIT_BY_ENEMY, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                decreaseHealth(1);
            }
        });
    }

    /**
     * This method is called after the player's position
     * is updated and will include anything extra that the player
     * needs to do when being updated (e.g. stop when at a wall).
     */
    protected void continueUpdate () {
        for (Bullet b : getBulletsFired()) {
            b.update();
        }
    }

    /**
     * Returns the type of the sprite.
     * 
     * @return player
     */
    public String getType () {
        return PLAYER_TYPE;
    }

    /**
     * Paints bullets of player.
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
}
