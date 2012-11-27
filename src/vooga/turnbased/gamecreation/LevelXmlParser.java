package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.imageprocessing.ImageLoop;
import util.reflection.Reflection;
import util.xml.XmlUtilities;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapPlayerObject;
import vooga.turnbased.gameobject.mapobject.MapTileObject;
import vooga.turnbased.sprites.Sprite;


/**
 * This class is designed to parse Xml data and create a level of
 * our game from this information, creating character sprites and
 * other objects that will either be interacted with or act as obstacles.
 * 
 * @author Mark Hoffman
 */
public class LevelXmlParser {

    private final String CLASS = "class";
    private final String EVENT = "event";
    private final String IMAGE = "image";
    private final String LEFT = "left";
    private final String RIGHT = "right";
    private final String UP = "up";
    private final String DOWN = "down";
    private final String MAP = "map";
    private final String X = "x";
    private final String Y = "y";
    private final String LOCATION = "location";
    private final String PLAYER_XML_PATH = "src/vooga/turnbased/resources/level/Player.xml";

    private Document myXmlDocument;
    private Document myPlayerXmlDocument;
    private Element myDocumentElement;
    private MapMode myMapMode;

    /**
     * 
     * @param file XML file used to create the level, the constructor
     *        parameters may change in the future.
     * @param mapMode The map mode of the level that is being parsed
     */
    public LevelXmlParser (File file, MapMode mapMode) {
        myXmlDocument = XmlUtilities.makeDocument(file);
        myDocumentElement = myXmlDocument.getDocumentElement();
        myMapMode = mapMode;
        myPlayerXmlDocument = XmlUtilities.makeDocument(PLAYER_XML_PATH);
    }

