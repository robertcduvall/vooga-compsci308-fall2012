package games.robssnake.strategy;

import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.UpdateStrategy;

public class DoNothingUpdateStrategy implements UpdateStrategy {
    private MovingObject myMoveObj;

    public DoNothingUpdateStrategy(MovingObject moveObj){
        myMoveObj = moveObj;
    }
    @Override
    public void applyAction () {
        // Do nothing! Whoo!
    }
}