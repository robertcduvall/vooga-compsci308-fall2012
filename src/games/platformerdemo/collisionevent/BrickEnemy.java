package games.platformerdemo.collisionevent;

import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.Enemy;

/**
 * @author Probably Niel
 * @author Yaqi
 *
 */
public class BrickEnemy extends BrickMovingObject{
    private StaticObject myBrick;
    private Enemy myEnemy;

    public BrickEnemy (StaticObject a, Enemy b) {
        super(a, b);
        myBrick = (StaticObject) this.a();
        myEnemy = (Enemy) this.b();
    }
    
    public BrickEnemy (Enemy a, StaticObject b) {
        this(b, a);
    }
    
    @Override
    protected void resetCenterLeft (double dx) {
        super.resetCenterLeft(dx);
        flipHorizontalVelocity();
    }
    
    protected void resetCenterRight (double dx) {
        super.resetCenterRight(dx);
        flipHorizontalVelocity();
    }
    
    private void flipHorizontalVelocity(){
        myEnemy.setVelocity(-myEnemy.getVelocity().getX(), myEnemy.getVelocity().getY());
    }
}
