package vooga.turnbased.gamecore.gamemodes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import util.input.core.KeyboardController;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.pathutility.PathFinder;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapPlayerObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.gui.InputAPI;


/**
 * Map Mode on which players move around and interact with other game objects
 * 
 * @author Tony, Rex
 **/
public class MapMode extends GameMode implements InputAPI {
    public static final Point UP = new Point(0, -1);
    public static final Point RIGHT = new Point(1, 0);
    public static final Point DOWN = new Point(0, 1);
    public static final Point LEFT = new Point(-1, 0);
    private int myNumDisplayRows;
    private int myNumDisplayCols;
    private HashMap<Point, List<MapObject>> myMapObjects;
    private MapPlayerObject myPlayer;
    private Dimension myMapSize;

    private Point myOrigin;
    private int myCurrentTileWidth;
    private int myCurrentTileHeight;
    private Rectangle myCurrentCamera;
    private PathFinder myPathFinder;
    private Point myTopLeftCoord;

    /**
     * Constructor of MapMode
     * 
     * @param gm
     *        the GameManager which manages the mode
     */
    public MapMode (GameManager gm, Class modeObjectType, List<Integer> involvedIDs) {
        super(gm, modeObjectType, involvedIDs);
        //myPlayer = (MapPlayerObject) myMapObjects.get(gm.getPlayerSpriteID());
        // initialize();
    }

    @Override
    /**
     * pause the game
     */
    public void pause () {
        myMapObjects.clear();
        setInactive();
        // should unregister keyboard input, otherwise leads to issues
    }

    @Override
    public void resume () {
        // do stuff when back to map mode
        setActive();
        initialize(); // TODO: Shouldn't need to reinitialize map objects
        // configureInputHandling();
    }

    @Override
    public void initialize () {
        myMapObjects = new HashMap<Point, List<MapObject>>();
        List<MapObject> mapObjects = getGameManager().getGameObjectsOfSpecificMode(MapObject.class);
        for (MapObject mapObject : mapObjects) {
            addMapObject(mapObject.getLocation(), mapObject);
            if (mapObject.getID() == getGameManager().getPlayerSpriteID()) {
                myPlayer = (MapPlayerObject) mapObject;
            }
        }
        configureInputHandling();
        // update();
    }

    public void setCameraSize (Dimension d) {
        setNumDisplayCols(d.width);
        setNumDisplayRows(d.height);
    }

    private void setNumDisplayRows (int numDisplayRows) {
        this.myNumDisplayRows = numDisplayRows;
    }

