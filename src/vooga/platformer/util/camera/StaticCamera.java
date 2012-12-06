package vooga.platformer.util.camera;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;


public class StaticCamera extends UpdatableCamera {

    public StaticCamera (Dimension2D cameraSize, Rectangle2D outerBounds) {
        super(cameraSize, outerBounds);

    }

    @Override
    public void update (long elapsedTime) {
        
    }

}
