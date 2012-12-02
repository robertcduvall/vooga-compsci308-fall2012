package vooga.platformer.gameobject.strategy;


import vooga.platformer.gameobject.Enemy;
import vooga.platformer.gameobject.UpdateStrategy;

/**
 * Enemies have some additional behavior in addition to regular MovingObjects
 * @author Probably Niel, modified by Yaqi
 * @author Zach Michaelov
 */
public class EnemyMoveStrategy implements UpdateStrategy {
    private Enemy myEnemy;
    private Boolean myIsExcused = false;
    
    public EnemyMoveStrategy(Enemy enemy) {
        myEnemy = enemy;
    }
    
    @Override
    public void applyAction() {
        if (myEnemy.isOnGround() && (!myIsExcused)) {
            myEnemy.setVelocity(1, myEnemy.getVelocity().getY());
            myIsExcused = true;
        }
    }

}
