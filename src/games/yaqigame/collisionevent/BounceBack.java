package games.yaqigame.collisionevent;

import java.awt.geom.Rectangle2D;
import vooga.platformer.collision.CollisionEvent;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.gameobject.StaticObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.Direction;


/**
 * @author Yaqi Zhang
 * 
 */
public class BounceBack extends CollisionEvent {

    /**
     * @param typeA must be a moving obj
     * @param typeB must be a moving obj
     */
    public BounceBack (Class typeA, Class typeB) {
        super(typeA, typeB);
    }

    @Override
    public void applyCollision (Level level, GameObject objectA,
            GameObject objectB) {
        MovingObject mo1 = (MovingObject) objectA;
        MovingObject mo2 = (MovingObject) objectB;
        Rectangle2D intersection = mo1.getShape().createIntersection(
                mo2.getShape());
        double dy = intersection.getHeight();
        double dx = intersection.getWidth();
        if (direction() == Direction.DOWN) {
            mo2.setY(mo2.getY() - dy);
        }
        else if (direction() == Direction.UP) {
            mo2.setY(mo2.getY() + dy);
        }
        if (direction() == Direction.RIGHT) {
            mo2.setX(mo2.getX() - dx);
            flipHorizontalVelocity(mo1);
            flipHorizontalVelocity(mo2);
        }
        else if (direction() == Direction.LEFT) {
            mo2.setX(mo2.getX() + dx);
            flipHorizontalVelocity(mo1);
            flipHorizontalVelocity(mo2);
        }
    }

    private void flipHorizontalVelocity (MovingObject mo) {
        mo.setVelocity(-mo.getVelocity().getX(), mo.getVelocity().getY());
    }
}
