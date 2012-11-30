package vooga.platformer.gameobject;


import vooga.platformer.gameobject.strategy.EnemyMoveStrategy;
import vooga.platformer.gameobject.strategy.GravityStrategy;

public class Enemy extends MovingObject {
    
    /**
     * @param configString
     */
    public Enemy(String configString) {
        super(configString);
        addStrategy(new EnemyMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
    }
}
