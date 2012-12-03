package vooga.platformer.level.levelplugin;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;

/**
 * A plugin that operates on a Level's list of GameObjects and has updating
 * and painting functionality.
 * @author Niel Lebeck
 *
 */
public abstract class LevelPlugin implements Comparable<LevelPlugin>, Serializable {
    private static final long serialVersionUID = 1L;
    protected final static int MIN_PRIORITY = 0;
    protected final static int MAX_PRIORITY = 99;
    
    /**
     * Update this LevelPlugin, either modifying its internal state or modifying the
     * objects in the list.
     * @param objList the Level's list of objects. The collection must be a list to
     * take advantage of sorting by ID.
     */
    public abstract void update(List<GameObject> objList);
    
    /**
     * Paint this LevelPlugin.
     * @param pen Graphics object to use when painting
     * @param objList the Level's list of objects
     * @param cam the Level's current Camera
     */
    public abstract void paint(Graphics pen, List<GameObject> objList, Camera cam);
    
    /**
     * Return a map with information about the param tags (keys) and descriptions of the
     * params (values), just like GameObject.
     * @return the map
     */
    public abstract Map<String, String> getConfigStringParams();
    
    /**
     * Compares this LevelPlugin to another based on their priorities.
     * @param lp other LevelPlugin to compare to
     */
    public int compareTo(LevelPlugin lp) {
        int diff = this.getPriority() - lp.getPriority();
        if (diff != 0) {
            return diff;
        }
        else {
            return this.hashCode() - lp.hashCode();
        }
    }
    
    /**
     * A template method used for determining the sorting order of LevelPlugins
     * @return the priority of this LevelPlugin. A lower priority number means it is
     * earlier in a sorted list.
     */
    public abstract int getPriority();
    
}
