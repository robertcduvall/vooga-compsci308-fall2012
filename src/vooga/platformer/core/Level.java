package vooga.platformer.core;

import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.util.enums.PlayState;
import java.awt.Graphics;
import java.util.List;

/**
 * 
 * @author Niel
 *
 */

public abstract class Level {
    private List<GameObject> objectList;
    private Camera cam;
    
    /**
     * Paint the level, including all its GameObjects.
     * @param pen Graphics object to paint on
     */
    public void paint(Graphics pen) {
        paintBackground(pen);
        for (GameObject go : objectList) {
            go.paint(pen, cam);
        }
    }
    
    /**
     * Template method. Paint the background of the level.
     * @param pen Graphics object
     */
    public abstract void paintBackground(Graphics pen);
    
    /**
     * Update the level. 
     * @param elapsedTime time since last update cycle
     */
    public void update(long elapsedTime) {
        for (GameObject go : objectList) {
            go.update(this, elapsedTime);
        }
    }
    
    /**
     * @return the current camera of this level
     */
    public Camera getCamera() {
        return cam;
    }
    
    /**
     * Set the camera of this level.
     * @param c
     */
    public void setCamera(Camera c) {
        cam = c;
    }
    
    /**
     * @return a PlayState representing the progress of the player
     * through the level.
     */
    public abstract PlayState getLevelStatus();
    
    /**
     * @return a string representing the name of the next level to load
     */
    public abstract String getNextLevelName();
}
