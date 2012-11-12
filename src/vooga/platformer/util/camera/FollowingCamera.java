package vooga.platformer.util.camera;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import util.camera.Camera;
import util.camera.SizedCamera;
import vooga.platformer.gameobject.GameObject;


/**
 * The <code>FollowingCamera</code> maintains a rectangular window of fixed
 * size within a larger rectangular region, by following a particular
 * GameObject. The center of the window is set to the center of the target
 * GameObject.
 * 
 * @author Mark Govea
 */
public class FollowingCamera extends SizedCamera implements Camera {
    private GameObject myTarget;

    /**
     * Creates a <code>FollowingCamera</code>. It still has no target to follow
     * and needs to have <code>setTarget</code> applied before updating.
     * 
     * @param cameraSize The size of the camera.
     * @param outerBounds The larger region that limits the range of the
     *        camera's bounds.
     */
    public FollowingCamera(Dimension2D cameraSize, Rectangle2D outerBounds) {
        super(cameraSize, outerBounds);
    }

    /**
     * Creates a <code>FollowingCamera</code>. It will follow the inputted
     * target.
     * 
     * @param cameraSize The size of the camera.
     * @param outerBounds The larger region that limits the range of the
     *        camera's bounds.
     * @param target The <code>GameObject</code> for the camera to follow.
     */
    public FollowingCamera(Dimension2D cameraSize, Rectangle2D outerBounds,
            GameObject target) {
        super(cameraSize, outerBounds);
        myTarget = target;
    }

    /**
     * Sets the target that the camera will follow.
     * 
     * @param go <code>GameObject</code> for the camera to follow.
     */
    public void setTarget(GameObject go) {
        myTarget = go;
    }

    @Override
    public void update() {
        double x = myTarget.getShape().getCenterX() - size().getWidth() / 2;
        x = Math.max(x, outerBounds().getX());
        x = Math.min(x, outerBounds().getX() + outerBounds().getWidth()
                - size().getHeight());

        double y = myTarget.getShape().getCenterY() - size().getHeight() / 2;
        y = Math.max(y, outerBounds().getY());
        y = Math.min(y, outerBounds().getY() + outerBounds().getHeight()
                - size().getHeight());

        double w = size().getWidth();
        double h = size().getHeight();

        getBounds().setRect(x, y, w, h);
    }
}
