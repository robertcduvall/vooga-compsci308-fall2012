package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gameobject.MapPlayerObject;
import vooga.turnbased.gameobject.MapTileObject;
import vooga.turnbased.gameobject.MovingMapObject;
import vooga.turnbased.gui.GameWindow;


/**
 * Map Mode on which players move around and interact with other game objects
 * for now, the mastermind behind map mode...does just about everything
 * 
 * @author Tony, Rex
 **/
public class MapMode extends GameMode {
    public static final Point UP = new Point(0, -1);
    public static final Point RIGHT = new Point(1, 0);
    public static final Point DOWN = new Point(0, 1);
    public static final Point LEFT = new Point(-1, 0);
    private final int ID = 0;
    private boolean myIsFixedPlayer;
    private int myNumDisplayRows;
    private int myNumDisplayCols;
    private HashMap<Point, List<MapObject>> myMapObjects;
    private MapPlayerObject myPlayer;
    private Point myBottomRightCorner;
    private Point myOrigin;
    private int myCurrentTileWidth;
    private int myCurrentTileHeight;
    private Rectangle myCurrentCamera;
    private PathFinder myPathFinder;

    /**
     * Constructor of MapMode
     * 
     * @param gm
     *        the GameManager which manages the mode
     */
    public MapMode (GameManager gm, Class modeObjectType) {
        super(gm, modeObjectType);
    }

    @Override
    public void pause () {
        myMapObjects.clear();
    }

    @Override
    public void resume () {
        myNumDisplayRows = Integer.parseInt(GameWindow
                .importString("CameraHeight"));
        myNumDisplayCols = Integer.parseInt(GameWindow
                .importString("CameraWidth"));
        myBottomRightCorner = new Point(20, 30);
        addHardcodedSprites();
    }

    // only for testing purposes
    public void addHardcodedSprites () {
        myMapObjects = new HashMap<Point, List<MapObject>>();
        for (int i = 0; i < myBottomRightCorner.x; i++) {
            for (int j = 0; j < myBottomRightCorner.y; j++) {
                Point p = new Point(i, j);
                addMapObject(p, new MapTileObject(ID,
                        GameManager.GameEvent.NO_ACTION, p, GameWindow
                                .importImage("GrassImage")));
            }
        }
        Point center = new Point(7, 5);
        myPlayer = new MapPlayerObject(ID, GameManager.GameEvent.MAP_COLLISION,
                center, GameWindow.importImage("PlayerImage"));
        addMapObject(center, myPlayer);

        center = new Point(1, 1);
        MovingMapObject test1 = new MovingMapObject(ID,
                GameManager.GameEvent.MAP_COLLISION, center, GameWindow
                        .importImage("something"));
        addMapObject(center, test1);
    }

    /**
     * add MapObject to the MapMode
     * 
     * @param p
     *        coordinate the MapObject shoulld be placed
     * @param s
     *        MapObject to be added
     */
    public void addMapObject (Point p, MapObject s) {
        if (myMapObjects.keySet().contains(p)) {
            myMapObjects.get(p).add(s);
        }
        else {
            ArrayList<MapObject> spriteList = new ArrayList<MapObject>();
            spriteList.add(s);
            myMapObjects.put(p, spriteList);
        }
    }

    /**
     * paint the map
     * 
     * @param g
     *        Graphics onto which the map is drawn
     */
    @Override
    public void paint (Graphics g) {
        int playerX = 0;
        int playerY = 0;
        // foreach sprite: s.paint(g);
        for (int i = myCurrentCamera.x; i < myCurrentCamera.getMaxX(); i++) {
            for (int j = myCurrentCamera.y; j < myCurrentCamera.getMaxY(); j++) {
                List<MapObject> spritesOnTile = getSpritesOnTile(i, j);
                int xOffset = (i - (myCurrentCamera.x)) * myCurrentTileWidth
                        + myOrigin.x;
                int yOffset = (j - (myCurrentCamera.y)) * myCurrentTileHeight
                        + myOrigin.y;
                Image background = GameWindow.importImage("TileBackground");
                g.drawImage(background, xOffset, yOffset, myCurrentTileWidth,
                        myCurrentTileHeight, null);
                if (spritesOnTile != null) {
                    for (MapObject s : spritesOnTile) {
                        s.paint(g, xOffset, yOffset, myCurrentTileWidth,
                                myCurrentTileHeight);
                        if (s.equals(myPlayer)) {
                            playerX = xOffset;
                            playerY = yOffset;
                        }
                    }
                }
            }
        }
        myPlayer.paint(g, playerX, playerY, myCurrentTileWidth,
                myCurrentTileHeight);
    }

    @Override
    /**
     * update the map
     */
    public void update () {
        updateTileInfo();
        updateCameraPosition();
        updateMapObjects();
        processGameEvents();
    }

    /**
     * update tile's width and height, which will change when the window is
     * resized
     */
    public void updateTileInfo () {
        myCurrentTileWidth = getGM().getPaneDimension().width
                / myNumDisplayCols;
        myCurrentTileHeight = getGM().getPaneDimension().height
                / myNumDisplayRows;
        myOrigin = initializeOrigin();
    }

