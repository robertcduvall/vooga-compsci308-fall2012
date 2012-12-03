package vooga.platformer.gameobject;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import vooga.platformer.gameobject.strategy.update.MovementUpdateStrategy;
import vooga.platformer.util.enums.Direction;


/**
 * @author Yaqi Zhang
 * @modified Niel Lebeck
 * 
 */
public class MovingObject extends GameObject {

    private Point2D myVelocity;
    private boolean onGround = false;
    private Direction myFacingDirection = Direction.RIGHT;

    public MovingObject(double inX, double inY, double inWidth, double inHeight, int inId, File defaultImageFile) throws IOException {
        super(inX, inY, inWidth, inHeight, inId, defaultImageFile);
        myVelocity = new Point2D.Double(0, 0);
        // all MovingObjects have a MovementUpdateStrategy
        this.addStrategy("MovementUpdateStrategy", new MovementUpdateStrategy(this));
    }

    /**
     * @return the velocity of the player
     */
    public Point2D getVelocity () {
        return myVelocity;
    }

    /**
     * @param x horizontal velocity
     * @param y vertical velocity
     */
    public void setVelocity (double x, double y) {
        myVelocity = new Point2D.Double(x, y);
    }

    /**
     * set status of this moving object to be on the ground
     */
    public void setOnGround () {
        onGround = true;
    }

    /**
     * set status of this moving object to be not on the ground
     */
    public void setNotOnGround () {
        onGround = false;
    }

    /**
     * @return whether this MovingObject is on a brick
     */
    public boolean isOnGround () {
        return onGround;
    }

    /**
     * @return the current vertical moving direction of the object
     */
    public Direction getVerticalMovingDirection () {
        if (myVelocity.getY() > 0) {
            return Direction.DOWN;
        }
        else if (myVelocity.getY() < 0) {
            return Direction.UP;
        }
        else {
            return Direction.STILL;
        }
    }

    /**
     * @return the current horizontal moving direction of the object
     */
    public Direction getHorizontalMovingDirection () {
        if (myVelocity.getX() > 0) {
            return Direction.RIGHT;
        }
        else if (myVelocity.getX() < 0) {
            return Direction.LEFT;
        }
        else {
            return Direction.STILL;
        }
    }

    /**
     * @return the facing direction of the object
     */
    public Direction getFacingDirection () {
        return myFacingDirection;
    }

    /**
     * @param direction Facing Direction of the object
     */
    public void setFacingDirection (Direction direction) {
        myFacingDirection = direction;
    }
}
