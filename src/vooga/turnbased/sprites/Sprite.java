package vooga.turnbased.sprites;

import java.util.ArrayList;
import java.util.List;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.mapobject.MapObject;

/**
 * This class is bucket. It holds several GameObjects, one for each mode this sprite
 * exists in.
 * @author David Howdyshell
 *
 */
public class Sprite {
    private static int SPRITE_COUNT = 1;
    private int myID;
    private List<GameObject> myGameObjects;

    /**
     * Creates a sprite with a new ID. This initial sprite has no GameObjects yet.
     */
    public Sprite () {
        myGameObjects = new ArrayList<GameObject>();
        myID = SPRITE_COUNT;
        SPRITE_COUNT++;
    }

    /**
     * Creates a new sprite with given GameObjects, which will be the respective GameObjects
     * that represent the sprite in different modes.
     * @param objs The different GameObjects that will make up this sprite.
     */
    public Sprite (GameObject ... objs) {
        this();
        for (GameObject o : objs) {
            addGameObject(o);
        }
    }

    /**
     * This adds a GameObject (i.e. BattleObject, MapObject) to a sprite.
     * @param obj The GameObject to be added.
     */
    public void addGameObject (GameObject obj) {
        obj.setID(myID);
        myGameObjects.add(obj);
    }

    /**
     * Returns this sprite's ID.
     * @return The ID number of the sprite, and integer.
     */
    public int getID () {
        return myID;
    }

    /**
     * Returns all GameObjects from myGameObjects that are instance of clazz
     * @param <T>
     * @param clazz The class that you are looking for
     * @return
     */
    public <T extends GameObject> List<T> getObject (Class<T> clazz) {
        List<T> relevantObjects = new ArrayList<T>();
        for (GameObject go : myGameObjects) {
            if (clazz.isAssignableFrom(go.getClass())) {
                relevantObjects.add((T) go);
            }
        }
        return relevantObjects;
    }

    /**
     * Erases all GameObjects from Sprite.
     */
    public void clear () {
        for (GameObject go : myGameObjects) {
            go = null;
        }
        myGameObjects.clear();
    }

    /**
     * find the MapObject which represents the Sprite in the maps
     * @return the MapObject of the Sprite
     */
    public MapObject getMapObject() {
        for (GameObject object: myGameObjects) {
            if (object instanceof MapObject) { return (MapObject)object; }
        }
        return null;
    }
}
