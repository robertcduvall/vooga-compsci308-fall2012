package games.platformerdemo;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.CollisionDirection;


/**
 * @author Niel, modified by Yaqi
 */
public class BrickMovingObject extends CollisionEvent {

    private MovingObject myMovingObject;

    public BrickMovingObject (Brick a, MovingObject b) {
        super(a, b);
        myMovingObject = (MovingObject) this.b();
    }

    public BrickMovingObject (MovingObject a, Brick b) {
        super(b, a);
        myMovingObject = (MovingObject) this.b();
    }

    @Override
    public void applyCollision (Level level) {
        double dy = getIntersectSize().getHeight();
        double dx = getIntersectSize().getWidth();

        if (this.direction() == CollisionDirection.DOWN) {
            myMovingObject.setY(myMovingObject.getY() - dy);
            myMovingObject.setVelocity(myMovingObject.getVelocity().getX(), 0);
        }
        else if (this.direction() == CollisionDirection.UP) {
            myMovingObject.setY(myMovingObject.getY() + dy);
        }
        else if (this.direction() == CollisionDirection.RIGHT) {
            myMovingObject.setX(myMovingObject.getX() - dx);
        }
        else if (this.direction() == CollisionDirection.LEFT) {
            myMovingObject.setX(myMovingObject.getX() + dx);
        }

        // Over here I print out the direction. You can see the problem is that
        // when player and enemy first hit the ground, it detects the direction
        // to be left instead of down.
        System.out.println(direction());
    }

}
