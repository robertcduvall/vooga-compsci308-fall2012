package vooga.platformer.gameobject.strategy.movement;

import vooga.platformer.gameobject.ControlStrategy;
import vooga.platformer.gameobject.MovingObject;

/**
 * @author Zach Michaelov
 */
public class JumpStrategy implements ControlStrategy {
    private MovingObject myPlayer;
    private static final double VERTICAL_VELOCITY = -7;
    public JumpStrategy(MovingObject myPlayer) {
        this.myPlayer = myPlayer;
    }

    /**
     * when the JumpStrategy fires, the MovingObject jumps upward
     */
    @Override
    public void fire() {
        if (myPlayer.isOnGround()) {
            myPlayer.setVelocity(myPlayer.getVelocity().getX(), VERTICAL_VELOCITY);
            myPlayer.setNotOnGround();
        }
    }
}
