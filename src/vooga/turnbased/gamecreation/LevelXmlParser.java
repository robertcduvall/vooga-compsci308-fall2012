package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.graphicprocessing.ImageLoop;
import util.reflection.Reflection;
import util.xml.XmlUtilities;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapPlayerObject;
import vooga.turnbased.gameobject.mapstrategy.ConversationStrategy;
import vooga.turnbased.gameobject.mapstrategy.TransportStrategy;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.sprites.Sprite;


/**
 * This class is designed to parse Xml data and create a level of
 * our game from this information, creating character sprites and
 * other objects that will either be interacted with or act as obstacles.
 * 
 * @author Mark Hoffman
 */
public class LevelXmlParser {

    private static final String GAME_SETUP = "gameSetup";
    private static final String MODE_DEFS = "modeDeclarations";
    private static final String SPRITE = "sprite";
    private static final String NAME = "name";
    private static final String MODE = "mode";
    private static final String MODES = "modes";
    private static final String CLASS = "class";
    private static final String CONDITION = "condition";
    private static final String IMAGE = "image";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String MAP = "map1";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String LOCATION = "location";
    private static final String PLAYER = "player";
    private static final String OBJECT = "object";

    private Document myXmlDocument;
    private Document myPlayerXmlDocument;
    private Element myDocumentElement;
    private Element myPlayerElement;

    private int myPlayerID;
    private String myStartMode;
    private Dimension myMapSize;
    private Dimension myCameraSize;
    private String myBackgroundImage;

    /**
     * 
     * @param file XML file used to create the level, the constructor
     *        parameters may change in the future.
     * @param gm The game manager of the level that is being parsed
     */
    public LevelXmlParser (String gameFilePath, String playerFilePath, GameManager gm) {
        myXmlDocument = XmlUtilities.makeDocument(new File(gameFilePath));
        myDocumentElement = myXmlDocument.getDocumentElement();
        myPlayerXmlDocument = XmlUtilities.makeDocument(new File(playerFilePath));
        myPlayerElement = myPlayerXmlDocument.getDocumentElement();
        parseSetup();
    }

    private void parseSetup () {
        Element gameSetupElement = XmlUtilities.getElement(myDocumentElement, GAME_SETUP);
        myStartMode = XmlUtilities.getChildContent(gameSetupElement, "startMode");
        myBackgroundImage = XmlUtilities.getChildContent(gameSetupElement, "backgroundImage");
        myMapSize = parseDimension(GameWindow.importString("MapDimension"));
        myCameraSize = parseDimension(GameWindow.importString("CameraDimension"));
    }

    public Dimension getMapSize () {
        return myMapSize;
    }

    public Dimension getCameraSize () {
        return myCameraSize;
    }

    public String getStartMode () {
        return myStartMode;
    }

    public String getBackgroundImage () {
        return myBackgroundImage;
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
        // TODO:
        toReturn.add(parseSprite(myPlayerElement));
        return toReturn;
    }

    /**
     * 
     * @return The player-controlled sprite.
     */
    public Sprite parseSprite (Element spriteElement) {
        Sprite s = new Sprite();
        List<Element> playerObjects =
                (List<Element>) XmlUtilities.getElements(spriteElement, OBJECT);
        for (Element playerObject : playerObjects) {
            GameObject newObject = parseGameObject(playerObject);
            if (newObject != null) {
                s.addGameObject(newObject);
            }
        }
        myPlayerID = s.getID();
        return s;
    }
    
    private List<Sprite> parseCharacterSprites () {
        List<Element> sprites = (List<Element>) XmlUtilities.getElements(myDocumentElement, SPRITE);
        List<Sprite> spriteList = new ArrayList<Sprite>();
        for (Element sprite : sprites) {
            Sprite s = parseSprite(sprite);
            spriteList.add(s);
        }
        return spriteList;
    }

    private List<Sprite> parseStaticSprites () {
        List<Sprite> spriteList = new ArrayList<Sprite>();
        Sprite s = new Sprite();
        for (int i = 0; i < myMapSize.width; i++) {
            for (int j = 0; j < myMapSize.height; j++) {
                Point point = new Point(i, j);
                s = new Sprite();
                Element staticSprite = XmlUtilities.getElement(myDocumentElement, "staticSprite");
                String className = XmlUtilities.getChildContent(staticSprite, CLASS);
                String condition = XmlUtilities.getChildContent(staticSprite, CONDITION);
                Set<String> modes = getObjectsModes(staticSprite);
                Image image = XmlUtilities.getChildContentAsImage(staticSprite, IMAGE);
                MapObject mapTile =
                        (MapObject) Reflection.createInstance(className, modes, condition, point,
                                                              image);
                s.addGameObject(mapTile);
                spriteList.add(s);
            }
        }
        return spriteList;
    }
    
