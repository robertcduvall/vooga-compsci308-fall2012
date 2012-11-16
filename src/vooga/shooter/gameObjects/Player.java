package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import vooga.shooter.graphics.Canvas;

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
    public Player (Point position, Dimension size, Dimension bounds, Image image, int health) {
        super(position, size, bounds, image, health);
    }

    /**
     * This will set the methods for the player that will be called
     * when a certain key is pressed or a collision happens. The methods
     * are mapped to a string that describes the type of action happening,
     * e.g. "37" is used for the left arrow key because that is the int value
     * returned by the keyListener when pressing the left arrow key.
     */
    void setMethods() {
        //37 is the int value for left arrow key
        getMapper().addPair("37", new MethodWrapper() {
                                public void doAction(Object...o) {
                                    setVelocity(-10, getVelocity().y);
                                }
        });

        //38 is the int value for up arrow key
        getMapper().addPair("38", new MethodWrapper() {
                                public void doAction(Object...o) {
                                    setVelocity(getVelocity().x, -10);
                                }
        });

        //39 is the int value for right arrow key
        getMapper().addPair("39", new MethodWrapper() {
                                public void doAction(Object...o) {
                                    setVelocity(10, getVelocity().y);
                                }
        });

        //40 is the int value for down arrow key
        getMapper().addPair("40", new MethodWrapper() {
                                public void doAction(Object...o) {
                                    setVelocity(getVelocity().x, 10);
                                }
        });

        //32 is the int value for spacebar key
        getMapper().addPair("32", new MethodWrapper() {
                                public void doAction(Object...o) {
                                    fireBullet();
                                }
        });

        //-1 is the int value for no key pressed
        getMapper().addPair("-1", this);

        //the player is hit with a bullet (decreases health)
        getMapper().addPair("hitbullet", new MethodWrapper() {
                                public void doAction(Object...o) {
                                    decreaseHealth((Integer) o[0]);
                                }
        });
    }

    /**
     * This method is called after the player's position
     * is updated and will include anything extra that the player
     * needs to do when being updated (e.g. stop when at a wall).
     */
    protected void continueUpdate() {
        //don't go past right or left wall
        if(getRight() >= getBounds().width || getLeft() <= 0) {
            setVelocity(0, getVelocity().y);
        }
        //don't go past top or bottom wall
        if(getTop() <= 0 || getBottom() >= getBounds().height) {
            setVelocity(getVelocity().x, 0);
        }
    }

    /**
     * Returns the type of the sprite.
     * @return player
     */
    public String getType() {
        return "player";
    }

    /**
     * Paints bullets of player.
     */
    protected void continuePaint (Graphics pen) {
        for(Bullet b : getMyBulletsFired()) {
            b.paint(pen);
        }
    }

    /**
     * Has the player fire a bullet.
     * The bullet is added to the player's list of fired bullets
     * and will be painted during the player's paint method.
     */
    public void fireBullet() {
        ImageIcon iib = new ImageIcon(this.getClass().getResource("../vooga/shooter/images/playerbullet.png"));
        Image bulletImage = iib.getImage();

        Bullet b = new Bullet(getPosition(), new Dimension(5, 5), getBounds(),
                bulletImage, new Point(0, getVelocity().y - 5), 0);

        getMyBulletsFired().add(b);
    }
}
