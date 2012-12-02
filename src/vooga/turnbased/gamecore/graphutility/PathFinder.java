package vooga.turnbased.gamecore.graphutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * abstract path finder which provides basic functionality of finding path
 * multiple path supported (as the effect of shift+right click in WarCraft)
 * See vooga.turnbased.gamecore.graphutility.MapModePathFinder for an example to
 * extend the class and apply to games etc.
 * 
 * @author Rex Ying
 * 
 */
public abstract class PathFinder {
    private List<Point> myPath;
    private boolean myIsMultiDestination;
    private boolean myHasTask;
    private boolean myIsHighlighted;
    private boolean myCancelMovement;
    private Queue<GraphTask> myTaskCache;
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
        myTaskCache = new LinkedList<GraphTask>();
        myCancelMovement = false;
    }

    /**
     * this method executes the path search
     */
    public void executeSearch () {
        myPath = searchPath();
        if (!pathIsEmpty()) {
            myHasTask = true;
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
    protected void addTask (Point start, Point end, Dimension size) {
        if (myIsMultiDestination) {
            myTaskCache.add(new GraphTask(myStart, myEnd, mySize));
        }
        else {
            myStart = start;
            myEnd = end;
            mySize = size;
        }
    }

    /**
     * Use PathSearch for finding path
     * It can either be a DepthFirstSearch, BreadthFirstSearch, Dijkstra or
     * BellmanFord
     * Dijkstra should be used for dense graphs, BellmanFord used for sparse
     * graph
     * Default is BFS
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
     * get a particular Point on the path using index
     * 
     * @param index
     * @return the Point on the path
     */
    protected Point getPathUsingIndex (int index) {
        return myPath.get(index);
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
     * The abstract class does nothing to highlight.
     * sub-classes will determine how to indicate the path.
     * It should be called called in the updatePath cycle.
     */
    protected void highlightPath () {
        myIsHighlighted = true;
    }

    /**
     * used for display of the path.
     * need to be called in an update cycle of the application.
     * Override if subclasses wants to graphically display the path
     */
    public void updatePath () {
        if ((!myHasTask) || myCancelMovement) { return; }
        if ((!myIsHighlighted) && (getImmutablePath() != null)) {
            highlightPath();
        }
    }

    /**
     * stop the update path process
     * sub-classes could also override to dehighlight path etc.
     */
    public void stop () {
        myCancelMovement = true;
        myPath.clear();
        myTaskCache.clear();
    }

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

    /**
     * private inner class that describe a path finding task waiting to be
     * executed.
     * Used when multi-destination is on
     * 
     * @author rex
     * 
     */
    private class GraphTask {
        private Point start;
        private Point end;
        private Dimension size;

        protected GraphTask (Point start, Point end, Dimension size) {
            this.start = start;
            this.end = end;
            this.size = size;
        }

        protected Point getStart () {
            return start;
        }

        protected Point getEnd () {
            return end;
        }

        protected Dimension getSize () {
            return size;
        }
    }
}
