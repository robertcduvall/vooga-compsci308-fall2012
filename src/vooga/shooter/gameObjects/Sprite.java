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

    /**
     * Construct a sprite initializing only position, size, and image.
     * @param position the center of the sprite image
     * @param size the size of the image to display
     * @param image the image of the sprite
     */
    public Sprite(Point position, Dimension size, Image image){
        myPosition = position;
        mySize = size;
        myImage = image;
    }

    /**
     * Constructs a sprite with position, size, image, and starting velocity.
     * @param position
     * @param size
     * @param image
     * @param velocity
     */
    public Sprite(Point position, Dimension size, Image image, 
            Point velocity){
        myPosition = position;
        mySize = size;
        myImage = image;
        myVelocity = velocity;
    }

    /**
     * Returns this sprite's position.
     * @return myPosition
     */
    public Point getPosition(){
        return myPosition;
    }

    /**
     * Sets this sprite's position to the specified
     * point.
     * @param position the new position
     */
    public void setPosition(Point position){
        myPosition = position;
    }

    /**
     * Returns this sprite's velocity (velocity is
     * stored as a point to encompass both x and
     * y parts.
     * @return myVelocity
     */
    public Point getVelocity(){
        return myVelocity;
    }

    /**
     * Sets this sprite's velocity to a new one.
     * @param velocity the new velocity to set to 
     */
    public void setVelocity(Point velocity){
        myVelocity = velocity;
    }

    /**
     * Returns the image representing this sprite.
     * @return myImage
     */
    public Image getImage(){
        return myImage;
    }

    /**
     * Sets this sprite's image to something else.
     * @param image the new image to use
     */
    public void setImage(Image image){
        myImage = image;
    }

    /**
     * This method draws the image at the sprite's
     * current position.
     * @param g used for drawing the image
     */
    public void draw(Graphics g){
        Point topLeft = new Point();
        topLeft.x = myPosition.x - mySize.width / 2;
        topLeft.y = myPosition.y - mySize.height / 2;
        
        g.drawImage(myImage, topLeft.x, topLeft.y, mySize.width, mySize.height, null);
    }

    /**
     * This method will update the position for every
     * sprite, then call an abstract method. This abstract
     * method will be specific to a certain sprite (only if
     * they need to do something other than update position).
     * This allows for easy implementation of new results specific
     * to each sprite when calling the update method.
     */
    public void update(){
        myPosition.x += myVelocity.x;
        myPosition.y += myVelocity.y;
        continueUpdate();
    }

    public abstract void continueUpdate();
}
