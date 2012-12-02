package vooga.platformer.gameobject;

import java.awt.geom.Point2D;

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

    /**
     * @param configString String to parse parameters of this player
     */
    public MovingObject (String configString) {
        super(configString);
        myVelocity = new Point2D.Double(0, 0);
    }

    /**
     * 
     */
    public MovingObject () {
        super();
        myVelocity = new Point2D.Double(0, 0);
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
