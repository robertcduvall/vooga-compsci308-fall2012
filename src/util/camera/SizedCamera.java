package util.camera;

import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;


/**
 * The <code>SizedCamera</code> abstract class is used to keep track of a
 * rectangular window of a specific size within a larger rectangular region.
 * 
 * @author Mark Govea
 * @author last-minute modifications by Niel
 */
public abstract class SizedCamera implements UpdatableCamera {
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
    public SizedCamera(Dimension2D cameraSize, Rectangle2D outerBounds) {
        mySize = (Dimension2D) cameraSize.clone();
        myOuterBounds = (Rectangle2D) outerBounds.clone();
        myBounds = new Rectangle(0, 0, (int) mySize.getWidth(),
                (int) mySize.getHeight());
    }
    
    public void update(long elapsedTime) {
        obeyOuterBounds();
    }

    @Override
    public Rectangle2D getBounds() {
        if (myOuterBounds.contains(myBounds)) {
            return myBounds;
        }
        //obeyOuterBounds();
        return myBounds;
    }

    /**
     * Sets the bounds of the camera. Used in construction.
     * 
     * @param bounds Rectangle used to set bounds.
     */
    protected void setBounds(Rectangle2D bounds) {
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
    protected Dimension2D getSize () {
        return mySize;
    }

    /**
     * Ensures that the <code>SizedCamera</code>'s bounds are inside its outer
     * bounds.</br>If the outer bounds are smaller than the camera bounds,
     * the camera's top-left corner will be set to the outer bounds' top-left
     * corner.
     */
    protected void obeyOuterBounds () {
        double x = Math.min(getBounds().getX(),
                            outerBounds().getX() +
                            outerBounds().getWidth() -
                            getSize().getHeight());
        x = Math.max(x, outerBounds().getX());

        double y = Math.min(getBounds().getY(),
                            outerBounds().getY() +
                            outerBounds().getHeight() -
                            getSize().getHeight());
        y = Math.max(y, outerBounds().getY());
        getBounds().setRect(x, y, getSize().getWidth(), getSize().getHeight());
    }
}
