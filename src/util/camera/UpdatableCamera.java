package util.camera;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

public interface UpdatableCamera extends Camera {
    
    void update (long elapsedTime);
}
