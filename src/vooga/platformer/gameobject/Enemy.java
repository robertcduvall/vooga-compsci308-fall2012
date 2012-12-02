package vooga.platformer.gameobject;


import vooga.platformer.gameobject.strategy.update.EnemyMoveStrategy;
import vooga.platformer.gameobject.strategy.update.GravityStrategy;

import java.io.File;
import java.io.IOException;

/**
 * @author Zach Michaelov
 */
public class Enemy extends MovingObject {
    public Enemy(double inX, double inY, double inWidth, double inHeight, int inId, File defaultImageFile) throws IOException {
        super(inX, inY, inWidth, inHeight, inId, defaultImageFile);
        this.addStrategy("EnemyMoveStrategy", new EnemyMoveStrategy(this));
        this.addStrategy("GravityStrategy", new GravityStrategy(this));
    }

}
