package vooga.shooter.gameObjects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import util.input.core.KeyboardController;
import vooga.shooter.gameObjects.intelligence.AI;
import vooga.shooter.gameObjects.spriteUtilities.SpriteActionInterface;
import vooga.shooter.gameObjects.spriteUtilities.SpriteMethodMap;
import vooga.shooter.gameplay.inputInitialize.InputTeamSpriteActionAdapter;

/**
 * This class encompasses the basic layout for any sprites that appear in the
 * game. Any specific type of sprite (e.g. player, enemy, boss, etc.) will
 * extend this class and contain any additional information or behaviors
 * particular to that new type of sprite (e.g. the player could have a health
 * limit).
 *
 */
public abstract class Sprite implements SpriteActionInterface {

    protected static final String HIT_BY_BULLET = "hitbybullet";
    protected static final String HIT_BY_ENEMY = "hitbyenemy";
    protected static final String HIT_BY_PLAYER = "hitbyplayer";
    protected static final String PLAYER_TYPE = "player";
    protected static final String BULLET_TYPE = "bullet";
    protected static final String ENEMY_TYPE = "enemy";
    protected static final String LEFT_BOUND = "left";
    protected static final String RIGHT_BOUND = "right";
    protected static final String TOP_BOUND = "top";
    protected static final String BOTTOM_BOUND = "bottom";
    private static final String BULLET_IMAGEPATH = "vooga/shooter/images/playerbullet.png";
    private static final Dimension BULLET_SIZE = new Dimension(5, 10);
    private static final int BULLET_SPEED = 10;
    private static final int BULLET_DAMAGE = 1;
    private Point myPosition;
    private Point myVelocity;
    private Dimension mySize;
    private Dimension myBounds;
    private Image myImage;
    private String myImagePath;
    private List<Bullet> myBulletsFired;
    private int myHealth;
    private SpriteMethodMap myMapper;
    private boolean isDead = false;
    private AI myAI;

