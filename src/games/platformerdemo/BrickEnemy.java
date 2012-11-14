package games.platformerdemo;

import java.awt.geom.Rectangle2D;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.CollisionDirection;

public class BrickEnemy extends BrickMovingObject{
    private Brick myBrick;
    private Enemy myEnemy;

    public BrickEnemy (Brick a, Enemy b) {
        super(a, b);
        myBrick = (Brick) this.a();
        myEnemy = (Enemy) this.b();
    }
    
    public BrickEnemy (Enemy a, Brick b) {
        super(b, a);
        myBrick = (Brick) this.a();
        myEnemy = (Enemy) this.b();
    }

    @Override
    public void applyCollision (Level level) {
        super.applyCollision(level);
        myEnemy.notifyOnGround();
    }

}
