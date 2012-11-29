package games.platformerdemo;

import vooga.platformer.gameobject.Brick;
import vooga.platformer.level.Level;

/**
 * @author Probably Niel
 * @author Yaqi
 *
 */
public class BrickEnemy extends BrickMovingObject{
    private Brick myBrick;
    private Enemy myEnemy;

    public BrickEnemy (Brick a, Enemy b) {
        super(a, b);
        myBrick = (Brick) this.a();
        myEnemy = (Enemy) this.b();
    }
    
    public BrickEnemy (Enemy a, Brick b) {
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
