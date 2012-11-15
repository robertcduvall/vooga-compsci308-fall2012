package games.platformerdemo;

import vooga.platformer.gameobject.UpdateStrategy;

public class SimpleMoveStrategy implements UpdateStrategy{
    private MovingObject myMoveObj;

    /**
     * @param player GameObject
     */
    public SimpleMoveStrategy (MovingObject moveObj) {
        myMoveObj = moveObj;
    }
    
    @Override
    public void applyAction () {
        myMoveObj.setX(myMoveObj.getVelocity().getX() + myMoveObj.getX());
        myMoveObj.setY(myMoveObj.getVelocity().getY() + myMoveObj.getY());
    }
}
