package vooga.platformer.levelfileio;

import java.awt.Image;
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


/**
 * Instances of this class are created using the path to an XML level data file.
 * Once the instance is created, the getter methods can be used to get the
 * contents of this data file as java types (ints and Sprites, rather than
 * string values).
 * 
 * @author Grant Oakley
 * @author Zach Michaelov (modified)
 */
public class LevelFileReader {

    private final Document myDocument;
    private Element myRoot;
    private File myLevelFile;

    /**
     * Creates a new LevelFileReader using the level data file specified.
     * 
     * @param levelFilePath path to the level data file (XML format)
     */
    public LevelFileReader (String levelFilePath) {
        this(new File(levelFilePath));
    }

    /**
     * Creates a new LevelFileReader using the File specified.
     * 
     * @param levelFile File in XML format representing the level to be read
     */

    public LevelFileReader (File levelFile) {
        myDocument = XmlUtilities.makeDocument(levelFile);
        myLevelFile = levelFile;
        myRoot = myDocument.getDocumentElement();
    }

    /**
     * Gets the level's type. This specifies what subclass of Level to use to
     * build the Level in LevelFactory.
     * 
     * @return the level's type
     */
    public String getLevelType () {
        return myRoot.getAttribute(XmlTags.CLASS_NAME);
    }

    /**
     * Gets the name of the level.
     * 
     * @return name of the level as a String
     */
    public String getLevelID () {
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
     * Gets the image that is to be the background scenery of the level. This
     * will be rendered behind the Sprites.
     * 
     * @return Image representing the background of the level
     */
    public Image getBackgroundImage () {
        return XmlUtilities.fileNameToImage(myLevelFile, XmlUtilities
                .getChildContent(myRoot, XmlTags.BACKGROUND_IMAGE));
    }

    /**
     * Gets the class name of the Camera to use for this particular level.
     * 
     * @return class name of this level's Camera subclass
     */
    public String getCameraType () {
        return XmlUtilities.getChildContent(myRoot, XmlTags.CAMERA);
    }

    /**
     * Gets a collection of all the serialized GameObjects stored in the binary
     * file specified in the GameObject data tag of the xml document. This
     * method will throw an exception if called on data files written using the
     * out dated <code>writeLevel(...)</code> method in LevelFileWriter.
     * 
     * @return a collection of the saved GameObjects
     */
    public Collection<GameObject> getGameObjects () {
        String gameObjectDataFile = XmlUtilities.getChildContent(myRoot, XmlTags.GAMEOBJECT_DATA);
        FileInputStream fis;
        Collection<GameObject> inputGameObjects;
        try {
            fis = new FileInputStream(gameObjectDataFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            inputGameObjects = new ArrayList<GameObject>();
            while (fis.available() > 0) {
                inputGameObjects.add((GameObject) ois.readObject());
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
        return inputGameObjects;
    }
}
