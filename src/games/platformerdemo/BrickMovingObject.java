package games.platformerdemo;

import java.awt.geom.Rectangle2D;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.Brick;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.CollisionDirection;


/**
 * @author Niel, modified by Yaqi
 */
public class BrickMovingObject extends CollisionEvent {
    private Brick myBrick;
    private MovingObject myMovingObject;
    public BrickMovingObject (Brick a, MovingObject b) {
        super(a, b);
        myMovingObject = (MovingObject) this.b();
    }

    public BrickMovingObject (MovingObject a, Brick b) {
        this(b, a);
    }

    @Override
    public void applyCollision (Level level) {
        Rectangle2D intersection = myBrick.getShape().createIntersection(
                myMovingObject.getShape());
        double dy = intersection.getHeight();
        double dx = intersection.getWidth();

        if (this.direction() == CollisionDirection.DOWN) {
            myMovingObject.setY(myMovingObject.getY() - dy);
            // changed here
            myMovingObject.setVelocity(myMovingObject.getVelocity().getX(), 0);
        }
        else if (this.direction() == CollisionDirection.UP) {
            myMovingObject.setY(myMovingObject.getY() + dy);
        }
        if (this.direction() == CollisionDirection.RIGHT) {
            resetCenterRight(dx);
        }
        else if (this.direction() == CollisionDirection.LEFT) {
            resetCenterLeft(dx);
        }
        myMovingObject.setOnGround();
    }

    protected void resetCenterLeft (double dx) {
        myMovingObject.setX(myMovingObject.getX() + dx);
    }

    protected void resetCenterRight (double dx) {
        myMovingObject.setX(myMovingObject.getX() - dx);
    }

}