    private void setNumDisplayCols (int numDisplayCols) {
        this.myNumDisplayCols = numDisplayCols;
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

    public void removeMapObject (MapObject mapObject) {
        myMapObjects.get(mapObject.getLocation()).remove(mapObject);
    }

    /**
     * paint the map
     * 
     * @param g
     *        Graphics onto which the map is drawn
     */
    @Override
    public void paint (Graphics g) {
        paintMapBackgroung(g);
        List<MapObject> visibleSprites = getSpritesWithinCamera();
        for (MapObject s : visibleSprites) {
            s.paint(g);
        }
        myPlayer.paint(g);
    }

    private void paintMapBackgroung (Graphics g) {
        Image background = GameWindow.importImage("TileBackground");
        Dimension paneDim = getGameManager().getPaneDimension();
        g.drawImage(background, 0, 0, paneDim.width, paneDim.width, null);
    }

    private List<MapObject> getSpritesWithinCamera () {
        List<MapObject> visibleSprites = new ArrayList<MapObject>();
        for (int i = myCurrentCamera.x; i < myCurrentCamera.getMaxX(); i++) {
            for (int j = myCurrentCamera.y; j < myCurrentCamera.getMaxY(); j++) {
                List<MapObject> spritesOnTile = getSpritesOnTile(i, j);
                if (spritesOnTile != null) {
                    visibleSprites.addAll(spritesOnTile);
                }
            }
        }
        return visibleSprites;
    }

    @Override
    /**
     * update the map
     */
    public void update () {
        updateTileInfo();
        updateCameraPosition();
        if (myPathFinder != null) {
            myPathFinder.updatePath();
        }
        updateMapObjects();
    }

    /**
     * update tile's width and height, which will change when the window is
     * resized
     */
    public void updateTileInfo () {
        myCurrentTileWidth = getGameManager().getPaneDimension().width / myNumDisplayCols;
        myCurrentTileHeight = getGameManager().getPaneDimension().height / myNumDisplayRows;
        myOrigin = initializeOrigin();
    }

    /**
     * move the camera according to player's movement do not move when player
     * reaches the edge There are 2 rows and columns of margin for smooth
     * movement
     */
    private void updateCameraPosition () {
        Point displacement =
                myPlayer.calcScreenDisplacement(myCurrentTileWidth, myCurrentTileHeight);
        myTopLeftCoord = calculateTopLeftCoordinate();
        if (myTopLeftCoord.x * myCurrentTileWidth + myPlayer.getDirection().x < 0) {
            myTopLeftCoord.x = 0; // player near the left boundary
            displacement.x = 0; // screen fixed when player moves to the edge
        }
        else if ((myTopLeftCoord.x + myNumDisplayCols) * myCurrentTileWidth +
                 myPlayer.getDirection().x > myMapSize.width * myCurrentTileWidth) {
            myTopLeftCoord.x = myMapSize.width - myNumDisplayCols;
            displacement.x = 0;
        }
        if (myTopLeftCoord.y * myCurrentTileHeight + myPlayer.getDirection().y < 0) {
            myTopLeftCoord.y = 0; // player near the top boundary
            displacement.y = 0;
        }
        else if ((myTopLeftCoord.y + myNumDisplayRows) * myCurrentTileHeight +
                 myPlayer.getDirection().y > myMapSize.height * myCurrentTileHeight) {
            myTopLeftCoord.y = myMapSize.height - myNumDisplayRows;
            displacement.y = 0;
        }
        myCurrentCamera =
                new Rectangle(myTopLeftCoord.x - 1, myTopLeftCoord.y - 1, myNumDisplayCols + 2,
                              myNumDisplayRows + 2);
        myOrigin = changeOriginForPlayer(displacement);
    }

    /**
     * iterate through the map and update MapObjects at each position
     */
    private void updateMapObjects () {
        for (Point p : myMapObjects.keySet()) {
            List<MapObject> objectsOnTile = getSpritesOnTile(p.x, p.y);
            Iterator<MapObject> it = objectsOnTile.iterator();
            while (it.hasNext()) {
                MapObject nextObject = it.next();
                if (!nextObject.isVisible()) {
                    it.remove(); // entire sprite should be removed???
                }
                else {
                    nextObject.update();
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
        Point result = new Point(myOrigin.x + displacement.x, myOrigin.y + displacement.y);
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
     * @return a list of MapObjects on the tile; empty list if no MapObjects
     *         found
     */
    public List<MapObject> getSpritesOnTile (int i, int j) {
        if (myMapObjects.containsKey(new Point(i, j))) { return myMapObjects.get(new Point(i, j)); }
        return new ArrayList<MapObject>();
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
            Point target =
                    new Point(e.getX() / myCurrentTileWidth + myTopLeftCoord.x,
                              e.getY() / myCurrentTileHeight + myTopLeftCoord.y);
            myPathFinder = new PathFinder(this, myPlayer, target, myMapSize);
        }
    }

    public Rectangle getCamera () {
        return myCurrentCamera;
    }

    public Dimension getTileDimensions () {
        return new Dimension(myCurrentTileWidth, myCurrentTileHeight);
    }

    public Point getOrigin () {
        return myOrigin;
    }

    @Override
    public void configureInputHandling () {
        try {
            GamePane.keyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED,
                                                   myPlayer, "moveLeft");
            GamePane.keyboardController.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED,
                                                   myPlayer, "moveUp");
            GamePane.keyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED,
                                                   myPlayer, "moveRight");
            GamePane.keyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED,
                                                   myPlayer, "moveDown");
            GamePane.keyboardController.setControl(KeyEvent.VK_R, KeyboardController.PRESSED,
                                                   myPlayer, "toggleRunning");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Dimension getMapSize () {
        return myMapSize;
    }

    public void setMapSize (Dimension mapSize) {
        myMapSize = mapSize;
    }

    public void setPlayer (MapPlayerObject p) {
        myPlayer = p;
    }

    public MapPlayerObject getPlayer () {
        return myPlayer;
    }

    public boolean isWithinBounds (Point dest) {
        return myMapSize.height > dest.y && myMapSize.width > dest.x && dest.x >= 0 && dest.y >= 0;
    }

    @Override
    public void processMouseInput (int mousePressed, Point myMousePosition, int myMouseButton) {
        if (mousePressed == GamePane.MOUSE_CLICKED && myMouseButton == MouseEvent.BUTTON3) {
            if (myPathFinder != null) {
                myPathFinder.stop();
            }
            Point target =
                    new Point((int) myMousePosition.getX() / myCurrentTileWidth + myTopLeftCoord.x,
                              (int) myMousePosition.getY() / myCurrentTileHeight + myTopLeftCoord.y);
            myPathFinder = new PathFinder(this, myPlayer, target, myMapSize);
        }
    }
}
