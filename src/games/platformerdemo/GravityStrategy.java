package games.platformerdemo;

import java.awt.geom.Point2D;
import vooga.platformer.gameobject.UpdateStrategy;

/**
 * @author Yaqi Zhang
 *
 */
public class GravityStrategy implements UpdateStrategy{
    private static final double GRAVITY = 0.02;
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
