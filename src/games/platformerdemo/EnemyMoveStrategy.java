package games.platformerdemo;

/**
 * @author Probably Niel, modified by Yaqi
 *
 */
public class EnemyMoveStrategy extends SimpleMoveStrategy {
    private Enemy myEnemy;
    private Boolean myIsExcused = false;
    
    public EnemyMoveStrategy (Enemy e) {
        super(e);
        myEnemy = e;
    }
    
    @Override
    public void applyAction() {
        if (myEnemy.isOnGround() && (!myIsExcused)) {
            myEnemy.setVelocity(1, myEnemy.getVelocity().getY());
            myIsExcused = true;
        }
        super.applyAction();
    }

}
