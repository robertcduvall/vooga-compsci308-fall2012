package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;

/**
 * This class encompasses the basic layout for any sprites that appear in the
 * game. Any specific type of sprite (e.g. player, enemy, boss, etc.) will
 * extend this class and contain any additional information or behaviors
 * particular to that new type of sprite (e.g. the player could have a health
 * limit).
 * 
 */
public abstract class Sprite {
    private Point myPosition;
    private Point myVelocity;
    private Dimension mySize;
    private Image myImage;
    private List<Bullet> myShotsFired;
    private int myHealth;

    /**
     * Construct a sprite initializing only position, size, and image.
     * Initial health is set to -1 to symbolize a sprite that does not
     * have health.
     * (something stationary with no health, e.g. barrier).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param image the image of the sprite
     */
    public Sprite (Point position, Dimension size, Image image) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myHealth = -1;
    }

    /**
     * Constructs a sprite with position, size, image, and starting velocity.
     * Initial health is set to -1 to symbolize a sprite that does not
     * have health.
     * (something with starting velocity but no health, e.g. moving asteroid).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param image the image of the sprite
     * @param velocity the starting velocity of the sprite
     */
    public Sprite (Point position, Dimension size, Image image, 
            Point velocity) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myVelocity = velocity;
        myHealth = -1;
    }

    /**
     * Constructs a sprite with position, size, image, and health.
     * (something with starting health but no starting velocity, e.g. player).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param image the image of the sprite
     * @param health the starting health of the sprite
     */
    public Sprite (Point position, Dimension size, Image image,
            int health) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myHealth = health;
    }

    /**
     * Constructs a sprite with position, size, image, velocity, and health.
     * (something with both starting velocity and health, e.g. enemy).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param image the image of the sprite
     * @param velocity the starting velocity of the sprite
     * @param health the starting health of the sprite
     */
    public Sprite (Point position, Dimension size, Image image,
            Point velocity, int health) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myVelocity = velocity;
        myHealth = health;
    }

    /**
     * Returns this sprite's position.
     * @return myPosition
     */
    public Point getPosition() {
        return myPosition;
    }

    /**
     * Sets this sprite's position to the specified
     * point.
     * @param position the new position
     */
    public void setPosition(Point position) {
        myPosition = position;
    }

    /**
     * Returns this sprite's velocity (velocity is
     * stored as a point to encompass both x and
     * y parts.
     * @return myVelocity
     */
    public Point getVelocity() {
        return myVelocity;
    }

    /**
     * Sets this sprite's velocity to a new one.
     * @param velocity the new velocity to set to 
     */
    public void setVelocity(Point velocity) {
        myVelocity = velocity;
    }

    /**
     * Returns the image representing this sprite.
     * @return myImage
     */
    public Image getImage() {
        return myImage;
    }

    /**
     * Sets this sprite's image to something else.
     * @param image the new image to use
     */
    public void setImage(Image image) {
        myImage = image;
    }

    /**
     * Returns the health of the player.
     * @return myHealth
     */
    public int getHealth() {
        return myHealth;
    }

    /**
     * Sets the sprite's health to
     * a new number (e.g. after being damaged).
     * @param h the new health of the sprite
     */
    public void setHealth(int h) {
        myHealth = h;
    }

    /**
     * Decreases the health of this sprite by the
     * amount specified (e.g. after being hit by a bullet).
     * @param damage the amount to decrease health by
     */
    public void decreaseHealth(int damage) {
        myHealth -= damage;
    }

    /**
     * Returns the leftmost x coordinate for the sprite.
     * @return the left coordinate of the image
     */
    public int getLeft() {
        return myPosition.x - mySize.width / 2;
    }

    /**
     * Returns the topmost y coordinate for the sprite.
     * @return the top coordinate of the image
     */
    public int getTop() {
        return myPosition.y - mySize.height / 2;
    }

    /**
     * Returns the rightmost y coordinate for the sprite.
     * @return the right coordinate of the image
     */
    public int getRight() {
        return myPosition.x + mySize.width / 2;
    }

    /**
     * Returns the bottommost y coordinate for the sprite.
     * @return the bottom coordinate of the image
     */
    public int getBottom() {
        return myPosition.y + mySize.height / 2;
    }

    /**
     * This method draws the image at the sprite's
     * current position.
     * @param g used for drawing the image
     */
    public void paint(Graphics pen) {
        Point topLeft = new Point();
        topLeft.x = myPosition.x - mySize.width / 2;
        topLeft.y = myPosition.y - mySize.height / 2;
        
        pen.drawImage(myImage, topLeft.x, topLeft.y, mySize.width, mySize.height, null);
    }

    /**
     * This method will update the position for every
     * sprite, then call an abstract method. This abstract
     * method will be specific to a certain sprite (only if
     * they need to do something other than update position).
     * This allows for easy implementation of new results specific
     * to each sprite when calling the update method.
     */
    public void update() {
        myPosition.x += myVelocity.x;
        myPosition.y += myVelocity.y;
        continueUpdate();
    }

    public abstract void continueUpdate();

    /**
     * Called when this sprite collides with another
     * sprite.
     */
    public void collide() {
        
    }
}
