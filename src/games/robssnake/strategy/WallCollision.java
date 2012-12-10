package games.robssnake.strategy;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;

public class WallCollision extends CollisionEvent {
    public WallCollision (Class typeA, Class typeB) {
        super(typeA, typeB);
    }

    @Override
    public void applyCollision (Level level, GameObject gameObjectA,
            GameObject gameObjectB) {
        gameObjectB.markForRemoval();
    }
}
