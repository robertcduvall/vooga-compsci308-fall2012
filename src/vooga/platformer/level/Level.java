package vooga.platformer.level;

import games.platformerdemo.Player;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import util.camera.Camera;
import util.input.core.Controller;
import util.input.core.KeyboardController;
import vooga.platformer.collision.CollisionChecker;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.util.enums.PlayState;


/**
 * 
 * @author Niel Lebeck, modified by Yaqi
 * 
 */

public abstract class Level {
    private List<GameObject> objectList;
    private Camera cam;
    private Dimension myDimension;
    private String myNextLevelName;
    private CollisionChecker myCollisionChecker;
    private Player myPlayer;

    /**
     * Paint the level, including all its GameObjects.
     * 
     * @param pen Graphics object to paint on
     */
    public void paint(Graphics pen) {
        paintBackground(pen);
        for (GameObject go : objectList) {
            go.paint(pen, cam);
        }
    }

    public Level(Dimension dim, CollisionChecker inChecker, Camera inCam) {
        objectList = new ArrayList<GameObject>();
        myDimension = dim;
        myCollisionChecker = inChecker;
        cam = inCam;
    }

    /**
     * Set the dimensions of the level.
     * 
     * @param d
     */
    public void setDimension(Dimension d) {
        myDimension = d;
    }

    /**
     * Return the dimensions of this level.
     * 
     * @return
     */
    public Dimension getDimension() {
        return myDimension;
    }

    /**
     * Template method. Paint the background of the level.
     * 
     * @param pen Graphics object
     */
    public abstract void paintBackground(Graphics pen);

    /**
     * Add a GameObject to the level.
     * 
     * @param go
     */
    public void addGameObject(GameObject go) {
        objectList.add(go);
    }

    /**
     * Return the list of game objects of this level.
     * 
     * @return
     */
    public List<GameObject> getObjectList() {
        return objectList;
    }

    /**
     * Update the level.
     * 
     * @param elapsedTime time since last update cycle
     */
    public void update(long elapsedTime) {
        List<GameObject> removalList = new ArrayList<GameObject>();
        for (GameObject go : objectList) {
            go.update(this, elapsedTime);
            if (go.checkForRemoval()) {
                removalList.add(go);
            }
        }
        
        for (GameObject removeObj : removalList) {
            objectList.remove(removeObj);
        }
        
        //modified here
        myCollisionChecker.checkCollisions(this);
        
    }

    /**
     * @return the current camera of this level
     */
    public Camera getCamera() {
        return cam;
    }

    /**
     * Set the camera of this level.
     * 
     * @param c
     */
    public void setCamera(Camera c) {
        cam = c;
    }

    /**
     * @return a PlayState representing the progress of the player
     *         through the level.
     */
    public abstract PlayState getLevelStatus();

    public void setNextLevelName(String lvlName) {
        myNextLevelName = lvlName;
    }

    /**
     * @return a string representing the name of the next level to load
     */
    public String getNextLevelName() {
        return myNextLevelName;
    }

    /**
     * Set up the given InputController to manage input for this level. For instance,
     * associate keyboard presses with actions directing the player object to move. The
     * Level subclass should have references to objects controlled by input, so that it
     * can set up the InputController correctly.
     * @param myInputController
     */
    public abstract void setInputController (KeyboardController myInputController);
    
    /**
     * @param pl
     */
    public void setPlayer(Player pl){
        myPlayer = pl;
    }
    
    /**
     * @return Player of the game
     */
    public Player getPlayer(){
        return myPlayer;
    }
}
