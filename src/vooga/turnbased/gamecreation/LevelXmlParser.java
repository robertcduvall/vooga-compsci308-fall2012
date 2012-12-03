package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
import vooga.turnbased.gamecore.gamemodes.GameMode;
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
 * @author Mark Hoffman, David Howdyshell
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
    private static final String X = "x";
    private static final String Y = "y";
    private static final String LOCATION = "location";
    private static final String OBJECT = "object";
    private static final String START_MODE = "startMode";
    private static final String BACKGROUND_IMAGE = "backgroundImage";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String MAP_SIZE = "MapDimension";
    private static final String CAMERA_SIZE = "CameraDimension";
    private static final String CSV_REGEX = "\\s*,\\s*";

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
     * @param gameFilePath XML file path for file used to create the level
     * @param playerFilePath XML file path for file used to construct
     *        information about player
     * @param gm The game manager of the level that is being parsed
     */
    public LevelXmlParser (String gameFilePath, String playerFilePath, GameManager gm) {
        myXmlDocument = XmlUtilities.makeDocument(new File(gameFilePath));
        myDocumentElement = myXmlDocument.getDocumentElement();
        myPlayerXmlDocument = XmlUtilities.makeDocument(new File(playerFilePath));
        myPlayerElement = myPlayerXmlDocument.getDocumentElement();
        parseSetup();
    }

    /**
     * Parses setup portion of the game's XML file.
     */
    private void parseSetup () {
        Element gameSetupElement = XmlUtilities.getElement(myDocumentElement, GAME_SETUP);
        myStartMode = XmlUtilities.getChildContent(gameSetupElement, START_MODE);
        myBackgroundImage = XmlUtilities.getChildContent(gameSetupElement, BACKGROUND_IMAGE);
        myMapSize = parseDimension(GameWindow.importString(MAP_SIZE));
        myCameraSize = parseDimension(GameWindow.importString(CAMERA_SIZE));
    }

    /**
     * Getter for size of map measured by grid spaces
     * 
     * @return - size of map
     */
    public Dimension getMapSize () {
        return myMapSize;
    }

    /**
     * Getter for size of map that camera displays at a time
     * 
     * @return - size of camera view
     */
    public Dimension getCameraSize () {
        return myCameraSize;
    }

    /**
     * Name of the first mode for the game to load
     * 
     * @return - starting mode name
     */
    public String getStartMode () {
        return myStartMode;
    }

    /**
     * String path of the background image
     * 
     * @return
     */
    public String getBackgroundImage () {
        return myBackgroundImage;
    }

    /**
     * @param name - The string name of the element containing dimensions
     * @return The Dimension of the Level
     */
    public Dimension parseDimension (String name) {
        List<Element> dimensionList =
                (List<Element>) XmlUtilities.getElements(myDocumentElement, name);
        Element dimension = dimensionList.get(0);
        int width = XmlUtilities.getChildContentAsInt(dimension, WIDTH);
        int height = XmlUtilities.getChildContentAsInt(dimension, HEIGHT);
        return new Dimension(width, height);
    }

    /**
     * 
     * @return Background Image of the Level
     */
    public Image parseBackgroundImage () {
        return XmlUtilities.getChildContentAsImage(myDocumentElement, BACKGROUND_IMAGE);
    }

    /**
     * 
     * @return List of Sprites in the Level
     */
    public List<Sprite> parseSprites () {
        List<Sprite> toReturn = new ArrayList<Sprite>();
        toReturn.addAll(parseStaticSprites());
        toReturn.addAll(parseCharacterSprites());
        toReturn.add(parsePlayerSprite());
        return toReturn;
    }

    /**
     * Parses the background sprites that fill up the map
     * 
     * @return List of background sprites that don't do anything
     */
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

    /**
     * Parses the NPC's sprites from the game's XML
     * 
     * @return a list of NPC sprites to populate the game
     */
    private List<Sprite> parseCharacterSprites () {
        List<Element> sprites = (List<Element>) XmlUtilities.getElements(myDocumentElement, SPRITE);
        List<Sprite> spriteList = new ArrayList<Sprite>();
        for (Element sprite : sprites) {
            Sprite s = parseSprite(sprite);
            spriteList.add(s);
        }
        return spriteList;
    }

    /**
     * Parses the player's sprite from the player's XML file
     * 
     * @return the player's sprite object
     */
    private Sprite parsePlayerSprite () {
        return parseSprite(myPlayerElement);
    }

    /**
     * Creates a sprite object from a Sprite element in the XML file
     * 
     * @param spriteElement XML element defining the sprite
     * @return a sprite
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

    /**
     * Creates a GameObject from the object element in the XML
     * 
     * @param objectElement - an XML element that defines some GameObject
     * @return - the actual game object
     */
    private GameObject parseGameObject (Element objectElement) {
        // this is nasty... given more time, reflection could have solved this
        String objectClass = XmlUtilities.getChildContent(objectElement, CLASS);
        if ("vooga.turnbased.gameobject.mapobject.MapPlayerObject".equals(objectClass)) {
            return (GameObject) parseMapPlayer(objectElement);
        }
        else if ("vooga.turnbased.gameobject.battleobject.TestMonster".equals(objectClass)) {
            return (GameObject) parseBattleObject(objectElement);
        }
        else if ("vooga.turnbased.gameobject.mapobject.MapEnemyObject".equals(objectClass)) {
            return (GameObject) parseMapObject(objectElement);
        }
        else if ("vooga.turnbased.gameobject.mapobject.MapObstacleObject".equals(objectClass)) {
            return (GameObject) parseMapObject(objectElement);
        }
        else if ("vooga.turnbased.gameobject.mapobject.MovingMapObject".equals(objectClass)) {
            return (GameObject) parseMapObject(objectElement);
        }
        else if ("vooga.turnbased.gameobject.optionobject.OptionObject".equals(objectClass)) {
            // OptionObject not yet implemented
        }
        return null;
    }

    /**
     * Creates an object of type MapPlayerObject from XML description
     * 
     * @param mapPlayer - element in XML describing the map player
     * @return an instantiated MapPlayerObject
     */
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

    /**
     * Get all modes that this object is allowed to exist in
     * 
     * @param objectElement - XML object defining a GameObject
     * @return - the set of allowable modes
     */
    private Set<String> getObjectsModes (Element objectElement) {
        String allModesAsList = XmlUtilities.getChildContent(objectElement, MODES);
        List<String> modeList = Arrays.asList(allModesAsList.split(CSV_REGEX));
        return new TreeSet<String>(modeList);
    }

    /**
     * Creates a map of ImageLoops to be used by animated MapObjects
     * 
     * @param map - the images of this object
     * @return - map of ImageLoops
     */
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

    /**
     * Gets a desired subset of the user's images
     * 
     * @param direction - descriptor of the image
     * @param map - maps images and their names
     * @return a list of images
     */
    private List<Image> parseImageList (String direction, Map<String, Image> map) {
        List<Image> list = new ArrayList<Image>();
        for (String key : map.keySet()) {
            if (key.contains(direction)) {
                list.add(map.get(key));
            }
        }
        return list;
    }

    /**
     * Parse XML to get a map object's location
     * 
     * @param element - XML object defining an object
     * @return - Point location of MapObject in the map
     */
    private Point parseLocation (Element element) {
        List<Element> locationList = (List<Element>) XmlUtilities.getElements(element, LOCATION);
        Element location = locationList.get(0);
        int xPos = XmlUtilities.getChildContentAsInt(location, X);
        int yPos = XmlUtilities.getChildContentAsInt(location, Y);
        Point point = new Point(xPos, yPos);
        return point;
    }

    /**
     * Parses XML to get all of the player's images
     * 
     * @param element - XML element defining the player sprite
     * @return - Map of image names -> images
     */
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

    /**
     * Parse the image representation of an object
     * 
     * @param element - XML element describing the object
     * @return - ImageLoop of object's images
     */
    private ImageLoop parseObjectImages (Element element) {
        List<Image> images = new ArrayList<Image>();
        List<Element> imageList = (List<Element>) XmlUtilities.getElements(element, IMAGE);
        for (Element e : imageList) {
            Image img = XmlUtilities.getContentAsImage(e);
            images.add(img);
        }
        return new ImageLoop(images);
    }

    /**
     * Parse an XML element that describes a Battle specific GameObject
     * 
     * @param battleObjectElement - XML element defining a BattleObject
     * @return instantiation of the battle object
     */
    private BattleObject parseBattleObject (Element battleObjectElement) {
        if (battleObjectElement.hasChildNodes()) {
            String className = XmlUtilities.getChildContent(battleObjectElement, CLASS);
            String event = XmlUtilities.getChildContent(battleObjectElement, CONDITION);
            Image image = XmlUtilities.getChildContentAsImage(battleObjectElement, IMAGE);
            Map<String, Number> stats = parseBattleStats(battleObjectElement);
            String name = XmlUtilities.getChildContent(battleObjectElement, NAME);
            Set<String> modes = getObjectsModes(battleObjectElement);
            BattleObject battleObject =
                    (BattleObject) Reflection.createInstance(className, modes, event, stats, name,
                                                             image);
            battleObject.setImageLoop(parseObjectImages(battleObjectElement));
            return battleObject;
        }
        return null;
    }

    /**
     * Parses the stats of a battle object
     * 
     * @param battleObjectElement - XML element defining a BattleObject
     * @return map of the object's battle stats
     */
    private Map<String, Number> parseBattleStats (Element battleObjectElement) {
        Map<String, Number> stats = new HashMap<String, Number>();
        Element battleStats = XmlUtilities.getElement(battleObjectElement, "stats");
        if (battleStats.hasChildNodes()) {
            NodeList statsList = battleStats.getChildNodes();
            for (int i = 0; i < statsList.getLength(); i++) {
                if (!"#text".equals(statsList.item(i).getNodeName())) {
                    Number statValue =
                            new Double(Double.parseDouble(statsList.item(i).getTextContent()));
                    stats.put(statsList.item(i).getNodeName(), statValue);
                }
            }
        }
        return stats;
    }

    /**
     * Parse an XML element that describes a Map specific GameObject
     * 
     * @param battleObjectElement - XML element defining a MapObject
     * @return instantiation of the map object
     */
    private MapObject parseMapObject (Element mapObjectElement) {
        if (mapObjectElement.hasChildNodes()) {
            String className = XmlUtilities.getChildContent(mapObjectElement, CLASS);
            String event = XmlUtilities.getChildContent(mapObjectElement, CONDITION);
            Element location = XmlUtilities.getElement(mapObjectElement, LOCATION);
            Set<String> modes = getObjectsModes(mapObjectElement);
            int xPos = XmlUtilities.getChildContentAsInt(location, X);
            int yPos = XmlUtilities.getChildContentAsInt(location, Y);
            Point point = new Point(xPos, yPos);
            Image image = XmlUtilities.getChildContentAsImage(mapObjectElement, IMAGE);
            MapObject mapObject =
                    (MapObject) Reflection.createInstance(className, modes, event, point, image);
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
    @SuppressWarnings("unchecked")
    public Map<String, Class<? extends GameMode>> getUserDefinedModes () {
        Map<String, Class<? extends GameMode>> userDefinedModes =
                new HashMap<String, Class<? extends GameMode>>();
        Element modeDefinitionsElement = XmlUtilities.getElement(myDocumentElement, MODE_DEFS);
        List<Element> allModes =
                (List<Element>) XmlUtilities.getElements(modeDefinitionsElement, MODE);
        for (Element mode : allModes) {
            try {
                String name = XmlUtilities.getChildContent(mode, NAME);
                String classString = XmlUtilities.getChildContent(mode, CLASS);
                Class<? extends GameMode> modeClass =
                        (Class<? extends GameMode>) Class.forName(classString);
                userDefinedModes.put(name, modeClass);
            }
            catch (ClassNotFoundException e) {
                System.out.println("An exception. Checkstyle said I just HAD to tell you about it");
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
                String[] conditions = XmlUtilities.getContent(conditionElement).split(CSV_REGEX);
                conditionMap.get(name).add(Arrays.asList(conditions));
            }
        }
        return conditionMap;
    }
}
