package vooga.platformer.collision;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.Direction;


/**
 * For collisions happen between any pair of game objects there would be a
 * specific collision event to deal with that. This CollisionEvent is a super
 * class for all the other collision events.</br><code>CollisionEvent</code>
 * contains the two colliding <code>GameObject</code>s and the direction of the
 * collision. The subclasses will implement <code>applyCollision</code> which
 * handles the logic for resolving the collision.
 * 
 * @author Mark Govea, Bruce Fu
 * 
 */
public abstract class CollisionEvent {
    private Class myTypeA;
    private Class myTypeB;
    private GameObject myObjectA;
    private GameObject myObjectB;
    private Direction myDirection;
    private Dimension2D myIntersectSize = new Dimension();;

    public CollisionEvent (GameObject objectA, GameObject objectB) {
        myTypeA = objectA.getClass();
        myTypeB = objectB.getClass();
        myObjectA = objectA;
        myObjectB = objectB;
        computeDirection();
    }

    public abstract void applyCollision (Level level, GameObject objectA, GameObject objectB);
    
    public void apply(Level level, GameObject objectA, GameObject objectB){
        if(myTypeA.equals(objectA.getClass()) && myTypeB.equals(objectB.getClass())) {
            applyCollision(level, objectA, objectB);
        }
        else {
            applyCollision(level, objectB, objectA);
        }
    }

    protected GameObject a () {
        return myObjectA;
    }

    protected GameObject b () {
        return myObjectB;
    }

    protected Direction direction () {
        return myDirection;
    }

    private void computeDirection () {
        Rectangle2D intersection = myObjectA.getShape()
                .createIntersection(myObjectB.getShape());
        myIntersectSize.setSize(intersection.getWidth(),
                intersection.getHeight());
        // lateral collision ?
        if (myIntersectSize.getHeight() > myIntersectSize.getWidth()) {
            if (myObjectA.getX() > myObjectB.getX()) { // determine collision direction
                myDirection = Direction.RIGHT;
            }
            else {
                myDirection = Direction.LEFT;
            }
        }
        // vertical collision
        else {
            if (myObjectA.getY() > myObjectB.getY()) { // determine collision direction
                myDirection = Direction.DOWN;
            }
            else {
                myDirection = Direction.UP;
            }
        }
    }

    /**
     * @return the size of the intersection rectangular.
     */
    public Dimension2D getIntersectSize () {
        return myIntersectSize;
    }
}