    private GameObject parseGameObject (Element objectElement) {
        // this is nasty... given more time, reflection could have solved this
        String objectClass = XmlUtilities.getChildContent(objectElement, CLASS);
        switch (objectClass) {
            case "vooga.turnbased.gameobject.mapobject.MapPlayerObject":
                return (GameObject) parseMapPlayer(objectElement);
            case "vooga.turnbased.gameobject.battleobject.TestMonster":
                return (GameObject) parseBattleObject(objectElement);
            case "vooga.turnbased.gameobject.mapobject.MapEnemyObject":
                return (GameObject) parseMapObject(objectElement);
            case "vooga.turnbased.gameobject.mapobject.MapObstacleObject":
                break;
            case "vooga.turnbased.gameobject.optionobject.OptionObject":
                break;
            case "vooga.turnbased.gameobject.mapobject.MovingMapObject":
                break;
        }
        return null;
    }

    private MapPlayerObject parseMapPlayer (Element mapPlayer) {
        if (mapPlayer == null) { return null; }
        String className = XmlUtilities.getChildContent(mapPlayer, CLASS);
        String event = XmlUtilities.getChildContent(mapPlayer, CONDITION);
        Set<String> modes = getObjectsModes(mapPlayer);
        Point point = parseLocation(mapPlayer);
        Map<String, Image> playerImages = parsePlayerImages(mapPlayer);
        Map<String, ImageLoop> imageMap = parseImageLoops(playerImages);
        MapPlayerObject myMapPlayer =
                (MapPlayerObject) Reflection.createInstance(className, modes, event, point,
                                                            playerImages);
        myMapPlayer.setImageLoops(imageMap);
        return myMapPlayer;
    }

    private Set<String> getObjectsModes (Element objectElement) {
        return new TreeSet<String>(Arrays.asList(XmlUtilities.getChildContent(objectElement, MODES)
                .split("\\s*,\\s*")));
    }