    /**
     * Construct a sprite initializing only position, size, and image.
     * Initial health is set to -1 to symbolize a sprite that does not
     * have health.
     * (something stationary with no health, e.g. barrier).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param bounds the size of the canvas holding the sprite
     * @param imagePath the path to the image file of the sprite.
     *          A relative path starting at the src directory.
     */
    public Sprite (Point position, Dimension size,
            Dimension bounds, String imagePath) {
        myPosition = position;
        mySize = size;
        myImagePath = System.getProperty("user.dir") + "/src/" + imagePath;
        myImage = (new ImageIcon(myImagePath)).getImage();
        myVelocity = new Point(0, 0);
        myHealth = Integer.MAX_VALUE;
        myBounds = bounds;
        myMapper = new SpriteMethodMap();
        myBulletsFired = new ArrayList<Bullet>();
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
     * @param bounds the size of the canvas holding the sprite
     * @param imagePath the path to the image file of the sprite.
     *          A relative path starting at the src directory.
     * @param velocity the starting velocity of the sprite
     */
    public Sprite (Point position, Dimension size,
            Dimension bounds, String imagePath, Point velocity) {
        myPosition = position;
        mySize = size;
        myImagePath = System.getProperty("user.dir") + "/src/" + imagePath;
        myImage = (new ImageIcon(myImagePath)).getImage();
        myVelocity = new Point(0, 0);
        myVelocity = velocity;
        myHealth = Integer.MAX_VALUE;
        myBounds = bounds;
        myMapper = new SpriteMethodMap();
        myBulletsFired = new ArrayList<Bullet>();
        setMethods();
    }
    
    /**
     * Constructs a sprite with position, size, image, and health.
     * (something with starting health but no starting velocity, e.g. player).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param bounds the size of the canvas holding the sprite
     * @param imagePath the path to the image file of the sprite.
     *          A relative path starting at the src directory.
     * @param health the starting health of the sprite
     */
    public Sprite (Point position, Dimension size,
            Dimension bounds, String imagePath, int health) {
        myPosition = position;
        mySize = size;
        myImagePath = System.getProperty("user.dir") + "/src/" + imagePath;
        myImage = (new ImageIcon(myImagePath)).getImage();
        myVelocity = new Point(0, 0);
        myHealth = health;
        myVelocity = new Point(0, 0);
        myBounds = bounds;
        myMapper = new SpriteMethodMap();
        myBulletsFired = new ArrayList<Bullet>();
        setMethods();
    }
    
    /**
     * Constructs a sprite with position, size, image, velocity, and health.
     * (something with both starting velocity and health, e.g. enemy).
     *
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param bounds the size of the canvas holding the sprite
     * @param imagePath the path to the image file of the sprite.
     *          A relative path starting at the src directory.
     * @param velocity the starting velocity of the sprite
     * @param health the starting health of the sprite
     */
    public Sprite (Point position, Dimension size,
            Dimension bounds, String imagePath, Point velocity, int health) {
        myPosition = position;
        mySize = size;
        myImagePath = System.getProperty("user.dir") + "/src/" + imagePath;
        myImage = (new ImageIcon(myImagePath)).getImage();
        myVelocity = velocity;
        myHealth = health;
        myBounds = bounds;
        myMapper = new SpriteMethodMap();
        myBulletsFired = new ArrayList<Bullet>();
        initialMethod();
    }

    private void initialMethod() {
        myMapper.addPair("stop", new SpriteActionInterface() {
            public void doAction (Object ... o) {
                setVelocity(0,0);
            }
        });

        setMethods();
    }

    abstract void setMethods();

    /**
     * Decreases the health of this sprite by the
     * amount specified (e.g. after being hit by a bullet).
     * @param damage the amount to decrease health by
     */
    public void decreaseHealth(int damage) {
        myHealth -= damage;
    }
    
    public int getCurrentHealth() {
        return myHealth;
    }

    /**
     * This method draws the image at the sprite's
     * current position. If a sprite needs to draw anything
     * else (e.g. its bullets) then it can implement the
     * continuePaint method, if not, just leave it blank.
     * @param pen used for drawing the image
     */
    public void paint(Graphics pen) {
        pen.drawImage(myImage, getLeft(), getTop(),
                mySize.width, mySize.height, null);
        continuePaint(pen);
    }

    protected abstract void continuePaint(Graphics pen);

    /**
     * This method will run the AI if one is present, 
     * update the position for every sprite, then call
     * an abstract method. This abstract method will be
     * specific to a certain sprite (only if they need to
     * do something other than update position). This
     * allows for easy implementation of new results specific
     * to each sprite when calling the update method.
     */
    public void update() {
        if (myHealth <= 0) {
            die();
        }
        else {
            if (myAI != null) {
                myAI.calculate();
            }
            myPosition.translate(myVelocity.x, myVelocity.y);
            continueUpdate();
        }
    }

    protected abstract void continueUpdate();

    /**
     * Tells the method mapper (class that holds strings to methods)
     * which key to use (which method to choose).
     * If parameters are added here, they should also be added to
     * SpriteMethodMap -> doEvent method. Also, newly added parameters
     * should be added at the end (after all other previous parameters).
     *
     * @param key the string (key) that maps to the right method to do
     * @param s the sprite that this one collides with
     */
    public void doEvent(String key, Sprite s) {
        myMapper.doEvent(key, s);
    }

    /**
     * Sets the sprite's default action as
     * doing nothing (continuing on its current
     * velocity vector).
     *
     * @param o a (possibly empty) list of
     * parameters to be used in the action
     * (only used if the sprite overrides the do
     * action method itself)
     */
    @Override
    public void doAction (Object ... o) {

    }
    
    /**
     * If this is used to add actions other than the default actions, a method must also be added
     * to InputTeamSpriteActionAdapter, similar to the methods already present there. If any parameters are
     * needed for the newly added sprite action, they will begin as the second parameter of the doEvent call.
     *
     * @param e the key event that triggers the new action
     * @param act the method that tells the sprite what to do
     * @param k the keyboard controller used in Game.java
     * @param a the input adapter used in Game.java
     */
    public void addAction(KeyEvent e, SpriteActionInterface act, KeyboardController k, InputTeamSpriteActionAdapter a) {
        myMapper.addPair(e.toString(), act);
        try {
            k.setControl(e.getKeyCode(), KeyboardController.PRESSED, a, act.toString());
            k.setControl(e.getKeyCode(), KeyboardController.RELEASED, a, "stop");
        }
        catch (NoSuchMethodException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * Erases the sprite's image, which will be
     * checked for during paint methods and erased
     * from the game if null.
     */
    public void die() {
        myImage = null;
        setDead(true);
    }
    

    /**
     * Has the player fire a bullet.
     * The bullet is added to the player's list of fired bullets
     * and will be painted during the player's paint method.
     */
    public void fireBullet() {

        Bullet b = new Bullet(new Point(myPosition.x, myPosition.y), BULLET_SIZE, myBounds, BULLET_IMAGEPATH,
                new Point(0, -BULLET_SPEED), BULLET_DAMAGE, this);

        myBulletsFired.add(b);
    }

    /**
     * Checks whether this sprite is within the bounds of the canvas,
     * depending on which wall is checked.
     * @param s the name of the wall to check e.g. "left", "right"
     * @return false if out of bounds, true if in bounds
     */
    public boolean checkBounds(String s) {
        //don't go past right
        if (RIGHT_BOUND.equals(s) && getRight() >= myBounds.width) {
            return false;
        }
        //don't pass left
        else if (LEFT_BOUND.equals(s) && getLeft() <= 0) {
            return false;
        }
        //don't go past top
        else if (TOP_BOUND.equals(s) && getTop() <= 0) {
            return false;        
        }
        //don't pass bottom
        else if (BOTTOM_BOUND.equals(s) && getBottom() >= myBounds.height) {
            return false;
        }
        return true;
    }

    /*
     * Getters and setters below here.
     */

    /**
     * Returns a string representing this sprite's type.
     *
     * @return lowercase string representing type of this sprite
     */
    public abstract String getType();

    /**
     * Returns this sprite's position of the
     * center of its image.
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
     * Returns a list of the bullets fired by this sprite.
     * @return myShotsFired
     */
    public List<Bullet> getBulletsFired() {
        return myBulletsFired;
    }
    /**
     * Returns the image representing this sprite.
     * @return myImage
     */
    public Image getImage() {
        return myImage;
    }

    protected SpriteMethodMap getMapper () {
        return myMapper;
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
    public Dimension getSize() {
        return mySize;
    }

    /**
     * @return the isDead
     */
    public boolean isDead () {
        return isDead;
    }

    /**
     * @param isDead the isDead to set
     */
    public void setDead (boolean isDead) {
        this.isDead = isDead;
    }
    
    /**
     * Sets the AI of the Sprite.
     * @param newAI the AI to set.
     */
    public void setAI (AI newAI) {
        myAI = newAI;
    }

    /**
     * Returns the bounds of the sprite.
     * This is needed for storing the data
     * to xml.
     * 
     * @return the myBounds
     */
    public Dimension getBounds () {
        return myBounds;
    }
    
    /**
     * Returns the health of the sprite.
     * This is needed for storing the data
     * to xml.
     * 
     * @return the health
     */
    public int getHealth () {
        return myHealth;
    }
    
    /**
     * Returns a string representing the path to the image
     * file for the Sprite. The path is relative starting at
     * the src directory. This is needed for converting to
     * xml.
     * 
     * @return the full path to an image file
     */
    public String getImagePath() {
        return myImagePath;
    }
    
}