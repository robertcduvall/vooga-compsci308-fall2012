package vooga.turnbased.gamecore;

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
import java.util.Map;
import util.imageprocessing.ImageLoop;
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
    private Point myTopLeftCoord;

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
                        "NO_ACTION", p, GameWindow
                                .importImage("GrassImage"), this));
            }
        }
        Point center = new Point(7, 5);
        Map<String, Image> images = new HashMap<String, Image>();
        images.put("left", GameWindow.importImage("PlayerLeft"));
        images.put("right", GameWindow.importImage("PlayerRight"));
        images.put("down", GameWindow.importImage("PlayerDown"));
        images.put("up", GameWindow.importImage("PlayerUp"));
        Map<String, ImageLoop> imageLoops = new HashMap<String, ImageLoop>();
        Image left = GameWindow.importImage("PlayerLeft");
        Image left1 = GameWindow.importImage("PlayerLeft1");
        Image left2 = GameWindow.importImage("PlayerLeft2");
        Image right = GameWindow.importImage("PlayerRight");
        Image right1 = GameWindow.importImage("PlayerRight1");
        Image right2 = GameWindow.importImage("PlayerRight2");
        Image up = GameWindow.importImage("PlayerUp");
        Image up1 = GameWindow.importImage("PlayerUp1");
        Image up2 = GameWindow.importImage("PlayerUp2");
        Image down = GameWindow.importImage("PlayerDown");
        Image down1 = GameWindow.importImage("PlayerDown1");
        Image down2 = GameWindow.importImage("PlayerDown2");
        List<Image> leftList = new ArrayList<Image>();
        leftList.add(left);
        leftList.add(left1);
        leftList.add(left2);
        imageLoops.put("left", new ImageLoop(leftList));
        List<Image> rightList = new ArrayList<Image>();
        rightList.add(right);
        rightList.add(right1);
        rightList.add(right2);
        imageLoops.put("right", new ImageLoop(rightList));
        List<Image> upList = new ArrayList<Image>();
        upList.add(up);
        upList.add(up1);
        upList.add(up2);
        imageLoops.put("up", new ImageLoop(upList));
        List<Image> downList = new ArrayList<Image>();
        downList.add(down);
        downList.add(down1);
        downList.add(down2);
        imageLoops.put("down", new ImageLoop(downList));

        myPlayer = new MapPlayerObject(ID, "MAP_COLLISION",
                center, images, this);
        myPlayer.setImageLoops(imageLoops);
        addMapObject(center, myPlayer);

        center = new Point(5, 5);
        MovingMapObject test1 = new MovingMapObject(ID,
                "MAP_COLLISION", center, GameWindow
                        .importImage("something"), this);
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
        updateMapObjects();
        processGameEvents();
    }

    /**
     * update tile's width and height, which will change when the window is
     * resized
     */
    public void updateTileInfo () {
        myCurrentTileWidth = getGameManager().getPaneDimension().width
                / myNumDisplayCols;
        myCurrentTileHeight = getGameManager().getPaneDimension().height
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
        myTopLeftCoord = calculateTopLeftCoordinate();
        if (myTopLeftCoord.x * myCurrentTileWidth + myPlayer.getDirection().x < 0) {
            myTopLeftCoord.x = 0; // player near the left boundary
            displacement.x = 0; // screen fixed when player moves to the edge
        }
        else if ((myTopLeftCoord.x + myNumDisplayCols) * myCurrentTileWidth
                + myPlayer.getDirection().x > myBottomRightCorner.x
                * myCurrentTileWidth) {
            myTopLeftCoord.x = myBottomRightCorner.x - myNumDisplayCols;
            displacement.x = 0;
        }
        if (myTopLeftCoord.y * myCurrentTileHeight + myPlayer.getDirection().y < 0) {
            myTopLeftCoord.y = 0; // player near the top boundary
            displacement.y = 0;
        }
        else if ((myTopLeftCoord.y + myNumDisplayRows) * myCurrentTileHeight
                + myPlayer.getDirection().y > myBottomRightCorner.y
                * myCurrentTileHeight) {
            myTopLeftCoord.y = myBottomRightCorner.y - myNumDisplayRows;
            displacement.y = 0;
        }
        myCurrentCamera = new Rectangle(myTopLeftCoord.x - 1,
                myTopLeftCoord.y - 1, myNumDisplayCols + 2,
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
                    it.remove();
                }
                else {
                    nextObject.update(getGameManager().getDelayTime());
                }
            }
        }
    }

    private void processGameEvents () { // this can be optimized A LOT, only
        // check mapobjects that did something last turn
        for (MapObject m : getSpritesOnTile(myPlayer.getLocation().x, myPlayer
                .getLocation().y)) {
            if (m != myPlayer) {
                m.interact(myPlayer);
            }
        }

        for (Point p : myMapObjects.keySet()) {
            HashMap<String, List<Integer>> myTileEvents = new HashMap<String, List<Integer>>();
            for (MapObject s : getSpritesOnTile(p.x, p.y)) {
                if (!myTileEvents.containsKey(s.getModeEvent())) {
                    myTileEvents
                            .put(s.getModeEvent(), new ArrayList<Integer>());
                }
                myTileEvents.get(s.getModeEvent()).add(s.getID());
            }
            for(String s : myTileEvents.keySet()){
                getGameManager().flagEvent(s, myTileEvents.get(s));
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
                s.setMoving(true);
            }
        }
        s.setDirection(dir); // direction changed even if the player is not
                             // moving
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
            Point target = new Point(e.getX() / myCurrentTileWidth
                    + myTopLeftCoord.x, e.getY() / myCurrentTileHeight
                    + myTopLeftCoord.y);
            myPathFinder = new PathFinder(this, myPlayer, target,
                    myBottomRightCorner);
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
        // will move all code for handling user input here
        // once input api allows invoking methods with arguments
    }
}
