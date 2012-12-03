package vooga.platformer.util.camera;

import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import util.camera.Camera;
import util.camera.SizedCamera;
import vooga.platformer.util.Constants;
import vooga.platformer.util.DimensionDouble;

/**
 * The <code>DriftingCamera</code> maintains a rectangular window of fixed
 * size within a larger rectangular region and moves at a desired speed.
 * 
 * @author Mark Govea
 */
public class DriftingCamera extends UpdatableCamera {
    private DimensionDouble mySpeed;

    /**
     * Creates a <code>DriftingCamera</code>. It still has no drift speed
     * and needs to have <code>setSpeed</code> applied before updating.
     * 
     * @param cameraSize The size of the camera.
     * @param outerBounds The larger region that limits the range of the
     *        camera's bounds.
     */
    public DriftingCamera (Dimension2D cameraSize, Rectangle2D outerBounds) {
        super(cameraSize, outerBounds);
        setBounds(new Rectangle2D.Double());
    }

    /**
     * Creates a <code>DriftingCamera</code>. It will move at the inputed speed.
     * 
     * @param cameraSize The size of the camera.
     * @param outerBounds The larger region that limits the range of the
     *        camera's bounds.
     * @param driftSpeed The speed at which the camera will move measured in
     *        pixels per second.
     */
    public DriftingCamera (Dimension2D cameraSize, Rectangle2D outerBounds,
                           DimensionDouble driftSpeed) {
        super(cameraSize, outerBounds);
        mySpeed = driftSpeed;
        setBounds(new Rectangle2D.Double());
    }

    /**
     * Creates a <code>DriftingCamera</code>. It will move at the inputed speed.
     * 
     * @param cameraSize The size of the camera.
     * @param outerBounds The larger region that limits the range of the
     *        camera's bounds.
     * @param xVelocity The x component of the speed at which the camera will move measured in
     *        pixels per second.
     * @param yVelocity The y component of the speed at which the camera will move measured in
     *        pixels per second.
     */
    public DriftingCamera (Dimension2D cameraSize, Rectangle2D outerBounds,
                           double xVelocity, double yVelocity) {
        super(cameraSize, outerBounds);
        mySpeed = new DimensionDouble(xVelocity, yVelocity);
        setBounds(new Rectangle2D.Double());
    }

    /**
     * Sets the speed of the <code>DriftingCamera</code> to the inputed speed.
     * 
     * @param driftSpeed The speed at which the camera will move measured in
     *        pixels per second.
     */
    public void setSpeed (DimensionDouble driftSpeed) {
        mySpeed = driftSpeed;
    }

    /**
     * Sets the speed of the <code>DriftingCamera</code> to the inputed speed.
     * 
     * @param xVelocity The x component of the speed at which the camera will move measured in
     *        pixels per second.
     * @param yVelocity The y component of the speed at which the camera will move measured in
     *        pixels per second.
     */
    public void setSpeed (double xVelocity, double yVelocity) {
        mySpeed = new DimensionDouble(xVelocity, yVelocity);
    }

    /**
     * Sets the position of the top-left corner of the camera. Used for
     * initialization.
     * 
     * @param x Desired x coordinate of the top-left corner.
     * @param y Desired y coordinate of the top-left corner.
     */
    public void setPosition (double x, double y) {
        super.getBounds().setRect(x, y,
                                  getBounds().getWidth(),
                                  getBounds().getHeight());
        obeyOuterBounds();
    }

    /**
     * Updates the <code>FollowingCamera</code>. Will update the bounds.
     * 
     * @param elapsedTime Time elapsed since last update call. Used for updating
     *        the <code>FollowingCamera</code>'s position.
     */
    public void update (long elapsedTime) {
        Rectangle2D rect = getBounds();
        rect.setRect(rect.getX() + mySpeed.getWidth() *
                     elapsedTime / Constants.MILLISECONDS_PER_SECOND,
                     rect.getY() + mySpeed.getHeight() *
                     elapsedTime / Constants.MILLISECONDS_PER_SECOND,
                     rect.getWidth(), rect.getHeight());
        obeyOuterBounds();
    }

}