    /**
     * @param name The string name of the element containing dimensions
     * @return The Dimension of the Level
     */
    public Dimension parseDimension (String name) {
        List<Element> dimensionList =
                (List<Element>) XmlUtilities.getElements(myDocumentElement, name);
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
     * @return List of Sprites in the Level
     */
    public List<Sprite> parseSprites () {
        List<Sprite> toReturn = new ArrayList<Sprite>();
        toReturn.addAll(parseStaticSprites());
        toReturn.addAll(parseCharacterSprites());
        Sprite playerSprite = parsePlayerSprite();
        if (playerSprite != null) {
            toReturn.add(playerSprite);
        }
        return toReturn;
    }

    private Sprite parsePlayerSprite () {
        Sprite s = new Sprite();
        MapPlayerObject mapPlayer = parseMapPlayer(s);
        if (mapPlayer == null) { return null; }
        Map<String, ImageLoop> imageLoops = parsePlayerImageLoops(mapPlayer.getImageMap());
        mapPlayer.setImageLoops(imageLoops);
        myMapMode.setPlayer(mapPlayer);
        s.addGameObject(mapPlayer);
        return s;
    }

    private List<Sprite> parseStaticSprites () {
        List<Sprite> spriteList = new ArrayList<Sprite>();
        Sprite s = new Sprite();
        for (int i = 0; i < myMapMode.getMapSize().width; i++) {
            for (int j = 0; j < myMapMode.getMapSize().height; j++) {
                Point point = new Point(i, j);
                s = new Sprite();
                Element staticSprite = XmlUtilities.getElement(myDocumentElement, "staticSprite");
                String className = XmlUtilities.getChildContent(staticSprite, CLASS);
                String event = XmlUtilities.getChildContent(staticSprite, EVENT);
                Image image = XmlUtilities.getChildContentAsImage(staticSprite, IMAGE);
                MapTileObject mapTile =
                        (MapTileObject) Reflection.createInstance(className, s.getID(), event,
                                                                  point, image, myMapMode);
                s.addGameObject(mapTile);
                spriteList.add(s);
            }
        }
        return spriteList;
    }

    private List<Sprite> parseCharacterSprites () {
        List<Element> sprites =
                (List<Element>) XmlUtilities.getElements(myDocumentElement, "sprite");
        List<Sprite> spriteList = new ArrayList<Sprite>();
        for (int i = 0; i < sprites.size(); i++) {
            Element sprite = sprites.get(i);
            Sprite s = parseSprite(sprite);
            spriteList.add(s);
        }
        return spriteList;
    }

    private Map<String, ImageLoop> parsePlayerImageLoops (Map<String, Image> map) {
        Map<String, ImageLoop> imageLoops = new HashMap<String, ImageLoop>();
        List<Image> leftList = parseImageList(LEFT, map);
        List<Image> rightList = parseImageList(RIGHT, map);
        List<Image> upList = parseImageList(UP, map);
        List<Image> downList = parseImageList(DOWN, map);
        imageLoops.put(LEFT, new ImageLoop(leftList));
        imageLoops.put(RIGHT, new ImageLoop(rightList));
        imageLoops.put(UP, new ImageLoop(upList));
        imageLoops.put(DOWN, new ImageLoop(downList));
        return imageLoops;
    }

    private List<Image> parseImageList (String direction, Map<String, Image> map) {
        List<Image> list = new ArrayList<Image>();
        for (String key : map.keySet()) {
            if (key.contains(direction)) {
                list.add(map.get(key));
            }
        }
        return list;
    }

    private MapPlayerObject parseMapPlayer (Sprite s) {
        Element mapPlayer = isolateMapPlayer();
        if (mapPlayer == null) { return null; }
        String className = XmlUtilities.getChildContent(mapPlayer, CLASS);
        String event = XmlUtilities.getChildContent(mapPlayer, EVENT);
        Point point = parseLocation(mapPlayer);
        Map<String, Image> imageMap = parsePlayerImages(mapPlayer);

        return (MapPlayerObject) Reflection.createInstance(className, s.getID(), event, point,
                                                           imageMap, myMapMode);
    }

    private Element isolateMapPlayer () {
        Element playerRoot = myPlayerXmlDocument.getDocumentElement();
        List<Element> playerList =
                (List<Element>) XmlUtilities.getElements(playerRoot, "player");
        if (playerList.isEmpty()) { return null; }
        Element player = playerList.get(0);
        List<Element> mapList = (List<Element>) XmlUtilities.getElements(player, MAP);
        Element mapPlayer = mapList.get(0);
        return mapPlayer;
    }

    private Point parseLocation (Element element) {
        List<Element> locationList = (List<Element>) XmlUtilities.getElements(element, LOCATION);
        Element location = locationList.get(0);
        Point point =
                new Point(XmlUtilities.getChildContentAsInt(location, X),
                          XmlUtilities.getChildContentAsInt(location, Y));
        return point;
    }

    private Map<String, Image> parsePlayerImages (Element element) {
        List<Element> imageList = (List<Element>) XmlUtilities.getElements(element, IMAGE);
        Map<String, Image> imageMap = new HashMap<String, Image>();
        for (int i = 0; i < imageList.size(); i++) {
            Element imageData = imageList.get(i);
            Image image = XmlUtilities.getChildContentAsImage(imageData, "source");
            String direction = XmlUtilities.getChildContent(imageData, "direction");
            imageMap.put(direction, image);
        }
        return imageMap;
    }

    private Sprite parseSprite (Element sprite) {
        Sprite s = new Sprite();
        MapObject mapObject = parseMapObject(s, sprite);
        if (mapObject != null) {
            s.addGameObject(mapObject);
        }
        BattleObject battleObject = parseBattleObject(s, sprite);
        if (battleObject != null) {
            s.addGameObject(battleObject);
        }
        return s;
    }

    private BattleObject parseBattleObject (Sprite s, Element sprite) {
        Element battleSprite = XmlUtilities.getElement(sprite, "battle");
        if (battleSprite.hasChildNodes()) {
            String className = XmlUtilities.getChildContent(battleSprite, CLASS);
            String event = XmlUtilities.getChildContent(battleSprite, EVENT);
            Image image = XmlUtilities.getChildContentAsImage(battleSprite, IMAGE);
            Map<String, Number> stats = new HashMap<String, Number>();
            Element battleStats = XmlUtilities.getElement(battleSprite, "stats");
            if (battleStats.hasChildNodes()) {
                //is there a way to do this without knowing what the name of each stat?
                //ie. iterate through the child nodes?
                stats.put("attack", XmlUtilities.getChildContentAsDouble(battleStats, "attack"));
                stats.put("defense", XmlUtilities.getChildContentAsDouble(battleStats, "defense"));
                stats.put("health", XmlUtilities.getChildContentAsDouble(battleStats, "health"));
                stats.put("maxHealth", XmlUtilities.getChildContentAsDouble(battleStats, "health"));
            }
            String name = XmlUtilities.getChildContent(battleSprite, "name");
            BattleObject battleObject =
                    (BattleObject) Reflection.createInstance(className, s.getID(), 
                            event, stats, name, image);
            return battleObject;
        }
        return null;
    }

    private MapObject parseMapObject (Sprite s, Element sprite) {
        Element mapSprite = XmlUtilities.getElement(sprite, MAP);
        if (mapSprite.hasChildNodes()) {
            String className = XmlUtilities.getChildContent(mapSprite, CLASS);
            String event = XmlUtilities.getChildContent(mapSprite, EVENT);
            Element location = XmlUtilities.getElement(mapSprite, LOCATION);
            Point point =
                    new Point(XmlUtilities.getChildContentAsInt(location, X),
                              XmlUtilities.getChildContentAsInt(location, Y));
            Image image = XmlUtilities.getChildContentAsImage(mapSprite, IMAGE);
            MapObject mapObject =
                    (MapObject) Reflection.createInstance(className, s.getID(), event, point,
                                                          image, myMapMode);
            // I'll delete it as soon as possible
            /*if (point.equals(new Point(10, 10))) {
                mapObject
                        .setStrategy(new TransportStrategy(
                                               myMapMode,
                                               "src/vooga/turnbased/resources/level/Level-one.xml",
                                               new Point(0, 0)));
            }*/

            return mapObject;
        }
        return null;
    }
}
