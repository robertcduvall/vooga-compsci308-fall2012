package games.platformerdemo;

import java.awt.geom.Rectangle2D;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.CollisionDirection;

public class BrickMovingObject extends CollisionEvent{
    
    private Brick myBrick;
    private MovingObject myMovingObject;

    public BrickMovingObject (Brick a, MovingObject b) {
        super(a, b);
        myBrick = (Brick) this.a();
        myMovingObject = (MovingObject) this.b();
    }
    
    public BrickMovingObject (MovingObject a, Brick b) {
        super(b, a);
        myBrick = (Brick) this.a();
        myMovingObject = (MovingObject) this.b();
    }

    @Override
    public void applyCollision (Level level) {
        Rectangle2D intersection = myBrick.getShape()
                .createIntersection(myMovingObject.getShape());
        double dy = intersection.getHeight();
        double dx = intersection.getWidth();
        
        if (this.direction() == CollisionDirection.DOWN) {
            myMovingObject.setY(myMovingObject.getY() - dy);
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
    }

}