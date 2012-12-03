package games.platformerdemo.collisionevent;

import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.gameobject.MovingObject;


/**
 * @author Probably Niel
 * @author Yaqi
 * 
 */
public class BrickBounceBack extends BrickMovingObject {

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
