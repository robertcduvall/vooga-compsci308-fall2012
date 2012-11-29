package games.platformerdemo;

import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.UpdateStrategy;


/**
 * @author Yaqi Zhang
 * 
 */
public class Player extends MovingObject {
    /**
     * @param configString of the player
     */
    public Player (String configString) {
        super(configString);
        addStrategy(new PlayerMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
        addStrategy(new ShootingStrategy(this));
    }

    // public void moveLeft() {
    // System.out.println("detected left input");
    // }

    /**
     * @return the moving strategy of this player
     */
    public PlayerMoveStrategy getMovingStragety () {
        for (UpdateStrategy s : getStrategyList()) {
            if (s.getClass() == PlayerMoveStrategy.class) { return (PlayerMoveStrategy) s; }
        }
        return null;
    }

    /**
     * @return the shooting strategy of this player
     */
    public ShootingStrategy getShootingStrategy () {
        for (UpdateStrategy s : getStrategyList()) {
            if (s.getClass() == ShootingStrategy.class) { return (ShootingStrategy) s; }
        }
        return null;
    }
}
