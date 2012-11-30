package vooga.platformer.gameobject;

import vooga.platformer.gameobject.strategy.GravityStrategy;
import vooga.platformer.gameobject.strategy.PlayerMoveStrategy;
import vooga.platformer.gameobject.strategy.ShootingStrategy;


/**
 * @author Yaqi Zhang
 * @author Zach Michaelov
 */
public class Player extends MovingObject {
    /**
     * @param configString
     */
    public Player (String configString) {
        super(configString);
        addStrategy("PlayerMoveStrategy", new PlayerMoveStrategy(this));
        addStrategy("GravityStrategy",new GravityStrategy(this));
        addStrategy("ShootingStrategy",new ShootingStrategy(this));
    }

    /**
     * 
     * @return the moving strategy of the player
     */
    public PlayerMoveStrategy getMovingStrategy () {

        for (UpdateStrategy s : getStrategyList()) {
            if (s instanceof PlayerMoveStrategy) { return (PlayerMoveStrategy) s; }
        }
        return null;
    }

    /**
     * @return the shooting strategy of the player
     */
    public ShootingStrategy getShootingStrategy () {
        for (UpdateStrategy s : getStrategyList()) {
            if (s.getClass() == ShootingStrategy.class) { return (ShootingStrategy) s; }
        }
        return null;
    }
}
