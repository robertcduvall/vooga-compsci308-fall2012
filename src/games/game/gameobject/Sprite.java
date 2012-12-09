package games.game.gameobject;

import games.game.core.IPaintable;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;


/**
 * Represents a game object on the screen.
 * 
 * @author Paul Dannenberg
 * 
 */
public class Sprite implements IPaintable {

    private Image myImage;
    private Point myLocation;

    // BAD!!!
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    /**
     * Creates a new object that will be displayed on
     * the screen.
     * 
     * @param image The image of the new object.
     * @param location The initial location of the new object.
     */
    public Sprite(Image image, Point location) {
        myImage = resizeImage(image, WIDTH, HEIGHT);
        myLocation = location;
    }

    /**
     * Resizes this object's image.
     * 
     * @param toResize The image to be resized.
     * @param width The width to be resized to.
     * @param height The height to be resized to.
     * @return
     */
    private Image resizeImage(Image toResize, int width, int height) {
        return toResize.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
    }

    /**
     * Displays this object's image on the screen.
     */
    @Override
    public void paint(Graphics2D pen) {
        pen.drawImage(myImage, myLocation.x, myLocation.y,
                myImage.getWidth(null), myImage.getHeight(null), null);
    }

    /**
     * Tests whether a particular point is within the bounds of this
     * object. This is determined by seeing if the method argument
     * lies within this object's image.
     * 
     * @param point The point that will be tested to see if it is
     *        within the bounds of this object.
     * @return True if the point lies within this sprite's outline,
     *         false otherwise.
     */
    public boolean contains(Point point) {
        Rectangle outline = getOutline(this);
        return outline.contains(point);
    }

    /**
     * Returns the outline of this sprite. In this case this just returns
     * a rectangle denoting the sprite's image bounds.
     * 
     * @param sprite The sprite whose outline will be computed.
     * @return A shape representing the sprite's bounds.
     */
    private Rectangle getOutline(Sprite sprite) {
        return new Rectangle(sprite.myLocation.x, sprite.myLocation.y,
                sprite.myImage.getWidth(null), sprite.myImage.getHeight(null));
    }

    /**
     * Sets the sprite's position on the screen to a new location.
     * 
     * @param newLocation The location to which the sprite's position
     *        should be set.
     */
    public void setLocation(Point newLocation) {
        myLocation = newLocation;
    }

    /**
     * @return The sprite's current position.
     */
    public Point getLocation() {
        return myLocation;
    }

}
