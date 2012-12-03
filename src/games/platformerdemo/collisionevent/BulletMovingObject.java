package games.platformerdemo.collisionevent;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.Bullet;
import vooga.platformer.gameobject.Enemy;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.level.Level;


/**
 * @author Yaqi
 * 
 */
public class BulletMovingObject extends CollisionEvent {
    /**
     * @param a 
     * @param b 
     */
    public BulletMovingObject (Bullet a, MovingObject b) {
        super(a, b);
    }

    /**
     * @param b 
     * @param a 
     */
    public BulletMovingObject (MovingObject b, Bullet a) {
        super(a, b);
    }

    @Override
    public void applyCollision (Level level, GameObject gameObjectA, GameObject gameObjectB) {
        // TODO: to make this more generalized, need to create a type for all
        // creatures.
        if (b().getClass().equals(Player.class)
                || (b().getClass().equals(Enemy.class))) {
            a().markForRemoval();
            b().markForRemoval();
        }
    }
}
