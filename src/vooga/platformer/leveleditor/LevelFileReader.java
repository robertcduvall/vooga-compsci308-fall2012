package vooga.platformer.leveleditor;

import java.awt.Image;
import java.util.Collection;


/**
 * Instances of this class are created using the path to an XML level data file.
 * Once the instance is created, the getter methods can be used to get the
 * contents of this data file as java types (ints and Sprites, rather than
 * string values).
 * 
 * @author Grant Oakley
 * 
 */
public class LevelFileReader {

    public LevelFileReader (String levelFilePath) {
        // TODO open file
    }

    public String getLevelID () {
        // TODO
        return null;
    }

    public int getWidth () {
        // TODO
        return 0;
    }

    public int getHeight () {
        // TODO
        return 0;
    }

    public Image getBackgroundImage () {
        // TODO
        return null;
    }

    public Collection<Sprite> getSprites () {
        // TODO
        return null;
    }

}
