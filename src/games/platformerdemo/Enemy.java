package games.platformerdemo;

public class Enemy extends MovingObject {
    
    /**
     * @param configString
     */
    public Enemy (String configString) {
        super(configString);
        addStrategy(new EnemyMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
    }
}
