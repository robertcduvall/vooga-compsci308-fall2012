package util.camera;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

/**
 * The <code>SizedCamera</code> abstract class is used to keeps track of a window
 * of a specific size.
 * large region.</br>
 * For instance, in a platformer, a small window of the level is actually
 * painted, and implementations of <code>Camera</code> can update where that
 * window actually is.
 * @author Mark Govea
 */
public abstract class SizedCamera implements Camera {
    Rectangle2D myBounds;
    Rectangle2D myOuterBounds;
    Dimension2D mySize;
    
    public SizedCamera (Dimension2D cameraSize, Rectangle2D outerBounds) {
        mySize = cameraSize;
        myOuterBounds = outerBounds;
    }

    @Override
    public abstract void update (Object ... args);

    @Override
    public Rectangle2D getBounds () {
        return myBounds;
    }
}
