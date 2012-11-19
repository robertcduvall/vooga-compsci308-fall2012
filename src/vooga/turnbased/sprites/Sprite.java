package vooga.turnbased.sprites;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import vooga.turnbased.gameobject.GameObject;

/**
 * This class is bucket. It holds several GameObjects, one for each mode this sprite
 * exists in.
 * @author David Howdyshell
 *
 */
public class Sprite {
    private static int SPRITE_COUNT = 0;
    private int myID;
    private List<GameObject> myGameObjects;

    public Sprite () {
        myGameObjects = new ArrayList<GameObject>();
        myID = SPRITE_COUNT;
        SPRITE_COUNT++;
    }

    public Sprite (GameObject ... objs) {
        this();
        for (GameObject o : objs) {
            addGameObject(o);
        }
    }

    public void addGameObject (GameObject obj) {
        obj.setID(myID);
        myGameObjects.add(obj);
    }

    public int getID () {
        return myID;
    }

    /**
     * Returns all GameObjects from myGameObjects that are
     * instance of clazz
     * @param clazz
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

    public Iterator<GameObject> getGameObjectsIterator () {
        return myGameObjects.iterator();
    }
    
    

}
