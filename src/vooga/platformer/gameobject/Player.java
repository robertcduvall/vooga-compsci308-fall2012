package vooga.platformer.gameobject;

import vooga.platformer.gameobject.strategy.GravityStrategy;
import vooga.platformer.gameobject.strategy.PlayerMoveStrategy;
import vooga.platformer.gameobject.strategy.ShootingStrategy;
import vooga.platformer.gameobject.strategy.movement.JumpStrategy;

import java.util.HashMap;
import java.util.Map;


/**
 * A Player is a MovingObject composed with Strategies
 *
 * @author Yaqi Zhang
 * @author Zach Michaelov
 */
public class Player {
    /**
     * the Moving Object Representing the Player
     */
    private MovingObject myPlayer;
    /**
     * the control strategies applied to the myPlayer
     */
    private Map<String, ControlStrategy> controlStrategies;


    /**
     * @param configString
     */
    public Player(String configString) {
        controlStrategies = new HashMap<String, ControlStrategy>();
        myPlayer = new MovingObject(configString);
        myPlayer.addStrategy("PlayerMoveStrategy", new PlayerMoveStrategy(this));
        myPlayer.addStrategy("GravityStrategy", new GravityStrategy(myPlayer));
        this.addControlStrategy("Jump", new JumpStrategy(myPlayer));
//        addStrategy("ShootingStrategy",new ShootingStrategy(this));
    }

    /**
     * @return the moving strategy of the myPlayer
     */
    public PlayerMoveStrategy getMovingStrategy() {
        return (PlayerMoveStrategy) myPlayer.getStrategy("PlayerMoveStrategy");
    }

    /**
     * @return the shooting strategy of the myPlayer
     */
    public ShootingStrategy getShootingStrategy() {
        return (ShootingStrategy) myPlayer.getStrategy("ShootingStrategy");
    }

    /**
     * adds the specified controlStrategy
     * @param controlStrategy the ControlStrategy we want to add to the Player
     */
    public void addControlStrategy(String name, ControlStrategy controlStrategy) {
        controlStrategies.put(name, controlStrategy);
    }
}
