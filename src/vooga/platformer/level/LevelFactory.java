package vooga.platformer.level;

import vooga.platformer.gameobject.GameObject;
import vooga.platformer.leveleditor.LevelFileReader;
import vooga.platformer.leveleditor.Sprite;


/**
 * A single-method class used to construct Level objects from formatted data
 * files.
 * 
 * @author Grant Oakley
 * 
 */
public final class LevelFactory {

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
        return null;
    }

    private static GameObject spriteToGameObject (Sprite s) {
        // TODO
        return null;
    }

}
