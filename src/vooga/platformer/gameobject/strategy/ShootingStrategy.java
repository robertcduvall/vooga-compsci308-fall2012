package vooga.platformer.gameobject.strategy;

import games.platformerdemo.Bullet;
import vooga.platformer.gameobject.ControlStrategy;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;


/**
 * Allows a GameObject to fire Bullets
 * @author Yaqi Zhang
 * @author Zach Michaelov
 */
public class ShootingStrategy implements ControlStrategy {
    private static final int Y_OFFSET = 20;
    private GameObject myGO;

    /**
     * @param go GameObject using this strategy
     */
    public ShootingStrategy (GameObject go) {
        myGO = go;
    }

    /**
     * shoot a bullet, this will be called by the input, such as keyboard
     */
    @Override
    public void fire() {
        Bullet bullet = new Bullet();
        bullet.setX(myGO.getX());
        bullet.setY(myGO.getY() + Y_OFFSET);
        if (myGO instanceof MovingObject) {
            bullet.setDirection(((MovingObject) myGO).getFacingDirection());
        }
//        myGO.getLevel().addToObjectList(bullet);
    }
}
