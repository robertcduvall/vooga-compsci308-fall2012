package vooga.platformer.level;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Collection;
import util.camera.Camera;
import util.reflection.Reflection;
import util.reflection.ReflectionException;
import vooga.platformer.collision.BasicCollisionChecker;
import vooga.platformer.collision.CollisionChecker;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.leveleditor.Sprite;
import vooga.platformer.levelfileio.LevelFileReader;
import vooga.platformer.util.camera.FollowingCamera;


/**
 * A single-method class used to construct Level objects from formatted data
 * files.
 * 
 * @author Grant Oakley
 * 
 */
public final class LevelFactory {

    // TODO collision factory with reflection
    private static final CollisionChecker DEFAULT_COLLISION_CHECKER = new BasicCollisionChecker();
    private static final Dimension2D DEFAULT_CAMERA_SIZE = new Dimension(50, 50);

    private LevelFactory () {
        /*
         * Empty constructor.
         */
    }

    /**
     * Creates a new Level from the file at the path specified. The format of
     * this file must conform to the conventions of LevelFileReader.
     * 
     * @param levelName path to the data file describing the level
     * @return a Level object with all the properties and GameObjects specified
     *         in the data file
     */
    public static Level loadLevel (String levelName) {
        LevelFileReader lfr = new LevelFileReader(levelName);
        Dimension levelDimension = new Dimension(lfr.getWidth(), lfr.getHeight());
        Collection<Sprite> levelSprites = lfr.getSprites();
        ArrayList<GameObject> levelGameObjects = new ArrayList<GameObject>();
        for (Sprite s : levelSprites) {
            levelGameObjects.add(spriteToGameObject(s));
        }
        // TODO Using FollowingCamera by default. This is due to complications
        // with differences in constructors between camera types.
        Camera followCam =
                new FollowingCamera(DEFAULT_CAMERA_SIZE, new Rectangle(levelDimension.width,
                                                                       levelDimension.width),
                                    findHeroGameObject(levelGameObjects));
        // TODO
        return null;
    }

    private static GameObject spriteToGameObject (Sprite s) {
        // TODO
        return null;
    }

    private static GameObject findHeroGameObject (Collection<GameObject> gameObjects) {
        // TODO
        return null;
    }

}
