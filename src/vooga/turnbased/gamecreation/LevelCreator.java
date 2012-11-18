package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.xml.XmlParser;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gameobject.MapPlayerObject;
import vooga.turnbased.sprites.Sprite;

/**
 * This class is designed to parse Xml data and create a level of
 * our game from this information, creating character sprites and
 * other objects that will either be interacted with or act as obstacles.
 *
 * @author Mark Hoffman
 *
 */
public class LevelCreator {

    private XmlParser myXmlParser;
    private Element myDocumentElement;

    /**
     *
     * @param file XML file used to create the level, the constructor
     * parameters may change in the future.
     */
    public LevelCreator (File file) {
        myXmlParser = new XmlParser(file);
        validateXml();
        myDocumentElement = myXmlParser.getDocumentElement();
    }

    /**
     *
     * @return The Dimension of the Level
     */
    public Dimension parseDimension () {
        NodeList dimensionList = myXmlParser.getElementsByName(
                myDocumentElement, "dimension");
        Element dimension = (Element) dimensionList.item(0);
        int width = myXmlParser.getIntContent(dimension, "width");
        int height = myXmlParser.getIntContent(dimension, "height");
        return new Dimension(width, height);
    }

    /**
     *
     * @return Background Image of the Level
     */
    public Image parseBackgroundImage () {
        return myXmlParser.getImageContent(
                myDocumentElement, "backgroundImage");
    }

    /**
     *
     * @return Player-controlled map object
     */
    public MapObject parseMapPlayer () {
        Element mapPlayer = isolateMapPlayer();
        
        String className = myXmlParser.getTextContent(mapPlayer, "class");
        int id = myXmlParser.getIntContent(mapPlayer, "id");
        GameManager.GameEvent event = parseEvent(mapPlayer);
        Point point = parseLocation(mapPlayer);
        Map<String, Image> imageMap = parseImagesMap(mapPlayer);
        
        return new MapPlayerObject(id, event, point, imageMap, null);
    }

    private Element isolateMapPlayer () {
        NodeList playerList = myXmlParser.getElementsByName(myDocumentElement, "player");
        Element player = (Element) playerList.item(0);
        NodeList mapList = myXmlParser.getElementsByName(player, "map");
        Element mapPlayer = (Element) mapList.item(0);
        return mapPlayer;
    }

    private GameManager.GameEvent parseEvent (Element element) {
        String eventString = myXmlParser.getTextContent(element, "event");
        GameManager.GameEvent event = null;
        for (GameManager.GameEvent current : GameManager.GameEvent.values()) {
            if (current.toString().equals(eventString)) {
                event = current;
            }
        }
        return event;
    }

    private Point parseLocation (Element element) {
        NodeList locationList = myXmlParser.getElementsByName(element, "location");
        Element location = (Element) locationList.item(0); 
        Point point = new Point(myXmlParser.getIntContent(location, "x"), 
                myXmlParser.getIntContent(location, "y"));
        return point;
    }

    private Map<String, Image> parseImagesMap (Element element) {
        NodeList imageList = myXmlParser.getElementsByName(element, "image");
        Map<String, Image> imageMap = new HashMap<String, Image>();
        for (int i = 0; i < imageList.getLength(); i++) {
        Element imageData = (Element) imageList.item(i);
            Image image = myXmlParser.getImageContent(imageData, "source");
            String direction = myXmlParser.getTextContent(imageData, "direction");
            imageMap.put(direction, image);
        }
        return imageMap;
    }
    /**
     *
     * @return List of Sprites in the Level
     */
    public List<Sprite> parseSprites () {
        return null;
    }

    /**
     * 
     * @return The Document Element from the Xml file
     */
    public Element getDocumentElement () {
        return myDocumentElement;
    }

    /**
     * Used to check for all required elements of the XML file.
     */
    private void validateXml () {
        // Empty until decided what is required of Xml
    }
}
