package vooga.platformer.gameobject;

import vooga.platformer.gameobject.strategy.GravityStrategy;
import vooga.platformer.gameobject.strategy.MovementUpdateStrategy;
import vooga.platformer.gameobject.strategy.PlayerMoveStrategy;
import vooga.platformer.gameobject.strategy.ShootingStrategy;
import vooga.platformer.gameobject.strategy.movement.GoLeftStrategy;
import vooga.platformer.gameobject.strategy.movement.GoRightStrategy;
import vooga.platformer.gameobject.strategy.movement.JumpStrategy;
import vooga.platformer.gameobject.strategy.movement.StopStrategy;

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
        initStrategies();
    }
    private void initStrategies() {
        // this Update strategy will update MovingObject's positions based on their velocities
        myPlayer.addStrategy("MovementUpdateStrategy", new MovementUpdateStrategy(myPlayer));
        // TODO migrate Gravity to level
        myPlayer.addStrategy("GravityStrategy", new GravityStrategy(myPlayer));

        // add the Player's Control Strategies
        this.addControlStrategy("Jump", new JumpStrategy(myPlayer));
        this.addControlStrategy("GoLeft", new GoLeftStrategy(myPlayer));
        this.addControlStrategy("GoRight", new GoRightStrategy(myPlayer));
        this.addControlStrategy("Stop", new StopStrategy(myPlayer));
        this.addControlStrategy("Shoot", new ShootingStrategy(myPlayer))
    }
    public Player(MovingObject myPlayer) {
        this.myPlayer = myPlayer;
        initStrategies();
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

    /**
     * Triggers the specified Control Strategy
     * @param name the name of the ControlStrategy we want to trigger
     */
    public void fireControlStrategy(String name) {
        controlStrategies.get(name).fire();
    }
}
