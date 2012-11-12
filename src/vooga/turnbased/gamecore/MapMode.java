/**
 * Map Mode on which players move around and interact with other game objects
 * 
 * @author Rex, Volodymyr
 */
package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.turnbased.gameobject.map.MapPlayer;
import vooga.turnbased.gameobject.map.MapSprite;
import vooga.turnbased.gui.GameWindow;


/**
 * for now, the mastermind behind map mode...does just about everything
 * 
 * @author Tony, Rex
 **/
public class MapMode extends GameMode {
    private Map<Point, List<MapSprite>> mySprites;
    private int myNumDisplayRows;
    private int myNumDisplayCols;
    private Point myScreenOrigin;

    public MapMode (GameManager gm) {
        super(gm);
        mySprites = new HashMap<Point, List<MapSprite>>();
        myNumDisplayRows = 11;
        myNumDisplayCols = 15;
        myScreenOrigin = new Point(0, 0);
        addSpriteToAll();
        Point center = new Point(7, 5);
        addSprite(center, new MapPlayer(center));
    }

    // only for testing purposes
    public void addSpriteToAll () {
        for (int i = 0; i < myNumDisplayRows; i++) {
            for (int j = 0; j < myNumDisplayCols; j++) {
                Point p = new Point(j, i);
                System.out.println(p.x);
                addSprite(p, new MapSprite(p));
            }
        }
    }

    public void addSprite (Point p, MapSprite s) {
        if (mySprites.keySet().contains(p)) {
            mySprites.get(p).add(s);
        }
        else {
            ArrayList<MapSprite> spriteList = new ArrayList<MapSprite>();
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

                List<MapSprite> spriteList = mySprites.get(new Point(i, j));
                int xOffset = (i - myScreenOrigin.x) * tileWidth;
                int yOffset = (j - myScreenOrigin.y) * tileHeight;
                Image background = GameWindow.importImage("TileBackground");
                g.drawImage(background, xOffset, yOffset, tileWidth, tileHeight, null);
                if (spriteList != null) {
                    for (MapSprite s : spriteList) {
                        g.drawImage(s.getImage(), xOffset, yOffset, tileWidth, tileHeight, null);
                    }
                }
            }
        }
    }
    
    public void moveSprite(MapSprite s, Point dest) {
        for (Point p : mySprites.keySet()) {
            if (mySprites.get(p).contains(s)) {
                if (mySprites.get(dest) != null) {
                    mySprites.get(dest).add(s);
                }
                else {
                    addSprite(dest, s);
                }
                mySprites.remove(s);
            }
        }
    }
}
