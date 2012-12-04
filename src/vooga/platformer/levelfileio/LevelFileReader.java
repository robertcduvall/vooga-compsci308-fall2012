package vooga.platformer.levelfileio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.condition.Condition;
import vooga.platformer.level.levelplugin.LevelPlugin;


/**
 * Instances of this class are created using the path to an XML level data file
 * written in the format established by LevelFileWriter. Once the instance is
 * created, the getter methods can be used to get the contents of this data file
 * as java types.
 * 
 * @author Grant Oakley
 * @author Zach Michaelov (modified)
 */
public class LevelFileReader {

    private final Document myDocument;
    private Element myRoot;

    /**
     * Creates a new LevelFileReader using the level data file specified.
     * 
     * @param levelFilePath path to the level data file (must conform to package
     *        XML format)
     */
    public LevelFileReader (String levelFilePath) {
        this(new File(levelFilePath));
    }

    /**
     * Creates a new LevelFileReader using the File specified.
     * 
     * @param levelFile File in XML format representing the level to be read
     *        (must conform to package XML format)
     */

    public LevelFileReader (File levelFile) {
        myDocument = XmlUtilities.makeDocument(levelFile);
        myRoot = myDocument.getDocumentElement();
    }

    /**
     * Gets the name of the level that is to be displayed to the user.
     * 
     * @return name of the level as a String
     */
    public String getLevelName () {
        return XmlUtilities.getChildContent(myRoot, XmlTags.LEVEL_NAME);
    }

    /**
     * Gets the overall width of the level.
     * 
     * @return width of the level as an int
     */
    public int getWidth () {
        return XmlUtilities.getChildContentAsInt(myRoot, XmlTags.WIDTH);
    }

    /**
     * Gets the overall height of the level.
     * 
     * @return height of the level as an int
     */
    public int getHeight () {
        return XmlUtilities.getChildContentAsInt(myRoot, XmlTags.HEIGHT);
    }

    /**
     * Gets the path to the xml file describing the collision checker.
     * 
     * @return file name of the xml file as a string
     */
    public String getCollisionCheckerPath () {
        return XmlUtilities.getChildContent(myRoot, XmlTags.COLLISION_CHECKER);
    }

    /**
     * Gets the fully-qualified class name of the Camera to use for this
     * particular level.
     * 
     * @return class name of this level's Camera subclass
     */
    public String getCameraType () {
        return XmlUtilities.getChildContent(myRoot, XmlTags.CAMERA);
    }

    /**
     * Gets a collection of all the serialized GameObjects stored in the binary
     * file specified in the <code>gameObjectData</code> tag of the xml
     * document.
     * 
     * @return a collection of the saved GameObjects
     */
    public Collection<GameObject> getGameObjects () {
        String gameObjectDataFile = XmlUtilities.getChildContent(myRoot, XmlTags.GAMEOBJECT_DATA);
        Collection<GameObject> castGameObjects = new ArrayList<GameObject>();

        for (Object g : readSerializedObjects(gameObjectDataFile)) {
            castGameObjects.add((GameObject) g);
        }

        return castGameObjects;
    }

    /**
     * Gets a collection of all the serialized Conditions stored in the binary
     * file specified in the <code>coditionData</code> data tag of the xml
     * document.
     * 
     * @return a collection of the saved Conditions
     */
    public Collection<Condition> getConditions () {
        String conditionDataFile = XmlUtilities.getChildContent(myRoot, XmlTags.CONDITION);
        Collection<Condition> castConditions = new ArrayList<Condition>();

        for (Object c : readSerializedObjects(conditionDataFile)) {
            castConditions.add((Condition) c);
        }

        return castConditions;
    }

    /**
     * Gets a collection of all the serialized LevelPlugins stored in the binary
     * file specified in the <code>pluginData</code> data tag of the xml
     * document.
     * 
     * @return a collection of the saved LevelPlugins
     */
    public Collection<LevelPlugin> getLevelPlugins () {
        String pluginDataFile = XmlUtilities.getChildContent(myRoot, XmlTags.PLUGIN);
        Collection<LevelPlugin> castPlugins = new ArrayList<LevelPlugin>();

        for (Object lp : readSerializedObjects(pluginDataFile)) {
            castPlugins.add((LevelPlugin) lp);
        }

        return castPlugins;
    }

    /**
     * A general method for reading Objects from an Object input stream.
     * 
     * @param gameObjectDataFile file location of the binary file to be read
     * @return a Collection of Objects that were stored in this binary file
     */
    private Collection<Object> readSerializedObjects (String gameObjectDataFile) {

        FileInputStream fis;
        Collection<Object> inputObjects;

        try {
            fis = new FileInputStream(gameObjectDataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            inputObjects = new ArrayList<Object>();
            while (fis.available() > 0) {
                inputObjects.add((Object) ois.readObject());
            }
            ois.close();
        }
        catch (FileNotFoundException e) {
            throw new LevelFileIOException("File could not be found", e);
        }
        catch (IOException e) {
            throw new LevelFileIOException("An IO error occurred", e);
        }
        catch (ClassNotFoundException e) {
            throw new LevelFileIOException(
                                           "A class matching the serialized class in the data file could not found.",
                                           e);
        }
        return inputObjects;
    }
}
