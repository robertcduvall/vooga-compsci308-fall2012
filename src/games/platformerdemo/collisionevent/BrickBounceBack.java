package games.platformerdemo.collisionevent;

import games.platformerdemo.Enemy;
import vooga.platformer.gameobject.Brick;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.level.Level;


/**
 * @author Probably Niel
 * @author Yaqi
 * 
 */
public class BrickBounceBack extends BrickMovingObject {
    private Brick myBrick;
    private MovingObject myMO;

    public BrickBounceBack (Brick a, MovingObject b) {
        super(a, b);
        myBrick = (Brick) this.a();
        myMO = (MovingObject) this.b();
    }

    public BrickBounceBack (MovingObject a, Brick b) {
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

    private void flipHorizontalVelocity () {
        myMO.setVelocity(-myMO.getVelocity().getX(), myMO.getVelocity().getY());
    }
}
