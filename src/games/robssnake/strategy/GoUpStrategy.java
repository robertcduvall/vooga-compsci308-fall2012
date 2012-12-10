package games.robssnake.strategy;

import vooga.platformer.gameobject.ControlStrategy;
import vooga.platformer.gameobject.MovingObject;

public class GoUpStrategy implements ControlStrategy {

    private MovingObject myPlayer;
    private static final double VERTICAL_VELOCITY = 5;

    public GoUpStrategy(MovingObject myPlayer) {
        this.myPlayer = myPlayer;
    }
    
    @Override
    public void fire () {
        myPlayer.setVelocity(0, -VERTICAL_VELOCITY);
    }

}
