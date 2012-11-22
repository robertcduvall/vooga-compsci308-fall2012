package vooga.turnbased.gamecore.pathutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Abstract class which describes common features of a search algorithm
 * for searching a path between 2 points in a table
 * 
 * @author rex
 */
public abstract class PathSearch {

    private boolean[][] myVisited;
    private List<Point> myPath;
    private Point myStart;
    private Point myEnd;
    private Dimension mySize;

    /**
     * Constructor of PathSearch
     * 
     * @param start Starting point
     * @param end Ending point
     * @param size Size of the table
     */
    public PathSearch (Point start, Point end, Dimension size) {
        myStart = start;
        myEnd = end;
        mySize = size;
        myPath = new ArrayList<Point>();
        myVisited = new boolean[mySize.width][mySize.height];
    }

    /**
     * translate a point by adding the coordinates of the second point
     * @param a First point
     * @param b Second point
     * @return the point whose x, y coordinates are the sum of the 
     * x, y coordinates of the two points
     */
    protected Point translatePoint (Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y);
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
        markVisited(x, y); // mark visited if the node has not yet been visited
        return false;
    }

    /**
     * mark a point as visited
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     */
    protected void markVisited (int x, int y) {
        myVisited[x][y] = true;
    }

    /**
     * find a possible path between start and destination
     * 
     * @param start The starting point
     * @return if a path could be found
     */
    public abstract boolean findPath (Point start);

    /**
     * check if a movement is possible, or will get out of boundaries
     * 
     * @param position original position of the object
     * @param direction direction it takes
     * @return if a movement is possible
     */
    public boolean validateMove (Point position, Point direction) {
        if ((position.x + direction.x >= mySize.width) || (position.x + direction.x < 0)) { return false; }
        if ((position.y + direction.y >= mySize.height) || (position.y + direction.y < 0)) { return false; }
        return true;
    }

    protected Point getStart () {
        return myStart;
    }

    protected void setStart (Point myStart) {
        this.myStart = myStart;
    }

    protected Point getEnd () {
        return myEnd;
    }

    protected void setEnd (Point myEnd) {
        this.myEnd = myEnd;
    }

    /**
     * return the path found so far
     * Call this method to retrieve the entire path after findPath method
     * 
     * @return list of Points that traces the path; empty list if no path is
     *         found
     */
    public List<Point> getImmutablePath () {
        return Collections.unmodifiableList(myPath);
    }

    protected void addToPath (Point p) {
        myPath.add(p);
    }

    protected void removeLastPointInPath () {
        myPath.remove(myPath.size() - 1);
    }

}
