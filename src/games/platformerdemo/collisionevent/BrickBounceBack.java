package games.platformerdemo.collisionevent;

import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.MovingObject;


/**
 * @author Probably Niel
 * @author Yaqi
 * 
 */
public class BrickBounceBack extends BrickMovingObject {

    public BrickBounceBack (Class typeA, Class typeB) {
        super(typeA, typeB);
    }

    @Override
    protected void resetCenterLeft (MovingObject mo, double dx) {
        super.resetCenterLeft(mo,dx);
        flipHorizontalVelocity(mo);
    }

    @Override
    protected void resetCenterRight (MovingObject mo, double dx) {
        super.resetCenterRight(mo);
        flipHorizontalVelocity(mo);
    }

    private void flipHorizontalVelocity (MovingObject mo) {
        ((MovingObject) mo).setVelocity(-((MovingObject) mo).getVelocity().getX(),
                ((MovingObject) mo).getVelocity().getY());
    }
}
