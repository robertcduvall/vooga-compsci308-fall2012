package vooga.platformer.gameobject;

import vooga.platformer.gameobject.strategy.update.GravityStrategy;
import vooga.platformer.gameobject.strategy.ShootingStrategy;
import vooga.platformer.gameobject.strategy.movement.GoLeftStrategy;
import vooga.platformer.gameobject.strategy.movement.GoRightStrategy;
import vooga.platformer.gameobject.strategy.movement.JumpStrategy;
import vooga.platformer.gameobject.strategy.movement.StopStrategy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A Player is a MovingObject composed with Strategies
 *
 * @author Yaqi Zhang
 * @author Zach Michaelov
 */
public class Player extends MovingObject {
    /**
     * the control strategies applied to the myPlayer
     */
    private Map<String, ControlStrategy> controlStrategies;

    public Player(double inX, double inY, double inWidth, double inHeight, int inId, File defaultImageFile) throws IOException {
        super(inX, inY, inWidth, inHeight, inId, defaultImageFile);
        controlStrategies = new HashMap<String, ControlStrategy>();
        initStrategies();
    }

    private void initStrategies() {
        // TODO migrate Gravity to level
        this.addStrategy("GravityStrategy", new GravityStrategy(this));

        // add the Player's Control Strategies
        this.addControlStrategy("Jump", new JumpStrategy(this));
        this.addControlStrategy("GoLeft", new GoLeftStrategy(this));
        this.addControlStrategy("GoRight", new GoRightStrategy(this));
        this.addControlStrategy("Stop", new StopStrategy(this));
        this.addControlStrategy("Shoot", new ShootingStrategy(this));
    }

    /**
     * adds the specified controlStrategy
     * @param controlStrategy the ControlStrategy we want to add to the Player
     */
    public void addControlStrategy(String name, ControlStrategy controlStrategy) {
        controlStrategies.put(name, controlStrategy);
    }

    /**
     * Triggers the specified ControlStrategy
     * @param name the name of the ControlStrategy we want to trigger
     */
    public void fireControlStrategy(String name) {
        controlStrategies.get(name).fire();
    }
}