    private Map<String, ImageLoop> parseImageLoops (Map<String, Image> map) {
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

    private List<Element> getListOfPlayerElements () {
        Element playerRoot = myPlayerXmlDocument.getDocumentElement();
        List<Element> playerList = (List<Element>) XmlUtilities.getElements(playerRoot, PLAYER);
        if (playerList.isEmpty()) { return null; }
        return playerList;
    }

    private Element isolateMapPlayer () {
        Element playerRoot = myPlayerXmlDocument.getDocumentElement();
        List<Element> playerList = (List<Element>) XmlUtilities.getElements(playerRoot, PLAYER);
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
        for (Element imageData : imageList) {
            Image image = XmlUtilities.getChildContentAsImage(imageData, "source");
            String direction = XmlUtilities.getChildContent(imageData, "direction");
            imageMap.put(direction, image);
        }
        return imageMap;
    }

    private ImageLoop parseObjectImages (Element element) {
        List<Image> images = new ArrayList<Image>();
        List<Element> imageList = (List<Element>) XmlUtilities.getElements(element, IMAGE);
        for (Element e : imageList) {
            Image img = XmlUtilities.getContentAsImage(e);
            images.add(img);
        }
        return new ImageLoop(images);
    }

//    private List<MapObject> parseMapObjects (Sprite s, Element sprite) {
//        Collection<Element> mapSprites = XmlUtilities.getElements(sprite, "map");
//        List<MapObject> mapObjects = new ArrayList<MapObject>();
//        for (Element ms : mapSprites) {
//            mapObjects.add(parseMapObject(s, ms));
//        }
//        return mapObjects;
//    }

    // private List<BattleObject> parseBattleObjects (Sprite s, Element sprite)
    // {
    // Collection<Element> battleSprites = XmlUtilities.getElements(sprite,
    // "battle");
    // List<BattleObject> battleObjects = new ArrayList<BattleObject>();
    // for (Element bs : battleSprites) {
    // battleObjects.add(parseBattleObject(s, bs));
    // }
    // return battleObjects;
    // }

    private BattleObject parseBattleObject (Element battleSprite) {
        if (battleSprite.hasChildNodes()) {
            String className = XmlUtilities.getChildContent(battleSprite, CLASS);
            String event = XmlUtilities.getChildContent(battleSprite, CONDITION);
            Image image = XmlUtilities.getChildContentAsImage(battleSprite, IMAGE);
            Map<String, Number> stats = parseBattleStats(battleSprite);
            String name = XmlUtilities.getChildContent(battleSprite, NAME);
            BattleObject battleObject =
                    (BattleObject) Reflection.createInstance(className,
                                                             new TreeSet<String>(Arrays
                                                                     .asList("battle")), event,
                                                             stats, name, image);
            battleObject.setImageLoop(parseObjectImages(battleSprite));
            return battleObject;
        }
        return null;
    }

    private Map<String, Number> parseBattleStats (Element battleSprite) {
        Map<String, Number> stats = new HashMap<String, Number>();
        Element battleStats = XmlUtilities.getElement(battleSprite, "stats");
        if (battleStats.hasChildNodes()) {
            NodeList statsList = battleStats.getChildNodes();
            for (int i = 0; i < statsList.getLength(); i++) {
                if (!"#text".equals(statsList.item(i).getNodeName())) {
                    stats.put(statsList.item(i).getNodeName(),
                              Double.parseDouble(statsList.item(i).getTextContent()));
                }
            }
        }
        return stats;
    }

    private MapObject parseMapObject (Element mapObjectElement) {
        if (mapObjectElement.hasChildNodes()) {
            String className = XmlUtilities.getChildContent(mapObjectElement, CLASS);
            String event = XmlUtilities.getChildContent(mapObjectElement, CONDITION);
            Element location = XmlUtilities.getElement(mapObjectElement, LOCATION);
            Set<String> modes = getObjectsModes(mapObjectElement);
            Point point =
                    new Point(XmlUtilities.getChildContentAsInt(location, X),
                              XmlUtilities.getChildContentAsInt(location, Y));
            Image image = XmlUtilities.getChildContentAsImage(mapObjectElement, IMAGE);
            MapObject mapObject =
                    (MapObject) Reflection
                            .createInstance(className, modes,
                                            event, point, image);
            // I'll delete it as soon as possible
            if (point.equals(new Point(10, 10))) {
                mapObject.addStrategy(new TransportStrategy(mapObject, new Point(8, 8)));
            }
            if (point.equals(new Point(9, 3))) {
                mapObject.addStrategy(new ConversationStrategy());
                // mapObject.addStrategy(mapStrategy);
            }

            return mapObject;
        }
        return null;
    }

    /**
     * Return the player ID
     */
    public int getPlayerID () {
        return myPlayerID;
    }

    /**
     * @return The user defined modes for the game
     */
    public Map<String, Class> getUserDefinedModes () {
        Map<String, Class> userDefinedModes = new HashMap<String, Class>();
        Element modeDefinitionsElement = XmlUtilities.getElement(myDocumentElement, MODE_DEFS);
        List<Element> allModes =
                (List<Element>) XmlUtilities.getElements(modeDefinitionsElement, MODE);
        for (Element mode : allModes) {
            try {
                String name = XmlUtilities.getChildContent(mode, NAME);
                Class modeClass = Class.forName(XmlUtilities.getChildContent(mode, CLASS));
                userDefinedModes.put(name, modeClass);
            }
            catch (ClassNotFoundException e) {
            }
        }
        return userDefinedModes;
    }

    /**
     * @return Map of events and conditions
     */
    public Map<String, List<List<String>>> getEventConditionMapping () {
        Map<String, List<List<String>>> conditionMap = new HashMap<String, List<List<String>>>();
        Element modeDefinitionsElement = XmlUtilities.getElement(myDocumentElement, MODE_DEFS);
        List<Element> allModes =
                (List<Element>) XmlUtilities.getElements(modeDefinitionsElement, MODE);
        for (Element mode : allModes) {
            String name = XmlUtilities.getChildContent(mode, NAME);
            conditionMap.put(name, new ArrayList<List<String>>());
            List<Element> conditionElements =
                    (List<Element>) XmlUtilities.getElements(mode, CONDITION);
            for (Element conditionElement : conditionElements) {
                String[] conditions = XmlUtilities.getContent(conditionElement).split("\\s*,\\s*");
                conditionMap.get(name).add(Arrays.asList(conditions));
            }
        }
        return conditionMap;
    }
}
