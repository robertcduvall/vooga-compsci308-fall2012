package vooga.platformer.leveleditor;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vooga.platformer.util.xml.XMLUtils;
<<<<<<< HEAD
=======
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8


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
    public LevelFileReader(String levelFilePath) {
        this(new File(levelFilePath));
    }

    /**
     * Creates a new LevelFileReader using the File specified.
     * 
     * @param levelFile File in XML format representing the level to be read
     */
<<<<<<< HEAD
    public LevelFileReader(File levelFile) {
        document = XMLUtils.initializeDocument(levelFile);
        root = document.getElementById("level");
=======
    public LevelFileReader (File levelFile) {
        myDocument = XMLUtils.initializeDocument(levelFile);
        myRoot = myDocument.getDocumentElement();
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    /**
     * Gets the name of the level.
     * 
     * @return name of the level as a String
     */
<<<<<<< HEAD
    public String getLevelID() {
        return XMLUtils.getTagValue("id", root);
=======
    public String getLevelID () {
        return XMLUtils.getTagValue("id", myRoot);
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    /**
     * Gets the overall width of the level.
     * 
     * @return width of the level as an int
     */
<<<<<<< HEAD
    public int getWidth() {
        return XMLUtils.getTagInt("width", root);
=======
    public int getWidth () {
        return XMLUtils.getTagInt("width", myRoot);
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    /**
     * Gets the overall height of the level.
     * 
     * @return height of the level as an int
     */
<<<<<<< HEAD
    public int getHeight() {
        return XMLUtils.getTagInt("height", root);
=======
    public int getHeight () {
        return XMLUtils.getTagInt("height", myRoot);
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    /**
     * Gets the image that is to be the background scenery of the level. This
     * will be rendered behind the Sprites.
     * 
     * @return Image representing the background of the level
     */
<<<<<<< HEAD
    public Image getBackgroundImage() {
        return XMLUtils.fileNameToImage(levelFile,
                XMLUtils.getTagValue("backgroundImage", root));
=======
    public Image getBackgroundImage () {
        return XMLUtils.fileNameToImage(myLevelFile,
                                        XMLUtils.getTagValue("backgroundImage", myRoot));
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    /**
     * Gets all the elements in the level data file tagged as gameObjects. The
     * Sprite objects are built using the parameters specified in level data
     * file.
     * 
     * @return a collection of Sprite objects representing the level's
     *         gameObjects
     */
<<<<<<< HEAD
    public Collection<Sprite> getSprites() {
        NodeList spritesNode = document.getElementsByTagName("gameobject");
        Collection<Sprite> spritesList = new ArrayList<Sprite>(
                spritesNode.getLength());
=======
    public Collection<Sprite> getSprites () {
        NodeList spritesNode = myDocument.getElementsByTagName("gameobject");
        Collection<Sprite> spritesList = new ArrayList<Sprite>(spritesNode.getLength());
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8

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

    private Sprite buildSprite(Element spriteElement) {
        String tag = spriteElement.getAttribute("type");
        int x = XMLUtils.getTagInt("x", spriteElement);
        int y = XMLUtils.getTagInt("y", spriteElement);
        int width = XMLUtils.getTagInt("width", spriteElement);
        int height = XMLUtils.getTagInt("height", spriteElement);
        String imagePath = XMLUtils.getTagValue("imagePath", spriteElement);
        Sprite builtSprite = new Sprite(tag, x, y, width, height, imagePath);
        return builtSprite;
    }

<<<<<<< HEAD
    private void addUpdateStrategies(Element spriteElement, Sprite builtSprite) {
        // TODO add update strategies
=======
    private void addUpdateStrategies (Element spriteElement, Sprite builtSprite) {
        NodeList strategiesNodeList = spriteElement.getElementsByTagName("strategies");

        for (int i = 0; i < strategiesNodeList.getLength(); i++) {
            Node strategiesNode = strategiesNodeList.item(i);
            if (strategiesNode.getNodeType() == Node.ELEMENT_NODE) {
                Element strategiesElement = (Element) strategiesNode;
                NodeList strategyNodeList = strategiesElement.getElementsByTagName("strategy");

                for (int j = 0; j < strategyNodeList.getLength(); j++) {
                    Node strategyNode = strategyNodeList.item(j);
                    if (strategyNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element strategyElement = (Element) strategyNode;
                        NodeList paramNodeList = strategyElement.getChildNodes();
                        Map<String, String> strategyMap = new HashMap<String, String>();

                        for (int k = 0; k < paramNodeList.getLength(); k++) {
                            Node paramNode = paramNodeList.item(k);
                            if (paramNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element paramElement = (Element) paramNode;
                                strategyMap.put(paramElement.getTagName(),
                                                XMLUtils.getTagValue(paramElement));
                            }
                        }
                        builtSprite.addUpdateStrategy(strategyMap);
                    }
                }
            }
        }
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    private void addSpriteAttributes(Element spriteElement, Sprite builtSprite) {
        // TODO add attributes
    }

}
