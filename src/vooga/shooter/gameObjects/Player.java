package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import vooga.shooter.gameObjects.spriteUtilities.SpriteActionInterface;


/**
 * Represents the player(s) to be used in the game.
 * Each player will start with health.
 * The player(s) can also be controlled using keys
 * that will direct their movement.
 * 
 * @author Jesse Starr
 *         (add your own name as you edit)
 */
public class Player extends Sprite {
    private static final int SPRITE_MOVEMENT = 10;
    private static final String NO_KEY_PRESSED = "-1";

    /**
     * Constructs a player for the user to control.
     * 
     * @param position the center of the sprite image
     * @param size the size of the sprite image
     * @param bounds the bounds of the canvas
     * @param image the image to use
     * @param health the starting health
     */
    public Player (Point position, Dimension size, Dimension bounds, Image image, int health) {
        super(position, size, bounds, image, health);
    }

    /**
     * This will set the methods for the player that will be called, whether
     * through KeyEvents or collisions
     */
    @Override
    void setMethods () {
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
                die();
            }
        });
    }

    /**
     * This method is called after the player's position
     * is updated and will include anything extra that the player
     * needs to do when being updated (e.g. stop when at a wall).
     */
    protected void continueUpdate () {
        this.setVelocity(0, 0);
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
