package games.platformerdemo;

public class EnemyMoveStrategy extends SimpleMoveStrategy {
    private Enemy myEnemy;
    
    public EnemyMoveStrategy (Enemy e) {
        super(e);
        myEnemy = e;
    }
    
    @Override
    public void applyAction() {
        if (myEnemy.isOnGround()) {
            myEnemy.setVelocity(1, myEnemy.getVelocity().getY());
        }
        super.applyAction();
    }

}
