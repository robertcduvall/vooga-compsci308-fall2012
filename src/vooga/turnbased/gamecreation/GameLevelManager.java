package vooga.turnbased.gamecreation;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.sprites.Sprite;


/**
 * create game objects and game modes for this level
 * 
 * @author rex
 * 
 */
public class GameLevelManager {

    private Map<String, MapMode> myLoadedMapModes;
    private Map<String, List<Sprite>> myLoadedSprites;
    private GameManager myGameManager;
    private String myCurrentMapModeKey;

    /**
     * Constructor
     * @param gameManager The GameManager it belongs to
     * @param entrance The URI of the xml file that describes the entrance level
     */
    public GameLevelManager (GameManager gameManager, String entrance) {
        myGameManager = gameManager;
        myLoadedMapModes = new HashMap<String, MapMode>();
        myLoadedSprites = new HashMap<String, List<Sprite>>();
        myCurrentMapModeKey = entrance;
        myLoadedMapModes.put(myCurrentMapModeKey, createLevel(myCurrentMapModeKey));
    }

    /**
     * get the current MapMode
     * @return current MapMode
     */
    public MapMode getCurrentMapMode () {
        return myLoadedMapModes.get(myCurrentMapModeKey);
    }
    
    /**
     * get the current MapMode
     * @return current MapMode
     */
    public List<Sprite> getCurrentSprites () {
        return myLoadedSprites.get(myCurrentMapModeKey);
    }

    /**
     * create a new MapMode using the LevelXmlParser
     * also process relevant Sprites
     * @param URI
     * @return
     */
    public MapMode createLevel (String URI) {
        MapMode mapMode = new MapMode(myGameManager, MapMode.class);

        File xmlFile = new File(URI);
        LevelXmlParser test = new LevelXmlParser(xmlFile, mapMode);
        mapMode.setMapSize(test.parseDimension(GameWindow.importString("MapDimension")));
        Dimension cameraDimension = test.parseDimension(GameWindow.importString("CameraDimension"));
        mapMode.setNumDisplayCols(cameraDimension.width);
        mapMode.setNumDisplayRows(cameraDimension.height);
        List<Sprite> sprites = test.parseSprites();
        myLoadedSprites.put(URI, sprites);

        return mapMode;
    }
}
