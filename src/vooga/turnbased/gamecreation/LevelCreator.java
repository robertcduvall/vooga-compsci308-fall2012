package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.reflection.Reflection;
import util.xml.XmlParser;
import util.xml.XmlUtilities;
import vooga.turnbased.gameobject.BattleObject;
import vooga.turnbased.gameobject.MapObject;
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

    private Document myXmlDocument;
    private Element myDocumentElement;

    /**
     * 
     * @param file XML file used to create the level, the constructor
     *        parameters may change in the future.
     */
    public LevelCreator (File file) {
        myXmlDocument = XmlUtilities.makeDocument(file);
        myDocumentElement = myXmlDocument.getDocumentElement();
    }

    /**
     * 
     * @return The Dimension of the Level
     */
    public Dimension parseDimension () {
        List<Element> dimensionList = (List<Element>) XmlUtilities.getElements(myDocumentElement, "dimension");
        Element dimension = dimensionList.get(0);
        int width = XmlUtilities.getChildContentAsInt(dimension, "width");
        int height = XmlUtilities.getChildContentAsInt(dimension, "height");
        return new Dimension(width, height);
    }

    /**
     * 
     * @return Background Image of the Level
     */
    public Image parseBackgroundImage () {
        return XmlUtilities.getChildContentAsImage(myDocumentElement, "backgroundImage");
    }

    /**
     * 
     * @return Player-controlled map object
     */
    public MapObject parseMapPlayer () {
        Element mapPlayer = isolateMapPlayer();

        String className = XmlUtilities.getChildContent(mapPlayer, "class");
        int id = XmlUtilities.getChildContentAsInt(mapPlayer, "id");
        String event = XmlUtilities.getChildContent(mapPlayer, "event");
        Point point = parseLocation(mapPlayer);
        Map<String, Image> imageMap = parseImagesMap(mapPlayer);

        return (MapObject) Reflection.createInstance(className, id, event,
                point, imageMap);
    }

    public BattleObject parserBattlePlayer () {
        Element battlePlayer = isolateBattlePlayer();
        String className = XmlUtilities.getChildContent(battlePlayer, "class");
        int id = XmlUtilities.getChildContentAsInt(battlePlayer, "id");
        String event = XmlUtilities.getChildContent(battlePlayer, "event");
        int health = XmlUtilities.getChildContentAsInt(battlePlayer, "health"); 
        int defense = XmlUtilities.getChildContentAsInt(battlePlayer, "defense"); 
        int attack = XmlUtilities.getChildContentAsInt(battlePlayer, "attack"); 
        Image image = XmlUtilities.getChildContentAsImage(battlePlayer, "image");
        return (BattleObject) Reflection.createInstance(className, id, event,
                defense, attack, health, image);
    }

    private Element isolateBattlePlayer () {
        List<Element> playerList = (List<Element>) XmlUtilities.getElements(myDocumentElement, "player");
        Element player = (Element) playerList.get(0);
        List<Element> battleList = (List<Element>) XmlUtilities.getElements(player, "battle");
        Element battlePlayer = (Element) battleList.get(0);
        return battlePlayer;
    }

    private Element isolateMapPlayer () {
        List<Element> playerList = (List<Element>) XmlUtilities.getElements(myDocumentElement, "player");
        Element player = (Element) playerList.get(0);
        List<Element> mapList = (List<Element>) XmlUtilities.getElements(player, "map");
        Element mapPlayer = (Element) mapList.get(0);
        return mapPlayer;
    }

    private Point parseLocation (Element element) {
        List<Element> locationList = (List<Element>) XmlUtilities.getElements(element, "location");
        Element location = (Element) locationList.get(0);
        Point point = new Point(XmlUtilities.getChildContentAsInt(location,"x"),
                XmlUtilities.getChildContentAsInt(location, "y"));
        return point;
    }

    private Map<String, Image> parseImagesMap (Element element) {
        List<Element> imageList = (List<Element>) XmlUtilities.getElements(element, "image");
        Map<String, Image> imageMap = new HashMap<String, Image>();
        for (int i = 0; i < imageList.size(); i++) {
            Element imageData = (Element) imageList.get(i);
            Image image = XmlUtilities.getChildContentAsImage(imageData, "source");
            String direction = XmlUtilities.getChildContent(imageData, "direction");
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
}
