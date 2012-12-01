package vooga.platformer.leveleditor;

import java.net.URL;


/**
 * Represents an Object that can be saved and loaded by
 * the user.
 * 
 * @author Paul
 * 
 */
public interface ISavable {

    /**
     * Saves the object to xml.
     */
    void save();

    /**
     * Loads the object from a
     * specified path.
     * 
     * @param path
     * @deprecated Don't know how I expected an object to
     *             be able to load itself...
     */
    void load(URL path);

}
