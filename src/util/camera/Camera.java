package util.camera;

import java.awt.geom.Rectangle2D;


/**
 * The <code>Camera</code> interface is used to keep track of a window within a
 * large region.</br>
 * For instance, in a platformer, a small window of the level is actually
 * painted, and implementations of <code>Camera</code> can update where that
 * window actually is.
 * 
 * @author Mark Govea
 */
public interface Camera {

    /**
     * Updates the <code>Camera</code>. Will update the bounds, and may
     * update other state.
     */
    void update ();

    /**
     * Returns the current bounds.
     * @return The bounds of the <code>Camera</code>.
     */
    Rectangle2D getBounds ();
}
