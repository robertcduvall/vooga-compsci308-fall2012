package vooga.turnbased.gamecore.pathutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import vooga.turnbased.gamecore.gamemodes.MapMode;
import vooga.turnbased.gameobject.mapobject.MapItemObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapObstacleObject;
import vooga.turnbased.gameobject.mapobject.MovingMapObject;
import vooga.turnbased.gui.GameWindow;


/**
 * path finding for a MovingMapObject to go to a target position
 * 
 * @author Rex Ying
 */
public class PathFinder {

    private static final int ATTEMPT_INTERVAL = 60;
    private List<Point> myPath;
    private List<MapObject> myHighlightObjects;
    private MapMode myMap;
    private boolean myCancelMovement;
    private Point myStart;
    private Point myEnd;
    private Dimension mySize;
    private MovingMapObject myMovingObject;
    private PathSearch myPathSearch;
    private boolean isHighlighted;
    // for execution of walking along the path
    private Point myPreviousLocation;
    private Point myCurrentLocation;
    private int myPathIndex;

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
        myPreviousLocation = myStart;
        myPathIndex = 0;
        isHighlighted = false;
        myHighlightObjects = new ArrayList<MapObject>();
        mySize = new Dimension(mapSize);
        myPath = searchPath();
        if (myPath.isEmpty()) { return; }

    }

    /**
     * use PathSearch for finding path
     * 
     * @return the path represented by a list of Points
     */
    private List<Point> searchPath () {
        // For now, it can either be a DepthFirstSearch, or BreadthFirstSearch
        myPathSearch = new BreadthFirstSearch(myStart, myEnd, mySize);
        checkObstacles();
        myPathSearch.findPath(myStart);
        return myPathSearch.getImmutablePath();
    }

    /**
     * mark obstacles as inaccessible
     * should be used after PathSearch object is instantiated
     * pre-processing
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
     * generate path indicator as MapItemObject
     * 
     * @param p Position the indicator should be placed
     * @return the MapItemObject representing a path indicator
     */
    private MapItemObject generatePathIndicator (Point p) {
        return new MapItemObject(0, "NO_ACTION", p, GameWindow.importImage("HighlightPath"), myMap);
    }

    /**
     * highlight the path by generating a series of path indicators
     */
    private void highlightPath () {
        for (Point p : myPath) {
            MapObject m = generatePathIndicator(p);
            myMap.addMapObject(p, m);
            myHighlightObjects.add(m);
        }
        isHighlighted = true;
    }

    /**
     * update the movement along the path (should be called in an update cycle
     * of the object which has an instance of active PathFinder
     */
    public void updatePath () {
        if ((!isHighlighted) && (myPath != null)) {
            System.out.println("drawing");
            highlightPath();
        }
        if ((myPathIndex < myPath.size()) && !myCancelMovement) {
            myCurrentLocation = myPath.get(myPathIndex);
            Point direction =
                    new Point(myCurrentLocation.x - myPreviousLocation.x, myCurrentLocation.y -
                                                                          myPreviousLocation.y);
            if (myMovingObject.tryMove(direction)) {
                myPathIndex++;
                myPreviousLocation = myCurrentLocation;
            }
        }
    }
}
