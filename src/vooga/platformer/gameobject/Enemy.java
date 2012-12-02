package vooga.platformer.gameobject;


import vooga.platformer.gameobject.strategy.GravityStrategy;

public class Enemy {
    private MovingObject myEnemy;

    /**
     * @param configString
     */
    public Enemy(String configString) {
        myEnemy = new MovingObject(configString);
        myEnemy.addStrategy("EnemyMoveStrategy", new EnemyMoveStrategy());
        myEnemy.addStrategy("GravityStrategy", new GravityStrategy(myEnemy));
    }

    /**
     * retrieves the MovingObject representing the enemy
     *
     * @return
     */
    public MovingObject getMovingObject() {
        return myEnemy;
    }

    /**
     * Enemies have some additional behavior in addition to regular MovingObjects
     */
    private class EnemyMoveStrategy implements UpdateStrategy {
        private Boolean myIsExcused = false;

//        public EnemyMoveStrategy(Enemy e) {
////        super(e);
//            myEnemy = e;
//        }

        @Override
        public void applyAction() {
            if (myEnemy.isOnGround() && (!myIsExcused)) {
                myEnemy.setVelocity(1, myEnemy.getVelocity().getY());
                myIsExcused = true;
            }
//        super.applyAction();
        }

    }
}
