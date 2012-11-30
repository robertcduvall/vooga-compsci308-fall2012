package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.MapMode;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.sprites.Sprite;


/**
 * create game objects and game modes for this level
 * 
 * @author rex
 * 
 */
public class GameLevelManager {
//
//    private Map<String, MapMode> myLoadedMapModes;
//    private Map<String, List<Sprite>> myLoadedSprites;
//    private GameManager myGameManager;
//    private String myCurrentMapModeKey;
//    private MapMode myPreviousMap;
//
//    /**
//     * Constructor
//     * 
//     * @param gameManager The GameManager it belongs to
//     * @param entrance The URI of the xml file that describes the entrance level
//     */
//    public GameLevelManager (GameManager gameManager) {
//        myGameManager = gameManager;
//        myLoadedMapModes = new HashMap<String, MapMode>();
//        myLoadedSprites = new HashMap<String, List<Sprite>>();
//        myPreviousMap = null;
//    }
//
//    /**
//     * get the current MapMode
//     * 
//     * @return current MapMode
//     */
//    public MapMode getCurrentMapMode () {
//        return myLoadedMapModes.get(myCurrentMapModeKey);
//    }
//
//    /**
//     * get the current MapMode
//     * 
//     * @return current MapMode
//     */
//    public List<Sprite> getCurrentSprites () {
//        return myLoadedSprites.get(myCurrentMapModeKey);
//    }
//
//    /**
//     * create a new MapMode using the LevelXmlParser
//     * also process relevant Sprites
//     * 
//     * @param URI
//     * @return
//     */
//    public MapMode createLevel (String URI, MapObject enteringObject) {
//        MapMode mapMode = new MapMode(myGameManager, MapMode.class);
//
//        LevelXmlParser test = new LevelXmlParser(new File(URI), mapMode);
//        mapMode.setMapSize(test.parseDimension(GameWindow.importString("MapDimension")));
//        Dimension cameraDimension = test.parseDimension(GameWindow.importString("CameraDimension"));
//        mapMode.setNumDisplayCols(cameraDimension.width);
//        mapMode.setNumDisplayRows(cameraDimension.height);
//        List<Sprite> sprites = test.parseSprites();
//        if (enteringObject == null) { //occurs at the start of the game
//            sprites.add(test.parsePlayerSprite());
//        }
//        else { //occurs when someone steps on a portal
//            //sprites.add(myGameManager.findSpriteWithID(enteringObject.getID()));
//            sprites.add(test.parsePlayerSprite());
//            /*if (enteringObject == myPreviousMap.getPlayer()) {
//                mapMode.setPlayer((MapPlayerObject)enteringObject);
//                myPreviousMap.setPlayer(null);
//            }*/
//            removeEnteringObject(enteringObject); //from previous map
//        }
//        myLoadedSprites.put(URI, sprites);
//
//        return mapMode;
//    }
//
//    /**
//     * the enteringObject enters the map 
//     * @param levelFileName file that describes this level
//     * @param enteringObject MapObject that enters
//     */
//    public void enterMap (String levelFileName, MapObject enteringObject) {
//        myPreviousMap = myLoadedMapModes.get(myCurrentMapModeKey);
//        myCurrentMapModeKey = levelFileName;
//        if (myCurrentMapModeKey == null) { return; }
//        myLoadedMapModes.put(myCurrentMapModeKey, createLevel(myCurrentMapModeKey, enteringObject));
//    }
//    
//    private void removeEnteringObject (MapObject mapObject) {
//        if (myPreviousMap == null) { return; }
//        myPreviousMap.removeMapObject(mapObject);
//        myGameManager.deleteSprite(mapObject.getID());
//    }
}
