package games.platformerdemo;

import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.UpdateStrategy;


/**
 * @author Yaqi Zhang
 * 
 */
public class ShootingStrategy implements UpdateStrategy {
    private static final int Y_OFFSET = 20;
    private GameObject myGO;

    /**
     * @param go GameObject holding this strategy
     */
    public ShootingStrategy (GameObject go) {
        myGO = go;
    }

    @Override
    public void applyAction () {

    }

    /**
     * 
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
