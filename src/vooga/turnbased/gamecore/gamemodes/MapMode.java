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
import vooga.turnbased.gamecore.graphutility.MapModePathFinder;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapPlayerObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.gui.InputAPI;


/**
 * Map Mode on which players move around and interact with other game objects
 * 
 * @author Tony, Rex, volodymyr
 **/
public class MapMode extends GameMode implements InputAPI {

    /**
     * A point 1 unit up in the Y direction
     */
    public static final Point UP = new Point(0, -1);
    /**
     * A point 1 unit right in the X direction
     */
    public static final Point RIGHT = new Point(1, 0);
    /**
     * A point 1 unit down in the Y direction
     */
    public static final Point DOWN = new Point(0, 1);
    /**
     * A point 1 unit left in the X direction
     */
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
    private MapModePathFinder myPathFinder;
    private Point myTopLeftCoord;

    /**
     * Constructor of MapMode
     * 
     * @param gm
     *        GameManager that manages the mode.
     * @param modeName
     *        String name of mode.
     * @param involvedIDs
     *        List of the IDS of sprites involved in MapMode.
     */
    public MapMode (GameManager gm, String modeName, List<Integer> involvedIDs) {
        super(gm, modeName, involvedIDs);
        initialize();
    }

    @Override
    /**
     * pause the game
     */
    public void pause () {
        getGameObjects().clear();
        setInactive();
        // should unregister keyboard input, otherwise leads to issues
    }

    @Override
    public void resume () {
        setActive();
        acquireGameObjects();
        initialize();
    }

    @Override
    public void initialize () {
        setActive();
        int playerID = getGameManager().getPlayerSpriteID();
        setCameraSize(getGameManager().getCameraSize());
        setMapSize(getGameManager().getMapSize());

        myMapObjects = new HashMap<Point, List<MapObject>>();
        @SuppressWarnings("unchecked")
        // the getGameObjects method has already taken care of correct casting
        List<MapObject> mapObjects = (List<MapObject>) getGameObjects();
        for (MapObject mapObject : mapObjects) {
            mapObject.setMapMode(this);
            addMapObject(mapObject.getLocation(), mapObject);
            if (mapObject.getID() == playerID) {
                setPlayer((MapPlayerObject) mapObject);
            }
        }
        myPathFinder = new MapModePathFinder(this, myPlayer);
        configureInputHandling();
        update();
    }

    private void setCameraSize (Dimension d) {
        setNumDisplayCols(d.width);
        setNumDisplayRows(d.height);
    }

    private void setNumDisplayRows (int numDisplayRows) {
        myNumDisplayRows = numDisplayRows;
    }

    private void setNumDisplayCols (int numDisplayCols) {
        myNumDisplayCols = numDisplayCols;
    }

    /**
     * add MapObject to the MapMode
     * 
     * @param p
     *        coordinate the MapObject should be placed
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
     * Removes a MapObject from the active mapMode.
     * 
     * @param mapObject
     *        MapObject to be removed.
     */
    public void removeMapObject (MapObject mapObject) {
        if (mapObject == null) { return; }
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
            // player near the left boundary
            myTopLeftCoord.x = 0;
            // screen fixed when player moves to the edge
            displacement.x = 0;
        }
        else if ((myTopLeftCoord.x + myNumDisplayCols) * myCurrentTileWidth +
                 myPlayer.getDirection().x > myMapSize.width * myCurrentTileWidth) {
            myTopLeftCoord.x = myMapSize.width - myNumDisplayCols;
            displacement.x = 0;
        }
        if (myTopLeftCoord.y * myCurrentTileHeight + myPlayer.getDirection().y < 0) {
            // player near the top boundary
            myTopLeftCoord.y = 0;
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
        HashMap<Point, MapObject> movedObjects = new HashMap<Point, MapObject>();
        for (Point p : myMapObjects.keySet()) {
            List<MapObject> objectsOnTile = getSpritesOnTile(p.x, p.y);
            Iterator<MapObject> it = objectsOnTile.iterator();
            while (it.hasNext()) {
                MapObject currentObject = it.next();
                Point startPos = currentObject.getLocation();
                currentObject.update();
                Point endPos = currentObject.getLocation();
                if (!endPos.equals(startPos)) {
                    movedObjects.put(startPos, currentObject);
                }
                if (!currentObject.isVisible()) {
                    it.remove();
                }
            }
        }
        for (Point startPos : movedObjects.keySet()) {
            MapObject currentObject = movedObjects.get(startPos);
            myMapObjects.get(startPos).remove(currentObject);
            if (currentObject.getAllowableModes().contains(getName())) {
                myMapObjects.get(currentObject.getLocation()).add(currentObject);
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
        if (result.x == 0) {
            // screen movement done!
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

    /**
     * Returns the camera currently in use.
     * 
     * @return Camera
     */
    public Rectangle getCamera () {
        return myCurrentCamera;
    }

    /**
     * Returns the current tile dimensions.
     * 
     * @return Dimension using current tile width and height.
     */
    public Dimension getTileDimensions () {
        return new Dimension(myCurrentTileWidth, myCurrentTileHeight);
    }

    /**
     * Returns Point at which origin is currently located.
     * 
     * @return
     */
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
            // enable/disable multi-destination feature in PathFinder
            GamePane.keyboardController.setControl(KeyEvent.VK_SHIFT, KeyboardController.PRESSED,
                                                   myPathFinder, "activateMultiDestination");
            GamePane.keyboardController.setControl(KeyEvent.VK_SHIFT, KeyboardController.RELEASED,
                                                   myPathFinder, "deactivateMultiDestination");
        }
        catch (NoSuchMethodException e) {
            System.out.println("A method was called that does not exist!");
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns current mapsize.
     * 
     * @return myMapSize
     */
    public Dimension getMapSize () {
        return myMapSize;
    }

    private void setMapSize (Dimension mapSize) {
        myMapSize = mapSize;
    }

    private void setPlayer (MapPlayerObject p) {
        myPlayer = p;
    }

    /**
     * Returns current player.
     * 
     * @return myPlayer
     */
    public MapPlayerObject getPlayer () {
        return myPlayer;
    }

    /**
     * Returns boolean of whether point is within current bounds.
     * 
     * @param dest
     *        Point to check.
     * @return Boolean true if point is within bounds, false if not.
     */
    public boolean isWithinBounds (Point dest) {
        return myMapSize.height > dest.y && myMapSize.width > dest.x && dest.x >= 0 && dest.y >= 0;
    }

    @Override
    public void processMouseInput (int mousePressed, Point myMousePosition, int myMouseButton) {
        if (mousePressed == GamePane.MOUSE_CLICKED && myMouseButton == MouseEvent.BUTTON3) {
            myPathFinder.stop();
            Point target =
                    new Point((int) myMousePosition.getX() / myCurrentTileWidth + myTopLeftCoord.x,
                              (int) myMousePosition.getY() / myCurrentTileHeight + myTopLeftCoord.y);
            myPathFinder.addTask(myPlayer, target, myMapSize);
            myPathFinder.executeSearch();
        }
    }
}