    /**
     * move the camera according to player's movement do not move when player
     * reaches the edge There are 2 rows and columns of margin for smooth
     * movement
     */
    private void updateCameraPosition () {
        Point displacement = myPlayer.calcScreenDisplacement(
                myCurrentTileWidth, myCurrentTileHeight);
        Point topLeftCoord = calculateTopLeftCoordinate();
        if (topLeftCoord.x * myCurrentTileWidth + myPlayer.getDirection().x < 0) {
            topLeftCoord.x = 0; // player near the left boundary
            displacement.x = 0; // screen fixed when player moves to the edge
        }
        else if ((topLeftCoord.x + myNumDisplayCols) * myCurrentTileWidth
                + myPlayer.getDirection().x > myBottomRightCorner.x
                * myCurrentTileWidth) {
            topLeftCoord.x = myBottomRightCorner.x - myNumDisplayCols;
            displacement.x = 0;
        }
        if (topLeftCoord.y * myCurrentTileHeight + myPlayer.getDirection().y < 0) {
            topLeftCoord.y = 0; // player near the top boundary
            displacement.y = 0;
        }
        else if ((topLeftCoord.y + myNumDisplayRows) * myCurrentTileHeight
                + myPlayer.getDirection().y > myBottomRightCorner.y
                * myCurrentTileHeight) {
            topLeftCoord.y = myBottomRightCorner.y - myNumDisplayRows;
            displacement.y = 0;
        }
        myCurrentCamera = new Rectangle(topLeftCoord.x - 1, topLeftCoord.y - 1,
                myNumDisplayCols + 2, myNumDisplayRows + 2);
        myOrigin = changeOriginForPlayer(displacement);
    }

    /**
     * iterate through the map and update MapObjects at each position
     */
    private void updateMapObjects () {
        for (Point p : myMapObjects.keySet()) {
            for (MapObject s : getSpritesOnTile(p.x, p.y)) {
                s.update(getGM().getDelayTime());
            }
        }
    }

    public void processGameEvents () { // this can be optimized A LOT, only
                                       // check mapobjects that did something
                                       // last turn
        for (Point p : myMapObjects.keySet()) {
            HashMap<GameManager.GameEvent, ArrayList<Integer>> myEventHash = new HashMap<GameManager.GameEvent, ArrayList<Integer>>();
            for (MapObject s : getSpritesOnTile(p.x, p.y)) {
                if (!myEventHash.containsKey(s.getEvent())) {
                    myEventHash.put(s.getEvent(), new ArrayList<Integer>());
                }
                myEventHash.get(s.getEvent()).add(s.getID());
            }
            for (GameManager.GameEvent ge : myEventHash.keySet()) {
                if (myEventHash.get(ge).size() >= 2) {
                    getGM().handleEvent(ge, myEventHash.get(ge));
                }
            }
        }
    }

    /**
     * change the top left corner of the screen when the player moves
     * 
     * @param displacement
     *        the displacement of the screen
     * @return new origin point
     */
    private Point changeOriginForPlayer (Point displacement) {
        Point result = new Point(myOrigin.x + displacement.x, myOrigin.y
                + displacement.y);
        if (result.x == 0) { // screen movement done!
            result = initializeOrigin();
        }
        return result;
    }

    /**
     * calculate top left corner coordinate of the grid
     * 
     * @return top-left coordinate
     */
    private Point calculateTopLeftCoordinate () {
        myIsFixedPlayer = true;
        int x = myPlayer.getPreviousLocation().x;
        int y = myPlayer.getPreviousLocation().y;
        x -= (myNumDisplayCols - 1) / 2;
        y -= (myNumDisplayRows - 1) / 2;
        return new Point(x, y);
    }

    /**
     * get a list of MapObjects on the tile
     * 
     * @param i
     *        x-coordinate on the grid
     * @param j
     *        y-coordinate on the grid
     * @return a list of MapObjects on the tile
     */
    private List<MapObject> getSpritesOnTile (int i, int j) {
        return myMapObjects.get(new Point(i, j));
    }

    /**
     * move a MovingMapObject to a nearby tile in a specific direction
     * 
     * @param s
     *        MovingMapObject
     * @param dir
     *        direction it moves towards
     */
    public void moveSprite (MovingMapObject s, Point dir) {
        Point dest = myPlayer.getLocation(dir);
        if (dest.x >= 0 && dest.x < myBottomRightCorner.x && dest.y >= 0
                && dest.y < myBottomRightCorner.y) {
            Point oldCoord = s.getLocation();

            if (myMapObjects.get(oldCoord).contains(s)) {
                myMapObjects.get(oldCoord).remove(s);
                addMapObject(dest, s);
                s.setLocation(dest);
                s.setDirection(dir); // start moving in update() when direction
                // is set
            }
        }
    }

    @Override
    /**
     * handle key pressed events specific to MapMode
     */
    public void handleKeyPressed (KeyEvent e) {
        // foreach sprite: s.handleKeyPressed(e); s.update();
        int keyCode = e.getKeyCode();
        if (myPlayer.isMoving()) { return; }
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                moveSprite(myPlayer, LEFT);
                break;
            case KeyEvent.VK_UP:
                moveSprite(myPlayer, UP);
                break;
            case KeyEvent.VK_RIGHT:
                moveSprite(myPlayer, RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                moveSprite(myPlayer, DOWN);
                break;
        }
    }

    @Override
    /**
     * handle key released event specific to MapMode
     */
    public void handleKeyReleased (KeyEvent e) {

    }

    /**
     * initialize the origin point
     * 
     * @return origin point
     */
    private Point initializeOrigin () {
        return new Point(-myCurrentTileWidth, -myCurrentTileHeight);
    }

    public void handleMouseClicked (MouseEvent e) {
        // right click
        if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) {
            if (myPathFinder != null) {
                myPathFinder.stop();
            }
            Point target = new Point((e.getX() + myOrigin.x)
                    / myCurrentTileWidth, (e.getY() + myOrigin.y)
                    / myCurrentTileHeight);
            myPathFinder = new PathFinder(this, myPlayer, target,
                    myBottomRightCorner);
        }
    }
}
