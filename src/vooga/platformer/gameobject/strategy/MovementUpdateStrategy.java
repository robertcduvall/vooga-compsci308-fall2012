package vooga.platformer.gameobject.strategy;

import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.UpdateStrategy;
import vooga.platformer.util.enums.Direction;


/**
 * Updates a MovingObject's movement (position and direction) based
 * on its velocity
 * @author Yaqi Zhang
 * @author Zach Michaelov (modified)
 */
public class MovementUpdateStrategy implements UpdateStrategy {
    private MovingObject myMoveObj;

    /**
     * @param moveObj MovingObject
     */
    public MovementUpdateStrategy(MovingObject moveObj) {
        myMoveObj = moveObj;
    }

    /**
     * updates the MovingObject's position and direction
     */
    @Override
    public void applyAction () {
        myMoveObj.setX(myMoveObj.getVelocity().getX() + myMoveObj.getX());
        myMoveObj.setY(myMoveObj.getVelocity().getY() + myMoveObj.getY());
        if (myMoveObj.getHorizontalMovingDirection().equals(Direction.LEFT)) {
            myMoveObj.setFacingDirection(Direction.LEFT);
        }
        else if (myMoveObj.getHorizontalMovingDirection().equals(
                Direction.RIGHT)) {
            myMoveObj.setFacingDirection(Direction.RIGHT);
        }
    }
}
