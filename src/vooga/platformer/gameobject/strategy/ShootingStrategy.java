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
     * supposed to be empty because shouldn't been updated by level
     */
    @Override
    public void applyAction () {

    }

    /**
     * shoot a bullet, this will be called by the input, such as keyboard
     */
    public void shoot () {
        Bullet bullet = new Bullet();
        bullet.setX(myGO.getX());
        bullet.setY(myGO.getY() + Y_OFFSET);
        if (myGO instanceof MovingObject) {
            bullet.setDirection(((MovingObject) myGO).getFacingDirection());
        }
        myGO.getLevel().addToObjectList(bullet);
    }
}
