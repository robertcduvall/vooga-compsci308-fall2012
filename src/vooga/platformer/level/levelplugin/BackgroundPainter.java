package vooga.platformer.level.levelplugin;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;

public abstract class BackgroundPainter extends LevelPlugin {

    @Override
    public void update (List<GameObject> objList) {

    }

    @Override
    public void paint (Graphics pen, List<GameObject> objList, Camera cam) {
        pen.drawImage(getCurrentBackgroundImage(objList), 0, 0, null);
    }
    
    /**
     * Return the current background image. If the background is static, this method
     * could return the same image each time; if it is animated, the image should switch.
     * If the background moves with the player or some other GameObject, the returned image
     * could depend on the state of the GameObjects in the level.
     */
    public abstract Image getCurrentBackgroundImage(List<GameObject> objectList);

    /**
     * The background is the very first thing painted, so the priority is 0.
     */
    @Override
    public int getPriority () {
        return MIN_PRIORITY;
    }

}
