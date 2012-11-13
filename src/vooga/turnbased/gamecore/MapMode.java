/**
 * Map Mode on which players move around and interact with other game objects
 * 
 * @author Rex, Volodymyr
 */
package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.turnbased.gameobject.MapPlayerObject;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gui.GameWindow;


/**
 * for now, the mastermind behind map mode...does just about everything
 * 
 * @author Tony, Rex
 **/
public class MapMode extends GameMode {
    private final Point UP = new Point(0, -1);
    private final Point RIGHT = new Point(1, 0);
    private final Point DOWN = new Point(0, 1);
    private final Point LEFT = new Point(-1, 0);
    private Map<Point, List<MapObject>> mySprites;
    private int myNumDisplayRows;
    private int myNumDisplayCols;
    private MapObject myPlayer;
    private Point myScreenOrigin;

    public MapMode (GameManager gm) {
        super(gm);
        mySprites = new HashMap<Point, List<MapObject>>();
        myNumDisplayRows = 11;
        myNumDisplayCols = 15;
        myScreenOrigin = new Point(0, 0);
        addSpriteToAll();
        Point center = new Point(7, 5);
        myPlayer = new MapPlayerObject(center);
        addSprite(center, myPlayer);
    }

    // only for testing purposes
    public void addSpriteToAll () {
        for (int i = 0; i < myNumDisplayRows*2; i++) {
            for (int j = 0; j < myNumDisplayCols*2; j++) {
                Point p = new Point(j, i);
                addSprite(p, new MapObject(p));
            }
        }
    }

    public void addSprite (Point p, MapObject s) {
        if (mySprites.keySet().contains(p)) {
            mySprites.get(p).add(s);
        }
        else {
            ArrayList<MapObject> spriteList = new ArrayList<MapObject>();
            spriteList.add(s);
            mySprites.put(p, spriteList);
        }
    }

    @Override
    public void paint (Graphics g, int canvasWidth, int canvasHeight) {
        int tileWidth = canvasWidth / myNumDisplayCols;
        int tileHeight = canvasHeight / myNumDisplayRows;
        for (int i = myScreenOrigin.x; i < myScreenOrigin.x + myNumDisplayCols; i++) {
            for (int j = myScreenOrigin.y; j < myScreenOrigin.x + myNumDisplayRows; j++) {

                List<MapObject> spriteList = mySprites.get(new Point(i, j));
                int xOffset = (i - myScreenOrigin.x) * tileWidth;
                int yOffset = (j - myScreenOrigin.y) * tileHeight;
                Image background = GameWindow.importImage("TileBackground");
                g.drawImage(background, xOffset, yOffset, tileWidth, tileHeight, null);
                if (spriteList != null) {
                    for (MapObject s : spriteList) {
                        g.drawImage(s.getImage(), xOffset, yOffset, tileWidth, tileHeight, null);
                    }
                }
            }
        }
    }
    
    public void moveSprite(MapObject s, Point dest) {
        for (Point p : mySprites.keySet()) {
            if (mySprites.get(p).contains(s)) {
                addSprite(dest, s);
                s.moveTo(dest);
                mySprites.get(p).remove(s);
            }
        }
    }

    @Override
    public void handleKeyEvent (KeyEvent e) {
        //internal testing for now...use Input team's stuff later
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                moveSprite(myPlayer, myPlayer.getCoord(LEFT));
                changeScreenOrigin(LEFT);
                break;
            case KeyEvent.VK_UP:
                moveSprite(myPlayer, myPlayer.getCoord(UP));
                changeScreenOrigin(UP);
                break;
            case KeyEvent.VK_RIGHT:
                moveSprite(myPlayer, myPlayer.getCoord(RIGHT));
                changeScreenOrigin(RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                moveSprite(myPlayer, myPlayer.getCoord(DOWN));
                changeScreenOrigin(DOWN);
                break;
        }
    }
    
    public void changeScreenOrigin (Point dir) {
        myScreenOrigin.x += dir.x;
        myScreenOrigin.y += dir.y;
    }
}
