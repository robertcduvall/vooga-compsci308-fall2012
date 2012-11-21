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
 * 
 * (could we rename it to something like XmlInfoParser?)
 */
public class LevelCreator {

    private Document myXmlDocument;
    private Element myDocumentElement;
    private MapMode myMapMode;

    /**
     * 
     * @param file XML file used to create the level, the constructor
     *        parameters may change in the future.
     */
    public LevelCreator (File file, MapMode mapMode) {
        myXmlDocument = XmlUtilities.makeDocument(file);
        myDocumentElement = myXmlDocument.getDocumentElement();
        myMapMode = mapMode;
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
    
    public Sprite parsePlayerSprite () {
        Sprite s = new Sprite();
        MapPlayerObject mapPlayer= (MapPlayerObject) parseMapPlayer(s);
        Map<String, ImageLoop> imageLoops = parsePlayerImageLoops(mapPlayer.getImageMap());
        mapPlayer.setImageLoops(imageLoops);
        myMapMode.setPlayer(mapPlayer);
        s.addGameObject(mapPlayer);
        return s;
    }

    private Map<String, ImageLoop> parsePlayerImageLoops (Map<String, Image> map) {
        Map<String, ImageLoop> imageLoops = new HashMap<String, ImageLoop>();
        List<Image> leftList = parseImageList("left", map);
        List<Image> rightList = parseImageList("right", map);
        List<Image> upList = parseImageList("up", map);
        List<Image> downList = parseImageList("down", map);
        imageLoops.put("left", new ImageLoop(leftList));
        imageLoops.put("right", new ImageLoop(rightList));
        imageLoops.put("up", new ImageLoop(upList));
        imageLoops.put("down", new ImageLoop(downList));
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

    /**
     * 
     * @return Player-controlled map object
     */
    public MapObject parseMapPlayer (Sprite s) {
        Element mapPlayer = isolateMapPlayer();
        String className = XmlUtilities.getChildContent(mapPlayer, "class");
        String event = XmlUtilities.getChildContent(mapPlayer, "event");
        Point point = parseLocation(mapPlayer);
        Map<String, Image> imageMap = parsePlayerImages(mapPlayer);

        return (MapObject) Reflection.createInstance(className, s.getID(), event,
                point, imageMap, myMapMode);
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

    private Map<String, Image> parsePlayerImages (Element element) {
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

    public Map<Integer, Sprite> parseStaticSprites () {
        Map<Integer,Sprite> spriteMap = new HashMap<Integer,Sprite>();
        Sprite s = new Sprite();
        for (int i = 0; i < myMapMode.getBottomRight().x; i++) {
            for (int j = 0; j < myMapMode.getBottomRight().y; j++) {
                Point point = new Point(i, j);
                s = new Sprite();
                Element staticSprite = XmlUtilities.getElement(myDocumentElement, "staticSprite");
                String className = XmlUtilities.getChildContent(staticSprite, "class");
                String event = XmlUtilities.getChildContent(staticSprite, "event");
                Image image = XmlUtilities.getChildContentAsImage(staticSprite, "image");
                MapTileObject mapTile = (MapTileObject) Reflection.createInstance(className, s.getID(),
                        event, point, image, myMapMode);
                s.addGameObject(mapTile);
                spriteMap.put(s.getID(), s);
            }
        }
        return spriteMap;
    }
    
    // for real one, would need to loop through sprites, mapSprites and battlSprites
    private Sprite parseSprite (Element sprite) {
        Sprite s = new Sprite();        
        MapObject mapObject = parseMapObject(s, sprite);
        if (mapObject == null) {
            
        }
        else {
            s.addGameObject(mapObject);
        }
        BattleObject battleObject = parseBattleObject(s, sprite);
        if (battleObject == null) {
            
        }
        else {
            s.addGameObject(battleObject);
        }
        return s;
    }

    private BattleObject parseBattleObject (Sprite s, Element sprite) {
        Element battleSprite = XmlUtilities.getElement(sprite, "battle");
        if (battleSprite.hasChildNodes()){
            String className = XmlUtilities.getChildContent(battleSprite, "class");
            String event = XmlUtilities.getChildContent(battleSprite, "event");
            int attack = XmlUtilities.getChildContentAsInt(battleSprite, "attack");
            int defense = XmlUtilities.getChildContentAsInt(battleSprite, "defense");
            int health = XmlUtilities.getChildContentAsInt(battleSprite, "health");
            Image image = XmlUtilities.getChildContentAsImage(battleSprite, "image");
            BattleObject battleObject = (BattleObject) Reflection.createInstance(className,
                    s.getID(), event, attack, defense, health, image);
            return battleObject;
        }
        return null;
    }

    private MapObject parseMapObject (Sprite s, Element sprite) {
        Element mapSprite = XmlUtilities.getElement(sprite, "map");
        if (mapSprite.hasChildNodes()) {
            String className = XmlUtilities.getChildContent(mapSprite, "class");
            String event = XmlUtilities.getChildContent(mapSprite, "event");
            Element location = XmlUtilities.getElement(mapSprite, "location");
            Point point = new Point (XmlUtilities.getChildContentAsInt(location, "x"),
                    XmlUtilities.getChildContentAsInt(location, "y"));
            Image image = XmlUtilities.getChildContentAsImage(mapSprite, "image");
            MapObject mapObject= (MapObject) Reflection.createInstance(className, s.getID(),
                    event, point, image, myMapMode);
            return mapObject;
        }
        return null;
    }
    
    /**
     * 
     * @return List of Sprites in the Level
     */
    public List<Sprite> parseSprites () {
        List<Element> sprites = (List<Element>) XmlUtilities.getElements(myDocumentElement,
                "sprite");
        List<Sprite> spriteList = new ArrayList<Sprite>();
        for (int i = 0; i < sprites.size(); i++) {
            Element sprite = sprites.get(i);
            Sprite s = parseSprite(sprite);
            spriteList.add(s);
        }
        return spriteList;
    }
}
