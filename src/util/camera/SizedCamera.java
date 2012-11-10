package util.camera;

import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;


/**
 * The <code>SizedCamera</code> abstract class is used to keeps track of a
 * rectangular window of a specific size within a larger rectangular region.
 * 
 * @author Mark Govea
 */
public abstract class SizedCamera implements Camera {
    private Rectangle2D myBounds;
    private Rectangle2D myOuterBounds;
    private Dimension2D mySize;

    /**
     * Creates a new <code>SizedCamera</code> by cloning the inputs and making
     * the bounds of the camera a <code>java.awt.Rectangle</code> of the correct
     * size.
     * 
     * @param cameraSize The size of the <code>SizedCamera</code>.
     * @param outerBounds The larger region that limits the range of the
     *        camera's bounds.
     */
    public SizedCamera (Dimension2D cameraSize, Rectangle2D outerBounds) {
        mySize = (Dimension2D) cameraSize.clone();
        myOuterBounds = (Rectangle2D) outerBounds.clone();
        myBounds =
                new Rectangle(0, 0, (int) mySize.getWidth(),
                              (int) mySize.getHeight());
    }

    @Override
    public abstract void update ();

    @Override
    public Rectangle2D getBounds () {
        return myBounds;
    }

    /**
     * Sets the bounds of the camera. Used in construction.
     * 
     * @param bounds Rectangle used to set bounds.
     */
    protected void setBounds (Rectangle2D bounds) {
        myBounds = bounds;
    }

    /**
     * Returns the outer bounding box which this camera will stay within.
     * 
     * @return The <code>SizedCamera</code>'s outer bounds.
     */
    protected Rectangle2D outerBounds () {
        return myOuterBounds;
    }

    /**
     * Returns the size of the camera's window.
     * 
     * @return The <code>SizedCamera</code>'s size.
     */
    protected Dimension2D size () {
        return mySize;
    }
}
