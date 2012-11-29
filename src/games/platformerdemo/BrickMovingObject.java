package games.platformerdemo;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.Brick;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.CollisionDirection;


<<<<<<< HEAD
/**
 * @author Niel, modified by Yaqi
 */
public class BrickMovingObject extends CollisionEvent {

=======
public class BrickMovingObject extends CollisionEvent {

    private Brick myBrick;
>>>>>>> dfa01f4364ac425da153d33ce42b92a5417d480b
    private MovingObject myMovingObject;

    public BrickMovingObject (Brick a, MovingObject b) {
        super(a, b);
        myMovingObject = (MovingObject) this.b();
    }

    public BrickMovingObject (MovingObject a, Brick b) {
<<<<<<< HEAD
        super(b, a);
        myMovingObject = (MovingObject) this.b();
=======
        this(b, a);
>>>>>>> dfa01f4364ac425da153d33ce42b92a5417d480b
    }

    @Override
    public void applyCollision (Level level) {
<<<<<<< HEAD
        double dy = getIntersectSize().getHeight();
        double dx = getIntersectSize().getWidth();

        if (this.direction() == CollisionDirection.DOWN) {
            myMovingObject.setY(myMovingObject.getY() - dy);
=======
        Rectangle2D intersection = myBrick.getShape().createIntersection(
                myMovingObject.getShape());
        double dy = intersection.getHeight();
        double dx = intersection.getWidth();

        if (this.direction() == CollisionDirection.DOWN) {
            myMovingObject.setY(myMovingObject.getY() - dy);
            // changed here
>>>>>>> dfa01f4364ac425da153d33ce42b92a5417d480b
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
<<<<<<< HEAD

        // Over here I print out the direction. You can see the problem is that
        // when player and enemy first hit the ground, it detects the direction
        // to be left instead of down.
        System.out.println(direction());
=======
        myMovingObject.setOnGround();
    }

    protected void resetCenterLeft (double dx) {
        myMovingObject.setX(myMovingObject.getX() + dx);
    }

    protected void resetCenterRight (double dx) {
        myMovingObject.setX(myMovingObject.getX() - dx);
>>>>>>> dfa01f4364ac425da153d33ce42b92a5417d480b
    }

}
