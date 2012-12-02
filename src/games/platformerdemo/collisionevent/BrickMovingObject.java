package games.platformerdemo.collisionevent;

import java.awt.geom.Rectangle2D;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.Direction;


public class BrickMovingObject extends CollisionEvent {

    private StaticObject myBrick;
    private MovingObject myMovingObject;

    public BrickMovingObject (StaticObject a, MovingObject b) {
        super(a, b);
        myBrick = (StaticObject) this.a();
        myMovingObject = (MovingObject) this.b();
    }

    public BrickMovingObject (MovingObject a, StaticObject b) {
        this(b, a);
    }
    @Override
    public void applyCollision (Level level) {
        Rectangle2D intersection = myBrick.getShape().createIntersection(
                myMovingObject.getShape());
        double dy = intersection.getHeight();
        double dx = intersection.getWidth();
        if (direction() == Direction.DOWN) {
            myMovingObject.setY(myMovingObject.getY() - dy);
            // changed here
            myMovingObject.setVelocity(myMovingObject.getVelocity().getX(), 0);
        }
        else if (direction() == Direction.UP) {
            myMovingObject.setY(myMovingObject.getY() + dy);
        }
        if (direction() == Direction.RIGHT) {
            resetCenterRight(dx);
        }
        else if (direction() == Direction.LEFT) {
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
