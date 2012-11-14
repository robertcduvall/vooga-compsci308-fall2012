package games.platformerdemo;

import java.awt.geom.Rectangle2D;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.CollisionDirection;

public class BrickEnemy extends CollisionEvent{
    private Brick myBrick;
    private Enemy myEnemy;

    public BrickEnemy (Brick a, Enemy b) {
        super(a, b);
        myBrick = (Brick) this.a();
        myEnemy = (Enemy) this.b();
    }
    
    public BrickEnemy (Enemy a, Brick b) {
        super(b, a);
        myBrick = (Brick) this.b();
        myEnemy = (Enemy) this.a();
    }

    @Override
    public void applyCollision (Level level) {
        Rectangle2D intersection = myBrick.getShape()
                .createIntersection(myEnemy.getShape());
        double dy = intersection.getHeight();
        double dx = intersection.getWidth();
        
        if (this.direction() == CollisionDirection.DOWN) {
            myEnemy.setY(myEnemy.getY() - dy);
        }
        else if (this.direction() == CollisionDirection.UP) {
            myEnemy.setY(myEnemy.getY() + dy);
        }
        else if (this.direction() == CollisionDirection.RIGHT) {
            myEnemy.setX(myEnemy.getX() - dx);
        }
        else if (this.direction() == CollisionDirection.LEFT) {
            myEnemy.setX(myEnemy.getX() + dx);
        }
    }

}
