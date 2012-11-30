package vooga.platformer.gameobject.strategy;

import games.platformerdemo.Bullet;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.UpdateStrategy;


/**
 * @author Yaqi Zhang
 * @author Zach Michaelov
 */
public class ShootingStrategy implements UpdateStrategy {
    private static final int Y_OFFSET = 20;
    private GameObject myGO;

    /**
     * @param go GameObject using this strategy
     */
    public ShootingStrategy (GameObject go) {
        myGO = go;
    }

    /**
     * applyAction of ShootingStrategy creates a bullet and fires it
     */
    @Override
    public void applyAction () {
        Bullet bullet = new Bullet();
        bullet.setX(myGO.getX());
        bullet.setY(myGO.getY() + Y_OFFSET);
        if (myGO instanceof MovingObject) {
            bullet.setDirection(((MovingObject) myGO).getFacingDirection());
        }
        myGO.getLevel().addToObjectList(bullet);
    }

}
