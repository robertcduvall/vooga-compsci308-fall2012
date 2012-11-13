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
    private final int ID = 0;
    private Map<Point, List<MapObject>> mySprites;
    private int myNumDisplayRows;
    private int myNumDisplayCols;
    private MapObject myPlayer;
    private Point myDimension;
    //private Point myScreenOrigin;

    public MapMode (GameManager gm) {
        super(gm);
        mySprites = new HashMap<Point, List<MapObject>>();
        myNumDisplayRows = 5;
        myNumDisplayCols = 7;
        myDimension = new Point (20, 30);
        //myScreenOrigin = new Point(0, 0);
        addSpriteToAll();
        Point center = new Point(7, 5);
        myPlayer = new MapPlayerObject(ID, center);
        addSprite(center, myPlayer);
    }

    // only for testing purposes
    public void addSpriteToAll () {
        for (int i = 0; i < myDimension.x; i++) {
            for (int j = 0; j < myDimension.y; j++) {
                Point p = new Point(i, j);
                addSprite(p, new MapObject(ID, p));
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
        int tileWidth = canvasWidth / (myNumDisplayCols*2) + 1;
        int tileHeight = canvasHeight / (myNumDisplayRows*2) + 1;
        Point playerCoord = myPlayer.getCoord();
        for (int i = playerCoord.x - myNumDisplayCols; i < playerCoord.x + myNumDisplayCols; i++) {
            for (int j = playerCoord.y - myNumDisplayRows; j < playerCoord.y + myNumDisplayRows; j++) {

                List<MapObject> spriteList = mySprites.get(new Point(i, j));
                int xOffset = (i - (playerCoord.x - myNumDisplayCols)) * tileWidth;
                int yOffset = (j - (playerCoord.y - myNumDisplayRows)) * tileHeight;
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
        if (dest.x >= 0 && dest.x < myDimension.x && dest.y >= 0 && dest.y < myDimension.y) {
            Point oldCoord = s.getCoord();
            
            if (mySprites.get(oldCoord).contains(s)) {
                mySprites.get(oldCoord).remove(s);
                addSprite(dest, s);
                s.moveTo(dest);
            }
        }
    }

    @Override
    public void handleKeyPressed (KeyEvent e) {
        //internal testing for now...use Input team's stuff later
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                moveSprite(myPlayer, myPlayer.getCoord(LEFT));
                break;
            case KeyEvent.VK_UP:
                moveSprite(myPlayer, myPlayer.getCoord(UP));
                break;
            case KeyEvent.VK_RIGHT:
                moveSprite(myPlayer, myPlayer.getCoord(RIGHT));
                break;
            case KeyEvent.VK_DOWN:
                moveSprite(myPlayer, myPlayer.getCoord(DOWN));
                break;
        }
    }

    @Override
    public void handleKeyReleased (KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}
