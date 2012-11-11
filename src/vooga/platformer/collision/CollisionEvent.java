package vooga.platformer.collision;

import java.awt.geom.Rectangle2D;
import vooga.platformer.core.Level;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.util.enums.CollisionDirection;


/**
 * For collisions happen between any pair of game objects there would be a
 * specific collision event to deal with that. This CollisionEvent is a super
 * class for all the other collision events.</br> <code>CollisionEvent</code>
 * contains the two colliding <code>GameObject</code>s and the direction of the
 * collision. The subclasses will implement <code>applyCollision</code> which
 * handles the logic for resolving the collision.
 * 
 * @author Bruce, Mark Govea
 * 
 */
public abstract class CollisionEvent {
    private GameObject a;
    private GameObject b;
    private CollisionDirection myDirection;

    public CollisionEvent (GameObject a, GameObject b) {
        this.a = a;
        this.b = b;
        computeDirection();
    }

    public abstract void applyCollision (Level level);

    protected GameObject a () {
        return a;
    }

    protected GameObject b () {
        return b;
    }

    protected CollisionDirection direction () {
        return myDirection;
    }

    private void computeDirection () {
        Rectangle2D intersection = a.getShape().intersection(b.getShape());

        // lateral collision ?
        if (intersection.getHeight() > intersection.getWidth()) {
            if (a.left() > b.left()) { // determine collision direction
                myDirection = CollisionDirection.RIGHT;
            }
            else {
                myDirection = CollisionDirection.LEFT;
            }
        }
        // vertical collision
        else {
            if (a.top() > b.top()) { // determine collision direction
                myDirection = CollisionDirection.DOWN;
            }
            else {
                myDirection = CollisionDirection.UP;
            }
        }
    }
}
