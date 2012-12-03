package vooga.platformer.util.camera;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import util.camera.Camera;
import util.camera.SizedCamera;

public abstract class UpdatableCamera extends SizedCamera {
    
    public UpdatableCamera (Dimension2D cameraSize, Rectangle2D outerBounds) {
        super(cameraSize, outerBounds);
    }

    public abstract void update (long elapsedTime);
}
