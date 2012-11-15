package vooga.turnbased.sprites;

import java.util.ArrayList;
import vooga.turnbased.gameobject.GameObject;

public class Sprite {
	private static int SPRITE_COUNT = 0;
	private int myID;
    private ArrayList<GameObject> myGameObjects;
    
    public Sprite () {
    	myGameObjects = new ArrayList<GameObject>();
    	myID = SPRITE_COUNT;
    	SPRITE_COUNT++;
    }
    
    public Sprite(GameObject ... objs) {
    	this();
    	for(GameObject o : objs) {
    		addGameObject(o);
    	}
    }
    
    public void addGameObject(GameObject obj) {
    	myGameObjects.add(obj);
    }
    
    public int getID() {
    	return myID;
    }

	public GameObject getObject(Class<GameObject> c) {
		for(GameObject go : myGameObjects) {
			if(c.isAssignableFrom(go.getClass())){
				return go;
			}
		}
		return null;
	}
}
