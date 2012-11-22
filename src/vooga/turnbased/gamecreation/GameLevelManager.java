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
    private GameManager myGameManager;
    private String myCurrentMapModeKey;

    public GameLevelManager (GameManager gameManager, String entrance) {
        myGameManager = gameManager;
        myLoadedMapModes = new HashMap<String, MapMode>();
        myCurrentMapModeKey = entrance;
        myLoadedMapModes.put(myCurrentMapModeKey, createLevel(myCurrentMapModeKey));
    }

    public MapMode getCurrentMapMode () {
        return myLoadedMapModes.get(myCurrentMapModeKey);
    }

    private MapMode createLevel (String entrance) {
        MapMode mapMode = new MapMode(myGameManager, MapMode.class);

        File xmlFile = new File(entrance);
        LevelXmlParser test = new LevelXmlParser(xmlFile, mapMode);
        mapMode.setMapSize(test.parseDimension(GameWindow.importString("MapDimension")));
        Dimension cameraDimension = test.parseDimension(GameWindow.importString("CameraDimension"));
        mapMode.setNumDisplayCols(cameraDimension.width);
        mapMode.setNumDisplayRows(cameraDimension.height);
        List<Sprite> sprites = test.parseSprites();
        myGameManager.addSprites(sprites);

        return mapMode;
    }
}