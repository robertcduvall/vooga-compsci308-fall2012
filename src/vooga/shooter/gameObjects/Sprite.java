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
public abstract class Sprite implements MethodWrapper {
    private Point myPosition;
    private Point myVelocity;
    private Dimension mySize;
    private Dimension myBounds;
    private Image myImage;
    private List<Bullet> myShotsFired;
    private int myHealth;
    private MethodMapper myMapper;

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
    public Sprite (Point position, Dimension size, Dimension bounds, Image image) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myHealth = Integer.MAX_VALUE;
        myBounds = bounds;
        myMapper = new MethodMapper();
        setMethods();
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
    public Sprite (Point position, Dimension size, Dimension bounds, Image image, 
            Point velocity) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myVelocity = velocity;
        myHealth = Integer.MAX_VALUE;
        myBounds = bounds;
        myMapper = new MethodMapper();
        setMethods();
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
    public Sprite (Point position, Dimension size, Dimension bounds, Image image,
            int health) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myHealth = health;
        myBounds = bounds;
        myMapper = new MethodMapper();
        setMethods();
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
    public Sprite (Point position, Dimension size, Dimension bounds, Image image,
            Point velocity, int health) {
        myPosition = position;
        mySize = size;
        myImage = image;
        myVelocity = velocity;
        myHealth = health;
        myBounds = bounds;
        myMapper = new MethodMapper();
        setMethods();
    }

    abstract void setMethods();

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
     * Can set a new velocity with x and y values
     * instead of a new point.
     * @param x the x value of the new velocity
     * @param y the y value of the new velocity
     */
    public void setVelocity(int x, int y) {
        Point v = new Point(x, y);
        setVelocity(v);
    }

    /**
     * Returns the bullets fired by this sprite.
     * @return a list of the bullets that this sprite
     * has fired.
     */
    public List<Bullet> getMyBulletsFired() {
        return myShotsFired;
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
     * Returns the dimensions of the sprite.
     * @return the dimensions of the sprite.
     */
    public Dimension getDimension() {
        return mySize;
    }

    /**
     * @return lowercase string representing type of this sprite
     */
    public abstract String getType();

    /**
     * This method draws the image at the sprite's
     * current position. If a sprite needs to draw anything
     * else (e.g. its bullets) then it can implement the
     * continuePaint method, if not, just leave it blank.
     * @param g used for drawing the image
     */
    public void paint(Graphics pen) {
        pen.drawImage(myImage, getLeft(), getTop(), mySize.width, mySize.height, null);
        continuePaint(pen);
    }

    protected abstract void continuePaint(Graphics pen);

    /**
     * This method will update the position for every
     * sprite, then call an abstract method. This abstract
     * method will be specific to a certain sprite (only if
     * they need to do something other than update position).
     * This allows for easy implementation of new results specific
     * to each sprite when calling the update method.
     */
    public void update() {
        myPosition.translate(myVelocity.x, myVelocity.y);
        continueUpdate();
    }

    protected abstract void continueUpdate();

    /**
     * Called when this sprite collides with another
     * sprite. Which one is called depends on the type
     * of sprite it is colliding with.
     */
    public void collide(Sprite s) {
        
    }

    protected void setMapper (MethodMapper mapper) {
        this.myMapper = mapper;
    }

    protected MethodMapper getMapper () {
        return myMapper;
    }

    protected Dimension getBounds() {
        return myBounds;
    }

    /**
     * Tells the method mapper (class that holds strings to methods)
     * which key to use (which method to choose).
     *
     * @param key the string (key) that maps to the right method to do
     * @param damage any damage this sprite will take
     * @param s the sprite that this one collides with
     */
    public void doEvent(String key, int damage, Sprite s) {
        myMapper.doEvent(key, damage, s);
    }

    /**
     * Sets the sprite's velocity to 0.
     */
    @Override
    public void doAction (Object ... o) {
        setVelocity(0, 0);
    }
}
