package vooga.platformer.gameobject.strategy.movement;

import vooga.platformer.gameobject.ControlStrategy;
import vooga.platformer.gameobject.MovingObject;

/**
 * @author Zach Michaelov
 */
public class GoLeftStrategy implements ControlStrategy {
    private MovingObject myPlayer;
    private static final double HORIZONTAL_VELOCITY = 5;

    public GoLeftStrategy(MovingObject myPlayer) {
        this.myPlayer = myPlayer;
    }

    /**
     * when the GoLeftStrategy fires, the MovingObject is updated to move left
     */
    @Override
    public void fire() {
        myPlayer.setVelocity(-HORIZONTAL_VELOCITY, myPlayer.getVelocity().getY());
    }
}
