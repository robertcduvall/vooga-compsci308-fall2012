package games.platformerdemo;

public class EnemyMoveStrategy extends SimpleMoveStrategy {
    private Enemy myEnemy;
    
    public EnemyMoveStrategy (Enemy e) {
        super(e);
        myEnemy = e;
    }
    
    @Override
    public void applyAction() {
        myEnemy.setVelocity(1, 0);
        super.applyAction();
    }

}
