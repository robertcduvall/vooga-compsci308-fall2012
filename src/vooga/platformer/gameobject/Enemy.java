package vooga.platformer.gameobject;


import vooga.platformer.gameobject.strategy.update.EnemyMoveStrategy;
import vooga.platformer.gameobject.strategy.update.GravityStrategy;

public class Enemy extends MovingObject {

    /**
     * @param configString
     */
    public Enemy(String configString) {
        super(configString);
        this.addStrategy("EnemyMoveStrategy", new EnemyMoveStrategy(this));
        this.addStrategy("GravityStrategy", new GravityStrategy(this));
    }

}
