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
 * An example of how PathFinder could be extended to apply to different games.
 * All of the methods in this class deal with the specific ways to show the
 * path in the MapMode of the turnbased game.
 * 
 * @author Rex Ying
 */
public class MapModePathFinder extends PathFinder {

    private List<MapObject> myHighlightObjects;
    private MapMode myMap;
    private MovingMapObject myMovingObject;

    // for execution of walking along the path

    /**
     * constructor
     * 
     * @param map MapMode on which the path finding is applied
     * @param object MapObject to be moved
     * @param target target position
     * @param size bottom right corner of the entire map
     */
    public MapModePathFinder (MapMode map, MovingMapObject object, Point target, Dimension size) {
        super(object.getLocation(), target, new Dimension(size));
        initialize(map, object);
        setPathSearch(new BreadthFirstSearch(getStart(), getEnd(), getSize()));
    }

    /**
     * default constructor.
     * needs to add task after constructing using this default one
     * 
     * @param map The MapMode where this path exists
     * @param object The MapObject which is will follow the path (i.e. the
     *        player)
     */
    public MapModePathFinder (MapMode map, MovingMapObject object) {
        super();
        myHighlightObjects = new ArrayList<MapObject>();
        initialize(map, object);
    }

    /**
     * initialize variables specific to this MapModePathFinder
     * 
     * @param map the MapMode instance on which the path should be found
     * @param object the object which needs to find a path
     */
    private void initialize (MapMode map, MovingMapObject object) {
        myMap = map;
        myMovingObject = object;
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
     * add a new task to the path finder
     * 
     * @param object the object that needs to find path
     * @param target the target position
     * @param mapSize the size of the map
     */
    public void addTask (MovingMapObject object, Point target, Dimension mapSize) {
        super.addTask(object.getLocation(), target, new Dimension(mapSize));
        initialize(myMap, object);
    }

    @Override
    protected void dehighlightPath () {
        for (MapObject m : myHighlightObjects) {
            m.setVisible(false);
        }
    }

    /**
     * generate path indicator as MapItemObject
     * 
     * @param p Position the indicator should be placed
     * @return the MapItemObject representing a path indicator
     */
    protected MapItemObject generatePathIndicator (Point p) {
        return new MapItemObject(new TreeSet<String>(Arrays.asList(myMap.getName())), "NO_ACTION",
                                 p, GameWindow.importImage("HighlightPath"));
    }

    /**
     * highlight by generating path indicators
     * Could be overridden if other ways of highlighting path are needed
     */
    @Override
    protected void highlightPath (Point position) {
        MapObject m = generatePathIndicator(position);
        m.setMapMode(myMap);
        myMap.addMapObject(position, m);
        myHighlightObjects.add(m);
    }

    /**
     * update the movement along the path (should be called in an update cycle
     * of the object which has an instance of active PathFinder
     */
    @Override
    public boolean updatePath () {
        if (!super.updatePath()) { return false; }
        Point currentLocation = getPathUsingIndex();
        if (myMovingObject.tryMoveTo(currentLocation)) {
            incrementPathIndex();
        }
        return true;
    }
}
