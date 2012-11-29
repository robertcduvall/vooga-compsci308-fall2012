package games.platformerdemo;

import java.awt.Graphics;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.UpdateStrategy;


/**
 * @author Yaqi Zhang
 * 
 */
public class ShootingStrategy implements UpdateStrategy {
    private GameObject myGO;

    /**
     * @param go
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
        Bullet bullet = new Bullet(null);
        bullet.setX(myGO.getX());
        bullet.setY(myGO.getY());
        if (myGO instanceof MovingObject) {
            String directionOfGO;
            double xVel = ((MovingObject) myGO).getVelocity().getX();
            if (xVel > 0) {
                directionOfGO = "right";
            }
            else {
                directionOfGO = "left";
            }
            bullet.setDirection(directionOfGO);
        }
        //TODO: add this bullet to gameobject list
    }
}
