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
    private Direction myDirection = Direction.NONE;
    private Dimension2D myIntersectSize = new Dimension();

    public CollisionEvent (Class typeA, Class typeB) {
        myTypeA = typeA;
        myTypeB = typeB;
    }

    public abstract void applyCollision (Level level, GameObject objectA, GameObject objectB);
    
    public void apply(Level level, GameObject objectA, GameObject objectB){
        if(myTypeA.equals(objectA.getClass()) && myTypeB.equals(objectB.getClass())) {
            computeDirection(objectA, objectB);
            applyCollision(level, objectA, objectB);
        }
        else if (myTypeB.equals(objectA.getClass()) && myTypeA.equals(objectB.getClass())) {
            computeDirection(objectB, objectA);
            applyCollision(level, objectB, objectA);
        }
    }

    protected Direction direction () {
        return myDirection;
    }

    private void computeDirection (GameObject objA, GameObject objB) {
        Rectangle2D intersection = objA.getShape()
                .createIntersection(objB.getShape());
        myIntersectSize.setSize(intersection.getWidth(),
                intersection.getHeight());
        // lateral collision ?
        if (myIntersectSize.getHeight() > myIntersectSize.getWidth()) {
            if (objA.getX() > objB.getX()) { // determine collision direction
                myDirection = Direction.RIGHT;
            }
            else if (objA.getX() < objB.getX()){
                myDirection = Direction.LEFT;
            }
        }
        // vertical collision
        else {
            if (objA.getY() > objB.getY()) { // determine collision direction
                myDirection = Direction.DOWN;
            }
            else if (objA.getY() < objB.getY()){
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