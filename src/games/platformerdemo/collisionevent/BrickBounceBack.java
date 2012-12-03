package games.platformerdemo.collisionevent;

import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.MovingObject;


/**
 * @author Probably Niel
 * @author Yaqi
 * 
 */
public class BrickBounceBack extends BrickMovingObject {
    private StaticObject myBrick;
    private MovingObject myMO;

    public BrickBounceBack (StaticObject a, MovingObject b) {
        super(a, b);
        myBrick = (StaticObject) this.a();
        myMO = (MovingObject) this.b();
    }

    public BrickBounceBack (MovingObject a, StaticObject b) {
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
