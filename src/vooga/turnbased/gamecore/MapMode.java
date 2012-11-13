/**
 * Map Mode on which players move around and interact with other game objects
 * 
 * @author Rex, Volodymyr
 */
package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gameobject.MapPlayerObject;
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
    private Point myBottomRightCorner;
    private int myCurrentTileWidth;
    private int myCurrentTileHeight;
    private Rectangle myCurrentCamera;

    public MapMode (GameManager gm) {
        super(gm);
        myNumDisplayRows = Integer.parseInt(GameWindow.importString("CameraHeight"));
        myNumDisplayCols = Integer.parseInt(GameWindow.importString("CameraWidth"));
        myBottomRightCorner = new Point (20, 30);
        addHardcodedSprites();
    }

    // only for testing purposes
    public void addHardcodedSprites () {
        mySprites = new HashMap<Point, List<MapObject>>();
        for (int i = 0; i < myBottomRightCorner.x; i++) {
            for (int j = 0; j < myBottomRightCorner.y; j++) {
                Point p = new Point(i, j);
                addSprite(p, new MapObject(ID, p));
            }
        }
        Point center = new Point(7, 5);
        myPlayer = new MapPlayerObject(ID, center);
        addSprite(center, myPlayer);
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
    public void update () {
        myCurrentTileWidth = getGM().getCanvasDimension().width / myNumDisplayCols;
        myCurrentTileHeight = getGM().getCanvasDimension().height / myNumDisplayRows;
        Point playerCoord = myPlayer.getCoord();
        myCurrentCamera = new Rectangle(playerCoord.x - (myNumDisplayCols - 1) / 2,
                                        playerCoord.y - (myNumDisplayRows - 1) / 2,
                                        myNumDisplayCols, myNumDisplayRows);
    }

    @Override
    public void paint (Graphics g) {
        update();
        for (int i = myCurrentCamera.x; i < myCurrentCamera.getMaxX(); i++) {
            for (int j = myCurrentCamera.y; j < myCurrentCamera.getMaxY(); j++) {
                List<MapObject> spritesOnTile = getSpritesOnTile(i, j);
                int xOffset = (i - (myCurrentCamera.x)) * myCurrentTileWidth;
                int yOffset = (j - (myCurrentCamera.y)) * myCurrentTileHeight;
                Image background = GameWindow.importImage("TileBackground");
                g.drawImage(background, xOffset, yOffset, myCurrentTileWidth, myCurrentTileHeight, null);
                if (spritesOnTile != null) {
                    for (MapObject s : spritesOnTile) {
                        g.drawImage(s.getImage(), xOffset, yOffset, myCurrentTileWidth, myCurrentTileHeight, null);
                    }
                }
            }
        }
    }

    private List<MapObject> getSpritesOnTile (int i, int j) {
        return mySprites.get(new Point(i, j));
    }
    
    public void moveSprite(MapObject s, Point dest) {
        if (dest.x >= 0 && dest.x < myBottomRightCorner.x && dest.y >= 0 && dest.y < myBottomRightCorner.y) {
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
