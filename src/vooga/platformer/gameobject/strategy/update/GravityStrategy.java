package vooga.platformer.gameobject.strategy.update;

import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.UpdateStrategy;

import java.awt.geom.Point2D;

/**
 * @author Yaqi Zhang
 *
 */
public class GravityStrategy implements UpdateStrategy{
    private static final double GRAVITY = 0.05;
    private MovingObject myMoveObj;
    /**
     * @param moveObj
     */
    public GravityStrategy(MovingObject moveObj){
        myMoveObj = moveObj;
    }
    @Override
    public void applyAction () {
        Point2D velocity = myMoveObj.getVelocity();
        myMoveObj.setVelocity(velocity.getX(), velocity.getY()+GRAVITY);
    }
}
