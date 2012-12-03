package vooga.platformer.level;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.util.Collection;
import util.reflection.ReflectionException;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;
import vooga.platformer.level.condition.Condition;
import vooga.platformer.level.levelplugin.LevelPlugin;
import vooga.platformer.levelfileio.LevelFileIOException;
import vooga.platformer.levelfileio.LevelFileReader;
import vooga.platformer.util.camera.FollowingCamera;
import vooga.platformer.util.camera.UpdatableCamera;


/**
 * A single-method class used to construct Level objects from formatted data
 * files.
 * 
 * @author Grant Oakley
 * 
 */
public final class LevelFactory {

    private static final Dimension2D DEFAULT_CAMERA_SIZE = new Dimension(800, 600);

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
     * @throws LevelFileIOException exception if the data file cannot be
     *         successfully read from the level data file
     */
    public static Level loadLevel (String levelName) throws LevelFileIOException {
        try {
            
            LevelFileReader lfr = new LevelFileReader(levelName);
            Dimension levelDimension = new Dimension(lfr.getWidth(), lfr.getHeight());
            Collection<GameObject> levelGameObjects = lfr.getGameObjects();
            GameObject player = findPlayerGameObject(levelGameObjects);
            UpdatableCamera followCam =
                    new FollowingCamera(DEFAULT_CAMERA_SIZE, new Rectangle(levelDimension.width,
                                                                           levelDimension.height),
                                        player);

            Level level = new Level(levelDimension, followCam, lfr.getCollisionCheckerPath());

            for (GameObject g : levelGameObjects) {
                level.addGameObject(g);
            }
            level.setPlayer((Player) player);
            addConditionsAndPlugins(lfr, level);

            return level;
            
        }
        catch (ReflectionException e) {
            throw new LevelFileIOException("Class name in Level file not found.", e);
        }
        catch (LevelFileIOException e) {
            throw e;
        }
        catch (Exception e) {
            throw new LevelFileIOException("Failed to load level", e.getCause());
        }
    }

    private static void addConditionsAndPlugins (LevelFileReader lfr, Level level) {

        Collection<Condition> levelConditions = lfr.getConditions();
        for (Condition c : levelConditions) {
            level.addCondition(c);
        }

        Collection<LevelPlugin> levelPlugins = lfr.getLevelPlugins();
        for (LevelPlugin lp : levelPlugins) {
            level.addPlugin(lp);
        }

    }

    private static GameObject findPlayerGameObject (Collection<GameObject> gameObjects) {
        for (GameObject g : gameObjects) {
            if (g instanceof Player) { return g; }
        }

        throw new LevelFileIOException("No Player GameObject in data file");
    }

}
