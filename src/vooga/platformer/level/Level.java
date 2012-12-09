package vooga.platformer.level;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.camera.Camera;
import util.camera.UpdatableCamera;
import vooga.platformer.collision.BasicCollisionChecker;
import vooga.platformer.collision.CollisionChecker;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.level.condition.Condition;
import vooga.platformer.level.levelplugin.LevelPlugin;
import vooga.platformer.util.enums.PlayState;


/**
 * 
 * @author Niel Lebeck, modified by Yaqi
 * 
 */

public class Level {
    private List<GameObject> objectList;
    private List<LevelPlugin> pluginList;
    private List<Condition> conditionList;
    private UpdatableCamera cam;
    private Dimension myDimension;
    private String myNextLevelName;
    private CollisionChecker myCollisionChecker;
    private Player myPlayer;
    private boolean myPaused;

    public Level (Dimension levelDim, UpdatableCamera inCam, String collisionFileName) {
        objectList = new ArrayList<GameObject>();
        conditionList = new ArrayList<Condition>();
        pluginList = new ArrayList<LevelPlugin>();
        myDimension = levelDim;
        myCollisionChecker = new BasicCollisionChecker(collisionFileName);
        cam = inCam;
        myPaused = false;
    }

    /**
     * Paint the level, including all its GameObjects.
     * 
     * @param pen Graphics object to paint on
     */
    public void paint (Graphics pen) {
        for (LevelPlugin lp : pluginList) {
            lp.paint(pen, objectList, cam);
        }
        for (GameObject go : objectList) {
            go.paint(pen, cam);
        }
    }

    /**
     * Add a LevelPlugin to the level.
     * 
     * @param lp LevelPlugin to add
     */
    public void addPlugin (LevelPlugin lp) {
        pluginList.add(lp);
        Collections.sort(pluginList);
    }

    /**
     * Add a Condition to the level.
     * 
     * @param c Condition to add
     */
    public void addCondition (Condition c) {
        conditionList.add(c);
    }

    /**
     * Set the dimensions of the level.
     * 
     * @param d
     */
    public void setDimension (Dimension d) {
        myDimension = d;
    }

    /**
     * Return the dimensions of this level.
     * 
     * @return
     */
    public Dimension getDimension () {
        return myDimension;
    }

    /**
     * Add a GameObject to the level.
     * 
     * @param go GameObject to add
     */
    public void addGameObject (GameObject go) {
        objectList.add(go);
        Collections.sort(objectList);
    }

    /**
     * Return the list of game objects of this level.
     * 
     * @return
     */
    public List<GameObject> getObjectList () {
        return objectList;
    }

    /**
     * Update the level.
     * 
     * @param elapsedTime time since last update cycle
     */
    public void update (long elapsedTime) {
        if (!myPaused) {
            List<GameObject> removalList = new ArrayList<GameObject>();
            for (GameObject go : objectList) {
                go.update(this, elapsedTime);
                Rectangle levelBounds =
                        new Rectangle(0, 0, (int) myDimension.getWidth(),
                                      (int) myDimension.getHeight());
                if (!go.getShape().intersects(levelBounds)) {
                        go.markForRemoval();
                }
                if (go.checkForRemoval()) {
                    removalList.add(go);
                }
            }

            for (int i = removalList.size() - 1; i >= 0; i--) {
                objectList.remove(removalList.get(i));
            }

            // modified here
            myCollisionChecker.checkCollisions(this);
            cam.update(elapsedTime);

            for (LevelPlugin lp : pluginList) {
                lp.update(objectList);
            }
        }
    }

    /**
     * Pause the game, temporarily stopping it from updating
     */
    public void pause () {
        myPaused = true;
    }

    public void unpause () {
        myPaused = false;
    }

    /**
     * @return the current camera of this level
     */
    public Camera getCamera () {
        return cam;
    }

    /**
     * Set the camera of this level.
     * 
     * @param c
     */
    public void setCamera (UpdatableCamera c) {
        cam = c;
    }

    /**
     * @return a PlayState representing the progress of the player
     *         through the level.
     */
    public PlayState getLevelStatus () {
        for (Condition c : conditionList) {
            if (c.isSatisfied(objectList)) {
                setNextLevelName(c.getNextLevelName());
                return c.getStatus();
            }
        }
        return PlayState.IS_PLAYING;
    }

    private void setNextLevelName (String lvlName) {
        myNextLevelName = lvlName;
    }

    /**
     * @return a string representing the name of the next level to load
     */
    public String getNextLevelName () {
        return myNextLevelName;
    }

    /**
     * @param pl
     */
    public void setPlayer (Player pl) {
        myPlayer = pl;
    }

    /**
     * @return Player of the game
     */
    public Player getPlayer () {
        return myPlayer;
    }
}
