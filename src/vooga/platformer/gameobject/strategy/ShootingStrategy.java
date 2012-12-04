package vooga.platformer.gameobject.strategy;

import java.io.File;
import java.io.IOException;
import vooga.platformer.gameobject.Bullet;
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
    private static final int BULLET_SIZE = 8;
    private static final int DEFAULT_XVELOCITY = 3;
    private static final int DEFAULT_YVELOCITY = 0;
    
    private int xvelocity;
    private int yvelocity;

    /**
     * @param go GameObject using this strategy
     */
    public ShootingStrategy (GameObject go) {
        myGO = go;
        xvelocity = DEFAULT_XVELOCITY;
        yvelocity = DEFAULT_YVELOCITY;
    }
    
    public void changeBulletVelocity(int vx, int vy) {
        xvelocity = vx;
        yvelocity = vy;
    }

    /**
     * shoot a bullet, this will be called by the input, such as keyboard
     */
    @Override
    public void fire() {
        Bullet bullet = null;
        try {
            bullet = new Bullet(0, 0, BULLET_SIZE, BULLET_SIZE, 0, new File("src/games/platformerdemo/bullet.png")
                , xvelocity, yvelocity);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        bullet.setX(myGO.getX());
        bullet.setY(myGO.getY() + Y_OFFSET);
        if (myGO instanceof MovingObject) {
            bullet.setDirection(((MovingObject) myGO).getFacingDirection());
        }
        myGO.getLevel().addGameObject(bullet);
    }
}
