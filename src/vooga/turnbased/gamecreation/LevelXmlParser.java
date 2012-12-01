package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.graphicprocessing.ImageLoop;
import util.reflection.Reflection;
import util.xml.XmlUtilities;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.GameMode;
import vooga.turnbased.gamecore.gamemodes.MapMode;
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
    private final String PLAYER = "player";
    private final String PLAYER_XML_PATH = "src/vooga/turnbased/resources/level/Player.xml";

    private Document myXmlDocument;
    private Document myPlayerXmlDocument;
    private Element myDocumentElement;
    private MapMode myMapMode;
    
    private int myPlayerID;

    /**
     * 
     * @param file XML file used to create the level, the constructor
     *        parameters may change in the future.
     * @param mapMode The map mode of the level that is being parsed
     */
    public LevelXmlParser (File file, GameManager gm) {
        myXmlDocument = XmlUtilities.makeDocument(file);
        myDocumentElement = myXmlDocument.getDocumentElement();
        myMapMode = new MapMode(gm, MapObject.class, new ArrayList<Integer>()); //hardcoded ID :(
        myMapMode.setMapSize(parseDimension(GameWindow.importString("MapDimension"))); //ahhhhhhhh
        myMapMode.setCameraSize(parseDimension(GameWindow.importString("CameraDimension")));//gggggggggrrrrgggggllllllllll....aggggggg *dramatic death*
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
        toReturn.add(parsePlayerSprite()); //TODO
        return toReturn;
    }

    /**
     * 
     * @return The player-controlled sprite.
     */
    public Sprite parsePlayerSprite () {
        Sprite s = new Sprite();
        MapPlayerObject mapPlayer = parseMapPlayer(s);
        if (mapPlayer == null) {
            System.err.println("No player information found!");
            return null;
        }
        Map<String, ImageLoop> imageLoops = parseImageLoops(mapPlayer.getImageMap());
        mapPlayer.setImageLoops(imageLoops);
        myMapMode.setPlayer(mapPlayer);
        s.addGameObject(mapPlayer);
        List<BattleObject> playerBattleObjects = parseBattleObjects (
                s, getListOfPlayerElements().get(0));
        for (BattleObject pbs : playerBattleObjects) {
            s.addGameObject(pbs);
        }
        myPlayerID = s.getID();
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
                MapObject mapTile =
                        (MapObject)Reflection.createInstance(className, s.getID(), event,
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
        for (int i = 0; i < imageList.size(); i++) {
            Element imageData = imageList.get(i);
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
            System.out.println(XmlUtilities.getContent(e));
            Image img = XmlUtilities.getContentAsImage(e);
            images.add(img);
        }
        return new ImageLoop(images);
    }

    private Sprite parseSprite (Element sprite) {
        Sprite s = new Sprite();
        List<MapObject> mapObjects = parseMapObjects(s, sprite);
        for (MapObject mo : mapObjects) {
            if (mo != null) {
                s.addGameObject(mo);
            }
        }
        List<BattleObject> battleObjects = parseBattleObjects(s, sprite);
        for (BattleObject bo : battleObjects) {
            if (bo != null) {
                s.addGameObject(bo);
            }
        }
        return s;
    }
    
    private List<MapObject> parseMapObjects(Sprite s, Element sprite) {
        Collection<Element> mapSprites = XmlUtilities.getElements(sprite, "map");
        List<MapObject> mapObjects = new ArrayList<MapObject>();
        for (Element ms : mapSprites) {
            mapObjects.add(parseMapObject (s, ms));
        }
        return mapObjects;
    }
    
    private List<BattleObject> parseBattleObjects(Sprite s, Element sprite) {
        Collection<Element> battleSprites = XmlUtilities.getElements(sprite, "battle");
        List<BattleObject> battleObjects = new ArrayList<BattleObject>();
        for (Element bs : battleSprites) {
            battleObjects.add(parseBattleObject (s, bs));
        }
        return battleObjects;
    }

    private BattleObject parseBattleObject (Sprite s, Element battleSprite) {
        if (battleSprite.hasChildNodes()) {
            String className = XmlUtilities.getChildContent(battleSprite, CLASS);
            String event = XmlUtilities.getChildContent(battleSprite, EVENT);
            Image image = XmlUtilities.getChildContentAsImage(battleSprite, IMAGE);
            Map<String, Number> stats = parseBattleStats(battleSprite);
            String name = XmlUtilities.getChildContent(battleSprite, "name");
            BattleObject battleObject =
                    (BattleObject) Reflection.createInstance(className, s.getID(), event, stats,
                                                             name, image);
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

    private MapObject parseMapObject (Sprite s, Element mapSprite) {
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
            if (point.equals(new Point(10, 10))) {
                mapObject.addStrategy(new TransportStrategy(
                        myMapMode,
                        "src/vooga/turnbased/resources/level/Level2.xml",
                        new Point(8, 8)));
            }
            if (point.equals(new Point(9, 3))) {
                mapObject.addStrategy(new ConversationStrategy(myMapMode));
                //mapObject.addStrategy(mapStrategy);
            }

            return mapObject;
        }
        return null;
    }

    public int getPlayerID() {
        return myPlayerID;
    }
    
    public GameMode getMapMode() { //TODO: fix...
    	return myMapMode;
    }
}
