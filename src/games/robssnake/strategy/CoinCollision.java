package games.robssnake.strategy;

import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;

public class CoinCollision extends CollisionEvent {
    public CoinCollision (Class typeA, Class typeB) {
        super(typeA, typeB);
    }

    @Override
    public void applyCollision (Level level, GameObject gameObjectA,
            GameObject gameObjectB) {
        gameObjectA.markForRemoval();
        gameObjectB.setSize(gameObjectB.getWidth() + 5, gameObjectB.getHeight() + 5);
    }
}
