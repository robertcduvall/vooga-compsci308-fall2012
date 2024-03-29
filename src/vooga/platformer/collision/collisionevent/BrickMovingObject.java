package vooga.platformer.collision.collisionevent;

import java.awt.geom.Rectangle2D;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.Direction;


public class BrickMovingObject extends CollisionEvent {

    public BrickMovingObject (Class typeA, Class typeB) {
        super(typeA, typeB);
    }

    /**
     * gameObjectA is a StaticObject, gameObjectB is a MovingObject
     */
    @Override
    public void applyCollision (Level level, GameObject gameObjectA, GameObject gameObjectB) {
        GameObject brick = gameObjectA;
        MovingObject moveObj = (MovingObject) gameObjectB;
        Rectangle2D intersection = brick.getShape().createIntersection(
                moveObj.getShape());
        double dy = intersection.getHeight();
        double dx = intersection.getWidth();
        if (direction() == Direction.DOWN) {
            moveObj.setY(moveObj.getY() - dy);
            // changed here
            moveObj.setVelocity(moveObj.getVelocity().getX(), 0);
            moveObj.setOnGround();
        }
        else if (direction() == Direction.UP) {
            moveObj.setY(moveObj.getY() + dy);
            moveObj.setVelocity(moveObj.getVelocity().getX(), 0);
        }
        if (direction() == Direction.RIGHT) {
            resetCenterRight(moveObj, dx);
        }
        else if (direction() == Direction.LEFT) {
            resetCenterLeft(moveObj, dx);
        }
    }

    protected void resetCenterLeft (MovingObject gameObj, double dx) {
        gameObj.setX(gameObj.getX() + dx);
    }

    protected void resetCenterRight (MovingObject gameObj, double dx) {
        gameObj.setX(gameObj.getX() - dx);
    }
}
