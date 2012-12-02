package vooga.turnbased.gamecore.graphutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * abstract path finder which provides basic functionality of finding path
 * multiple path supported (as the effect of shift+right click in WarCraft)
 * 
 * @author rex
 * 
 */
public abstract class PathFinder {
    private List<Point> myPath;
    private boolean myIsMultiDestination;
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
        myPath = new ArrayList<Point>();
        myIsMultiDestination = false;
        myStart = start;
        myEnd = end;
        mySize = size;
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
        return myPathSearch.getImmutablePath();
    }

    protected void setPath (List<Point> path) {
        myPath = path;
    }

    public void setMultiDestination (boolean isMultiDestination) {
        myIsMultiDestination = isMultiDestination;
    }

    protected boolean pathIsEmpty () {
        return myPath.isEmpty();
    }

    public List<Point> getImmutablePath () {
        return Collections.unmodifiableList(myPath);
    }

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
    protected Point getEnd() {
        return myEnd;
    }

    /**
     * get the size of the grid
     * Not necessary for Dijkstra and BellmanFord
     * 
     * @return the size of the grid
     */
    protected Dimension getSize () {
        return mySize;
    }
}
