package vooga.platformer.level.levelplugin;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.MovingObject;
import vooga.platformer.util.enums.Direction;


/**
 * A LevelPlugin that applies gravity to all MovingObjects in the level by
 * changing their velocity using the given acceleration and direction. Basically,
 * this plugin applies a constant acceleration in a direction.
 * @author Niel Lebeck
 *
 */
public class GravityPlugin extends LevelPlugin {

    double myAccel;
    Direction myDir;
    
    /**
     * 
     * @param accel magnitude of acceleration
     * @param dir direction of acceleration
     */
    public GravityPlugin(double accel, Direction dir) {
        myAccel = accel;
        myDir = dir;
    }
    
    @Override
    public void update (List<GameObject> objList) {
        for (GameObject go : objList) {
            if (go instanceof MovingObject) {
                MovingObject mo = (MovingObject)go;
                Point2D vpoint = mo.getVelocity();
                double newXVel = vpoint.getX();
                double newYVel = vpoint.getY();
                if (myDir == Direction.UP) {
                    newYVel = newYVel - myAccel;
                }
                else if (myDir == Direction.DOWN) {
                    newYVel = newYVel + myAccel;
                }
                else if (myDir == Direction.LEFT) {
                    newXVel = newXVel - myAccel;
                }
                else if (myDir == Direction.RIGHT) {
                    newXVel = newXVel + myAccel;
                }
                mo.setVelocity(newXVel, newYVel);
            }
        }
    }

    @Override
    public void paint (Graphics pen, List<GameObject> objList, Camera cam) {
        
    }

    @Override
    public Map<String, String> getConfigStringParams () {
        return null;
    }

    @Override
    public int getPriority () {
        return MAX_PRIORITY;
    }

}
