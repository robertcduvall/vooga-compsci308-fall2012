package games.robssnake.strategy;

import vooga.platformer.gameobject.ControlStrategy;
import vooga.platformer.gameobject.MovingObject;

public class GoLeftStrategy implements ControlStrategy {

    private MovingObject myPlayer;
    private static final double HORIZONTAL_VELOCITY = 5;

    public GoLeftStrategy(MovingObject myPlayer) {
        this.myPlayer = myPlayer;
    }

    @Override
    public void fire () {
        myPlayer.setVelocity(-HORIZONTAL_VELOCITY, 0);
    }

}

