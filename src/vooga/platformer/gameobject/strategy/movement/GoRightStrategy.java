package vooga.platformer.gameobject.strategy.movement;

import vooga.platformer.gameobject.ControlStrategy;
import vooga.platformer.gameobject.MovingObject;

public class GoRightStrategy implements ControlStrategy {
    private MovingObject myPlayer;
    private static final double HORIZONTAL_VELOCITY = 5;

    public GoRightStrategy(MovingObject myPlayer) {
        this.myPlayer = myPlayer;
    }

    /**
     * when the GoRightStrategy fires, the MovingObject is updated to move right
     */
    @Override
    public void fire() {
        myPlayer.setVelocity(HORIZONTAL_VELOCITY, myPlayer.getVelocity().getY());
    }
}