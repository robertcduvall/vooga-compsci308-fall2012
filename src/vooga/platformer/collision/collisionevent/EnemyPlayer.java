package vooga.platformer.collision.collisionevent;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.Direction;


/**
 * Type A is the one that been killed if Type B comes from top of Type A.
 * Otherwise Type B gets killed.
 * 
 * @author Yaqi
 * 
 */
public class EnemyPlayer extends CollisionEvent {
    public EnemyPlayer (Class typeA, Class typeB) {
        super(typeA, typeB);
    }

    @Override
    public void applyCollision (Level level, GameObject gameObjectA,
            GameObject gameObjectB) {
        if (this.direction() == Direction.DOWN) {
            gameObjectA.markForRemoval();
        }
        else {
            gameObjectB.markForRemoval();
        }
    }

}
