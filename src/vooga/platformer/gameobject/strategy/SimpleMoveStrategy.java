package vooga.platformer.gameobject.strategy;

import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.UpdateStrategy;
import vooga.platformer.util.enums.Direction;


/**
 * @author Yaqi Zhang
 * 
 */
public class SimpleMoveStrategy implements UpdateStrategy {
    private MovingObject myMoveObj;

    /**
     * @param moveObj MovingObject
     */
    public SimpleMoveStrategy (MovingObject moveObj) {
        myMoveObj = moveObj;
    }

    @Override
    public void applyAction () {
        myMoveObj.setX(myMoveObj.getVelocity().getX() + myMoveObj.getX());
        myMoveObj.setY(myMoveObj.getVelocity().getY() + myMoveObj.getY());
        if (myMoveObj.getHorizontalMovingDirection().equals(Direction.LEFT)) {
            myMoveObj.setFacingDirection(Direction.LEFT);
        }
        else if (myMoveObj.getHorizontalMovingDirection().equals(Direction.RIGHT)){
            myMoveObj.setFacingDirection(Direction.RIGHT);
        }
    }
}
