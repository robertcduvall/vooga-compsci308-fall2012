package vooga.turnbased.gamecore.graphutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * abstract path finder which provides basic functionality of finding path
 * multiple path supported (as the effect of shift+right click in WarCraft).
 * See vooga.turnbased.gamecore.graphutility.MapModePathFinder for an example to
 * extend the class and apply to games etc.
 * 
 * It is designed to be flexible for use in a variety of situations. 
 * Sub-classes simply need to override
 * methods that deals with specific display of the path
 * 
 * @author Rex Ying
 * 
 */
public abstract class PathFinder {
    private List<Point> myPath;
    private int myPathIndex;
    private boolean myIsMultiDestination;
    private boolean myHasTask;
    private boolean myIsHighlighted;
    private boolean myCancelMovement;
    private PathSearch myPathSearch;
    private Point myStart;
    private Point myEnd;
    private Dimension mySize;

    /**
     * constructor of the abstract PathFinder
     * 
     * @param start the starting point
     * @param end the ending point
     * @param size the size of the entire graph (note this can be a grid or an
     *        adjacency matrix, depending on whether search or Dijkstra is
     *        intended to be used)
     */
    public PathFinder (Point start, Point end, Dimension size) {
        this();
        myStart = start;
        myEnd = end;
        mySize = size;
    }

    /**
     * construct an empty path finder
     * need to call addTask(Point start, Point end, Dimension size) later
     */
    public PathFinder () {
        myPath = new ArrayList<Point>();
        myIsMultiDestination = false;
        myHasTask = false;
        myIsHighlighted = false;
        myCancelMovement = false;
        myPathIndex = 0;
    }

    /**
     * this method executes the path search
     */
    public void executeSearch () {
        if (mySize == null) { return; }
        List<Point> path = searchPath();
        if (!path.isEmpty()) {
            if (myIsMultiDestination) {
                myPath.addAll(path);
            }
            else {
                myPath = path;
                myPathIndex = 0;
            }
            myIsHighlighted = false;
            myHasTask = true;
            myCancelMovement = false;
        }
    }

    /**
     * add task to the path finder, by specifying starting, ending point and the
     * size
     * 
     * @param start starting point
     * @param end ending point
     * @param size size of the grid
     */
    public void addTask (Point start, Point end, Dimension size) {
        stop();
        if (myIsMultiDestination && (myEnd != null)) {
            myStart = myEnd;
        }
        else {
            myStart = start;
        }
        myEnd = end;
        mySize = size;
    }

    /**
     * Use PathSearch for finding path
     * It can either be a DepthFirstSearch, BreadthFirstSearch, Dijkstra.
     * Dijkstra should be used for dense graphs.
     * Default is BFS.
     * subclasses can set this as different algorithms, see the example of
     * MapModePathFinder
     * 
     * @return the path represented by a list of Points
     */
    protected List<Point> searchPath () {
        myPathSearch = new BreadthFirstSearch(myStart, myEnd, mySize);
        checkObstacles();
        myPathSearch.findPath(myStart);
        return myPathSearch.getPath();
    }

    /**
     * activate multi-Destination functionality
     * have activate/deactivate so that the input API is easier to be used
     */
    public void activateMultiDestination () {
        myIsMultiDestination = true;
    }

    /**
     * de-activate multi-Destination functionality
     * clear the task cache
     */
    public void deactivateMultiDestination () {
        myIsMultiDestination = false;
    }

    /**
     * whether there is no path found
     * 
     * @return whether path is empty
     */
    protected boolean pathIsEmpty () {
        return myPath.isEmpty();
    }

    /**
     * get a list representing the path found
     * 
     * @return A list of Points representing the path
     */
    protected List<Point> getImmutablePath () {
        return Collections.unmodifiableList(myPath);
    }

    /**
     * get a particular Point on the path using myPathIndex
     * 
     * @return the Point on the path
     */
    protected Point getPathUsingIndex () {
        return myPath.get(myPathIndex);
    }

    protected void incrementPathIndex () {
        myPathIndex++;
    }

    /**
     * mark obstacles as inaccessible by calling markVisited in a PathSearch
     * object it contains
     * should be used after PathSearch object is instantiated
     * It is an important step of pre-processing.
     * If there are really no obstacle checks needed, just override with empty
     * method
     */
    protected abstract void checkObstacles ();

    /**
     * highlight the path by generating a series of path indicators.
     * called in the updatePath cycle.
     */
    private void highlight () {
        myIsHighlighted = true;
        int highlightIndex = myPathIndex;
        while (highlightIndex < myPath.size()) {
            highlightPath(myPath.get(highlightIndex));
            highlightIndex++;
        }
    }

    /**
     * The abstract class of highlighting a particular position
     * sub-classes will determine how to indicate the path.
     * 
     * @param position Highlight the particular position
     */
    protected abstract void highlightPath (Point position);

    /**
     * used for display of the path.
     * need to be called in an update cycle of the application.
     * Override if subclasses wants to graphically display the path
     * 
     * @return whether update should be carried out (used for the benefit of
     *         subclasses)
     */
    public boolean updatePath () {
        if ((!myHasTask) || myCancelMovement) { return false; }
        if (myPathIndex >= getImmutablePath().size()) { return false; }
        if (!myIsHighlighted) {
            highlight();
        }
        return true;
    }

    /**
     * stop the update path process
     * sub-classes could also override to de-highlight path etc.
     */
    public void stop () {
        if (!myIsMultiDestination) {
            myCancelMovement = true;
            myPath.clear();
            dehighlightPath();
        }
    }

    /**
     * de-highlight the path when it is no longer needed (canceled movement)
     */
    protected abstract void dehighlightPath ();

    /**
     * get the path search instance which determines what kind of algorithm is
     * used
     * 
     * @return the path search instance used in the path finder
     */
    protected PathSearch getPathSearch () {
        return myPathSearch;
    }

    /**
     * this method should be followed by searchPath()
     * otherwise obviously the change in pathSearch will not be reflected
     * 
     * @param pathSearch the new PathSearch instance to be used
     */
    protected void setPathSearch (PathSearch pathSearch) {
        myPathSearch = pathSearch;
    }

    /**
     * get the starting point
     * only those in same utility package, or subclasses could access
     * 
     * @return the starting point of a path search
     */
    protected Point getStart () {
        return myStart;
    }

    /**
     * get the ending point
     * 
     * @return the ending point of a path search
     */
    protected Point getEnd () {
        return myEnd;
    }

    /**
     * get the size of the grid.
     * Not necessary for Dijkstra and BellmanFord:
     * In these cases, the size is simply Dimension(V, V), where V is the number
     * of vertices
     * 
     * @return the size of the grid
     */
    protected Dimension getSize () {
        return mySize;
    }
}
