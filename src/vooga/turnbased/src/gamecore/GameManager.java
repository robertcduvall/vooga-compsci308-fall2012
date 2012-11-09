package gamecore;

import gameobjects.Sprite;
import gui.GameCanvas;
import java.awt.Point;
import java.util.HashMap;

public class GameManager {

    private GameCanvas myGameCanvas;
    private HashMap<Sprite, Point> mySprites;
    private GameMode myCurrentGameMode;
    private Factory myFactory;
    private Sprite myPlayer;
    private boolean isOver;
    
    public GameManager (GameCanvas gameCanvas) {
        myGameCanvas = gameCanvas;
        isOver = false;
        //mySprites = myFactory.initializeSprites(myGameCanvas.getInitialMapFile());
        myCurrentGameMode = new MapMode();
        //myCurrentGameMode.initializeMap();
    }

    public boolean isOver () {
        return isOver;
    }

    public void update () {
        
    }

    public void paint () {
        
    }
}
