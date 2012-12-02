package vooga.turnbased.gamecore.graphutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
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
public class MapModePathFinder extends PathFinder{

    private List<MapObject> myHighlightObjects;
    private MapMode myMap;
    private boolean myCancelMovement;
    private MovingMapObject myMovingObject;
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
    public MapModePathFinder (MapMode map, MovingMapObject object, Point target, Dimension mapSize) {
        super(object.getLocation(), target, new Dimension(mapSize));
        setPathSearch(new BreadthFirstSearch(getStart(), getEnd(), getSize()));
        myMap = map;
        myMovingObject = object;
        myPreviousLocation = getStart();
        myPathIndex = 0;
        isHighlighted = false;
        myHighlightObjects = new ArrayList<MapObject>();
        setPath(searchPath());
        if (pathIsEmpty()) { return; }
    }

    @Override
    protected void checkObstacles () {
        for (int i = 0; i < getSize().width; i++) {
            for (int j = 0; j < getSize().height; j++) {
                for (MapObject m : myMap.getSpritesOnTile(i, j)) {
                    if (m instanceof MapObstacleObject) {
                        getPathSearch().markVisited(i, j);
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
    protected MapItemObject generatePathIndicator (Point p) {
        return new MapItemObject(new TreeSet<String>(Arrays.asList(myMap.getName())), "NO_ACTION", p, GameWindow.importImage("HighlightPath"));
    }

    /**
     * highlight the path by generating a series of path indicators
     * Could be overriden if other ways of highlighting path are needed
     */
    protected void highlightPath () {
        for (Point p : getImmutablePath()) {
            MapObject m = generatePathIndicator(p);
            m.setMapMode(myMap);
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
        if ((!isHighlighted) && (getImmutablePath() != null)) {
            System.out.println("drawing");
            highlightPath();
        }
        if ((myPathIndex < getImmutablePath().size()) && !myCancelMovement) {
            myCurrentLocation = getPathUsingIndex(myPathIndex);
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