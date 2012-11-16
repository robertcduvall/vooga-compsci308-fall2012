package vooga.platformer.level;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Collection;
import util.camera.Camera;
import util.reflection.Reflection;
import util.reflection.ReflectionException;
import vooga.platformer.collision.CollisionChecker;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.leveleditor.Sprite;
import vooga.platformer.levelfileio.LevelFileIOException;
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
     * @param playerClass fully qualified class name of the player class
     *        for this level
     * @return a Level object with all the properties and GameObjects specified
     *         in the data file
     * @throws LevelFileIOException exception if the data file cannot be
     *         successfully read from the level data file
     */
    public static Level loadLevel (String levelName, String playerClass)
                                                                        throws LevelFileIOException {
        try {
            LevelFileReader lfr = new LevelFileReader(levelName);
            Dimension levelDimension = new Dimension(lfr.getWidth(), lfr.getHeight());
            CollisionChecker levelCollisionChecker =
                    (CollisionChecker) Reflection.createInstance(lfr.getCollisionCheckerType());

            Collection<Sprite> levelSprites = lfr.getSprites();
            ArrayList<GameObject> levelGameObjects = new ArrayList<GameObject>();
            for (Sprite s : levelSprites) {
                levelGameObjects.add(spriteToGameObject(s));
            }

            // TODO Using FollowingCamera by default. This is due to
            // complications with differences in constructors between camera
            // types.
            Camera followCam =
                    new FollowingCamera(DEFAULT_CAMERA_SIZE, new Rectangle(levelDimension.width,
                                                                           levelDimension.width),
                                        findPlayerGameObject(levelGameObjects, playerClass));
            // TODO configure with proper config string rather than assume
            // params
            Level level =
                    (Level) Reflection.createInstance(lfr.getLevelType(), levelDimension,
                                                      levelCollisionChecker, followCam);

            for (GameObject g : levelGameObjects) {
                level.addGameObject(g);
            }

            return level;
        }
        catch (ReflectionException e) {
            throw new LevelFileIOException("Incorrect class name was passed as a type parameter");
        }
        catch (LevelFileIOException e) {
            throw e;
        }
        catch (Exception e) {
            throw new LevelFileIOException("Failed to load level");
        }
    }

    private static GameObject spriteToGameObject (Sprite s) {
        String configString = "";
        String deliminator = ",";
        configString = configString.concat("x=" + s.getX() + deliminator);
        configString = configString.concat("y=" + s.getY() + deliminator);
        configString = configString.concat("width=" + s.getWidth() + deliminator);
        configString = configString.concat("height=" + s.getHeight());
        return (GameObject) Reflection.createInstance(s.getType(), configString);
    }

    private static GameObject findPlayerGameObject (Collection<GameObject> gameObjects,
                                                    String playerClassName) {
        try {
            Class<?> clazz = Class.forName(playerClassName);
            for (GameObject g : gameObjects) {
                if (clazz.equals(g.getClass())) { return g; }
            }
        }
        catch (ClassNotFoundException e) {
            throw new LevelFileIOException("Player class could not be found");
        }
        throw new LevelFileIOException("No player GameObject in data file");
    }

}
