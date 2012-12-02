package vooga.platformer.gameobject;


import vooga.platformer.gameobject.strategy.EnemyMoveStrategy;
import vooga.platformer.gameobject.strategy.GravityStrategy;

public class Enemy extends MovingObject {

    /**
     * @param configString
     */
    public Enemy(String configString) {
        this.addStrategy("EnemyMoveStrategy", new EnemyMoveStrategy(this));
        this.addStrategy("GravityStrategy", new GravityStrategy(this));
    }

}
