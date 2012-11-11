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

    public FollowingCamera (Dimension2D cameraSize, Rectangle2D outerBounds) {
        super(cameraSize, outerBounds);
    }

    public FollowingCamera (Dimension2D cameraSize, Rectangle2D outerBounds,
                            GameObject target) {
        super(cameraSize, outerBounds);
        myTarget = target;
    }

    public void setTarget (GameObject go) {
        myTarget = go;
    }

    @Override
    public void update () {
        double x =
                myTarget.getShape().getCenterX() - this.size().getWidth() / 2;
        x = Math.max(x, this.outerBounds().getX());
        x =
                Math.min(x, this.outerBounds().getX() +
                            this.outerBounds().getWidth());

        double y =
                myTarget.getShape().getCenterY() - this.size().getHeight() /
                        2;
        y = Math.max(y, this.outerBounds().getY());
        y =
                Math.min(y, this.outerBounds().getY() +
                            this.outerBounds().getHeight());

        double w = size().getWidth();
        double h = size().getHeight();

        this.getBounds().setRect(x, y, w, h);
    }
}
