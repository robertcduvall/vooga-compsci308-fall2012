package vooga.platformer.collision.collisionevent;

import vooga.platformer.gameobject.MovingObject;


/**
 * Type B bounces back when hits Type A.
 * 
 * @author Niel
 * @author Yaqi
 * 
 */
public class BrickBounceBack extends BrickMovingObject {

    public BrickBounceBack (Class typeA, Class typeB) {
        super(typeA, typeB);
    }

    @Override
    protected void resetCenterLeft (MovingObject mo, double dx) {
        super.resetCenterLeft(mo, dx);
        flipHorizontalVelocity(mo);
    }

    @Override
    protected void resetCenterRight (MovingObject mo, double dx) {
        super.resetCenterRight(mo, dx);
        flipHorizontalVelocity(mo);
    }

    private void flipHorizontalVelocity (MovingObject mo) {
        mo.setVelocity(-mo.getVelocity().getX(), mo.getVelocity().getY());
    }
}
