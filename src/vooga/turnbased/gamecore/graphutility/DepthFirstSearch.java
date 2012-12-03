package vooga.turnbased.gamecore.graphutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import vooga.turnbased.gamecore.gamemodes.MapMode;


/**
 * depth first search using Euclidean heuristic method
 * 
 * @author rex
 * 
 */
public class DepthFirstSearch extends PathSearch {

    /**
     * constructor of the DFS
     * 
     * @param start Starting point
     * @param end Ending point
     * @param size Size of the table
     */
    public DepthFirstSearch (Point start, Point end, Dimension size) {
        super(start, end, size);
    }

    /**
     * depth first search
     * 
     * @param current current position
     * @return if the path finding is successful
     */
    public boolean findPath (Point current) {
        if (checkVisited(current.x, current.y)) { return false; }
        // found the path
        if (current.equals(getEnd())) { return true; }
        Queue<Point> myOptions = validateMovements(current);
        while (!myOptions.isEmpty()) {
            Point p = new Point(myOptions.poll());
            addToPath(p);
            // recursion
            if (findPath(p)) {
                return true;
            }
            else {
                removeLastPointInPath();
            }
        }
        return false;
    }

    /**
     * validate the movement by checking if it is already visited
     * 
     * @param current the current Point to be checked
     * @return a queue of points representing where the object could move to in
     *         the next step. Sorted according to Eucledean heuristic method
     */
    private Queue<Point> validateMovements (Point current) {
        PriorityQueue<Point> myOptions = new PriorityQueue<Point>(2, new Comparator<Point>() {
            public int compare (Point a, Point b) {
                return Double.compare(a.distance(getEnd()), b.distance(getEnd()));
            }
        });
        if (validateMove(current, MapMode.LEFT)) {
            myOptions.add(translatePoint(current, MapMode.LEFT));
        }
        if (validateMove(current, MapMode.RIGHT)) {
            myOptions.add(translatePoint(current, MapMode.RIGHT));
        }
        if (validateMove(current, MapMode.UP)) {
            myOptions.add(translatePoint(current, MapMode.UP));
        }
        if (validateMove(current, MapMode.DOWN)) {
            myOptions.add(translatePoint(current, MapMode.DOWN));
        }
        return myOptions;
    }
}
