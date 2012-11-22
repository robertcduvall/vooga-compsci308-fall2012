package vooga.turnbased.gamecore.pathutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.mapobject.MapItemObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapObstacleObject;
import vooga.turnbased.gameobject.mapobject.MovingMapObject;
import vooga.turnbased.gui.GameWindow;


/**
 * path finding for a MovingMapObject to go to a target position
 * 
 * @author rex
 * 
 */
public class PathFinder implements Runnable {

    private static final int ATTEMPT_INTERVAL = 60;
    private List<Point> myPath;
    private List<MapObject> myHighlightObjects;
    private MapMode myMap;
    private boolean[][] myVisited;
    private boolean myCancelMovement;
    private Point myStart;
    private Point myEnd;
    private Dimension mySize;
    private Thread myMovementThread;
    private MovingMapObject myMovingObject;
    private PathSearch myPathSearch;

    /**
     * constructor
     * 
     * @param map MapMode on which the path finding is applied
     * @param object MapObject to be moved
     * @param target target position
     * @param mapSize bottom right corner of the entire map
     */
    public PathFinder (MapMode map, MovingMapObject object, Point target, Dimension mapSize) {
        myMap = map;
        myMovingObject = object;
        myStart = object.getLocation();
        myEnd = target;
        myHighlightObjects = new ArrayList<MapObject>();
        mySize = new Dimension(mapSize);
        myPath = searchPath();
        if (myPath.isEmpty()) { return; }
        highlightPath();
        myMovementThread = new Thread(this);
        myMovementThread.start();
    }
    
    private List<Point> searchPath() {
        myPathSearch = new DepthFirstSearch(myStart, myEnd, mySize);
        checkObstacles();
        myPathSearch.findPath(myStart);
        return myPathSearch.getImmutablePath();
    }

    /**
     * check if the point is visited
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if the (x, y) position is already visited
     */
    public boolean checkVisited (int x, int y) {
        if (myVisited[x][y]) { return true; }
        myVisited[x][y] = true; // mark visited if the point has not yet been
                                // visited
        return false;
    }

    /**
     * check if the player can move to the tile at the coordinate x, y
     * 
     * @param x x-coordinate in the current map
     * @param y y-coordinate in the current map
     * @return if the player can move to the tile at the coordinate x, y
     */
    protected boolean canMoveTo (int x, int y) {
        for (MapObject m : myMap.getSpritesOnTile(x, y)) {
            if (m instanceof MapObstacleObject) { return false; }
        }
        return true;
    }
    
    /**
     * mark obstacles as inaccessible
     */
    private void checkObstacles () {
        for (int i = 0; i < mySize.width; i++) {
            for (int j = 0; j < mySize.height; j++) {
                for (MapObject m : myMap.getSpritesOnTile(i, j)) {
                    if (m instanceof MapObstacleObject) { 
                        myPathSearch.markVisited(i, j);
                    }
                }
            }
        }
    }

    /**
     * stop the moving process and the highlighted path disappeared
     */
    public void stop () {
        for (MapObject m : myHighlightObjects) {
            m.setVisible(false);
        }
        myCancelMovement = true;
    }

    /**
     * loop that moves the object
     */
    @Override
    public synchronized void run () {
        if (myPath.isEmpty()) { return; }
        Point previousPoint = myStart;
        Point currentPoint;
        for (int i = 0; i < myPath.size(); i++) {
            currentPoint = myPath.get(i);
            Point direction =
                    new Point(currentPoint.x - previousPoint.x, currentPoint.y - previousPoint.y);
            previousPoint = currentPoint;
            while (myMovingObject.isMoving()) {
                try {
                    Thread.sleep(ATTEMPT_INTERVAL);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (myCancelMovement) {
                break;
            }
            myMovingObject.tryMove(direction);
        }
    }

    private MapItemObject generatePathIndicator (Point p) {
        return new MapItemObject(0, "NO_ACTION", p, GameWindow.importImage("HighlightPath"), myMap);
    }

    private void highlightPath () {
        for (Point p : myPath) {
            MapObject m = generatePathIndicator(p);
            myMap.addMapObject(p, m);
            myHighlightObjects.add(m);
        }
    }
}
