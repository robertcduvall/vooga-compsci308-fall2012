package vooga.platformer.gameobject;


import vooga.platformer.gameobject.strategy.EnemyMoveStrategy;
import vooga.platformer.gameobject.strategy.GravityStrategy;

public class Enemy {
    private MovingObject myEnemy;
    /**
     * @param configString
     */
    public Enemy(String configString) {
        myEnemy = new MovingObject(configString);
        myEnemy.addStrategy("EnemyMoveStrategy",new EnemyMoveStrategy(this));
        myEnemy.addStrategy("GravityStrategy",new GravityStrategy(myEnemy));
    }
}
