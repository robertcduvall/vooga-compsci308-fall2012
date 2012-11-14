package games.platformerdemo;

public class Enemy extends MovingObject {
    private boolean onGround;
    
    /**
     * @param configString
     */
    public Enemy (String configString) {
        super(configString, "enemy");
        addStrategy(new EnemyMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
        onGround = false;
    }
    
    public void notifyOnGround() {
        onGround = true;
    }
    
    public boolean isOnGround() {
        return onGround;
    }
}
