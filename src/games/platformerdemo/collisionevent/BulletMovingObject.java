package games.platformerdemo.collisionevent;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.Bullet;
import vooga.platformer.gameobject.Enemy;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.level.Level;


/**
 * When Type A and Type B collides, both been killed.
 * @author Yaqi
 * 
 */
public class BulletMovingObject extends CollisionEvent {
    public BulletMovingObject (Class typeA, Class typeB) {
        super(typeA, typeB);
    }

    @Override
    public void applyCollision (Level level, GameObject gameObjectA,
            GameObject gameObjectB) {
        gameObjectA.markForRemoval();
        gameObjectB.markForRemoval();
    }
}
