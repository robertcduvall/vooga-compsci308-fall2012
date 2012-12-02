package vooga.platformer.levelfileio;

import java.awt.Image;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.xml.XmlUtilities;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.leveleditor.Sprite;


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
     * Gets the class name of the CollisionChecker to use for this particular
     * level.
     * 
     * @return class name of this level's CollisionChecker subclass
     */
    public String getCollisionCheckerType () {
        return XmlUtilities.getChildContent(myRoot, XmlTags.COLLISION_CHECKER);
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

    /**
     * Gets all the elements in the level data file tagged as gameObjects. The
     * Sprite objects are built using the parameters specified in level data
     * file.
     * 
     * @return a collection of Sprite objects representing the level's
     *         gameObjects
     * 
     * @deprecated Sprites are no longer supported in the revised file format
     *             for saving levels. Use getGameObjects() instead.
     */

    public Collection<Sprite> getSprites () {
        NodeList spritesNode = myDocument.getElementsByTagName(XmlTags.GAMEOBJECT);
        Collection<Sprite> spritesList = new ArrayList<Sprite>(spritesNode.getLength());

        for (int i = 0; i < spritesNode.getLength(); i++) {
            Node spriteNode = spritesNode.item(i);
            if (spriteNode.getNodeType() == Node.ELEMENT_NODE) {
                Element spriteElement = (Element) spriteNode;
                Sprite builtSprite = buildSprite(spriteElement);
                addUpdateStrategies(spriteElement, builtSprite);
                addSpriteAttributes(spriteElement, builtSprite);
                spritesList.add(builtSprite);
            }
        }

        return spritesList;
    }

    private Sprite buildSprite (Element spriteElement) {
        String className = spriteElement.getAttribute(XmlTags.CLASS_NAME);
        int x = XmlUtilities.getChildContentAsInt(spriteElement, XmlTags.X);
        int y = XmlUtilities.getChildContentAsInt(spriteElement, XmlTags.Y);
        int width = XmlUtilities.getChildContentAsInt(spriteElement, XmlTags.WIDTH);
        int height = XmlUtilities.getChildContentAsInt(spriteElement, XmlTags.HEIGHT);
        String spriteID = XmlUtilities.getChildContent(spriteElement, XmlTags.ID);
        String imagePath = XmlUtilities.getChildContent(spriteElement, XmlTags.IMAGE_PATH);

        return new Sprite(className, x, y, width, height, spriteID, imagePath);
    }

    private void addUpdateStrategies (Element spriteElement, Sprite builtSprite) {
        Collection<Element> strategies = XmlUtilities.getElements(spriteElement, XmlTags.STRATEGY);

        for (Element strategy : strategies) {
            Map<String, String> strategyMap = new HashMap<String, String>();
            Collection<Element> attributes =
                    XmlUtilities.convertNodeListToCollection(strategy.getChildNodes());
            for (Element attr : attributes) {
                strategyMap.put(attr.getTagName(), XmlUtilities.getContent(attr));
            }
            builtSprite.addUpdateStrategy(strategy.getAttribute(XmlTags.CLASS_NAME), strategyMap);
        }
    }

    private void addSpriteAttributes (Element spriteElement, Sprite builtSprite) {
        NodeList attrNodeList = spriteElement.getElementsByTagName(XmlTags.CONFIG);

        for (int i = 0; i < attrNodeList.getLength(); i++) {
            Node attrNode = attrNodeList.item(i);
            if (attrNode.getNodeType() == Node.ELEMENT_NODE) {
                Element attrElement = (Element) attrNode;
                Map<String, String> attrMap = XmlUtilities.extractMapFromXml(attrElement);
                for (String str : attrMap.keySet()) {
                    builtSprite.addAttribute(str, attrMap.get(str));
                }
            }
        }
    }
}
