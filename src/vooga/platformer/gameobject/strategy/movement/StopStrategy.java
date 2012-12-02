package vooga.platformer.gameobject.strategy.movement;

import vooga.platformer.gameobject.ControlStrategy;
import vooga.platformer.gameobject.MovingObject;

/**
 * @author Zach Michaelov
 */
public class StopStrategy implements ControlStrategy {

    private MovingObject myPlayer;

    public StopStrategy(MovingObject myPlayer) {
        this.myPlayer = myPlayer;
    }

    /**
     * when the StopStrategy fires, the MovingObject stops
     */
    @Override
    public void fire() {
        myPlayer.setVelocity(0, myPlayer.getVelocity().getY());
    }
}
