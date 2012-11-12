package vooga.platformer.util.camera;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import util.camera.Camera;
import util.camera.SizedCamera;
import vooga.platformer.util.DimensionDouble;


public class DriftingCamera extends SizedCamera implements Camera {
    private DimensionDouble mySpeed;

    public DriftingCamera(Dimension2D cameraSize, Rectangle2D outerBounds) {
        super(cameraSize, outerBounds);
        setBounds(new Rectangle2D.Double());
    }

    public DriftingCamera(Dimension2D cameraSize, Rectangle2D outerBounds,
            DimensionDouble driftSpeed) {
        super(cameraSize, outerBounds);
        mySpeed = driftSpeed;
        setBounds(new Rectangle2D.Double());
    }

    public DriftingCamera(Dimension2D cameraSize, Rectangle2D outerBounds,
            double xVelocity, double yVelocity) {
        super(cameraSize, outerBounds);
        mySpeed = new DimensionDouble(xVelocity, yVelocity);
        setBounds(new Rectangle2D.Double());
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    public void update(long elapsedTime) {
        Rectangle2D rect = getBounds();
        rect.setRect(rect.getX() + mySpeed.getWidth() * elapsedTime / 1000,
                rect.getY() + mySpeed.getHeight() * elapsedTime / 1000,
                rect.getWidth(), rect.getHeight());
    }

}
