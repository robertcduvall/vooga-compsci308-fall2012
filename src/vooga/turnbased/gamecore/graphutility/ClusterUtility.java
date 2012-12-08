package vooga.turnbased.gamecore.graphutility;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;


/**
 * Utility for dealing with monsters in a game that cluster together
 * Including checking the cluster polygon and area
 * 
 * @author rex
 * 
 */
public class ClusterUtility {

    private List<Point> mySpritePositions;
    private List<Point> myPolygon;

    /**
     * constructor
     * 
     * @param positions coordinates of the points (monsters) concerned
     */
    public ClusterUtility (List<Point> positions) {
        mySpritePositions = positions;
    }

    /**
     * Get the convex of a graph, used when determining if player enters certain
     * polygon defined by positions of other objects (such as a particular kind
     * of monsters, as in Diablo II:)
     * 
     * @return points on the polygon (in order)
     */
    public List<Point> convexHull () {
        LinkedList<Point> pointsOnPolygon = new LinkedList<Point>();
        Point reference = findReferencePoint();
        TreeSet<Point> sortedPoints = getSortedSetForPoints(reference);
        pointsOnPolygon.add(reference);
        while (!sortedPoints.isEmpty()) {
            if (pointsOnPolygon.size() < 2) {
                pointsOnPolygon.add(sortedPoints.pollFirst());
            }
            if (sortedPoints.isEmpty()) {
                break;
            }
            Point last = pointsOnPolygon.getLast();
            Point secondLast = pointsOnPolygon.get(pointsOnPolygon.size() - 2);
            Point origin = sortedPoints.first();
            if (!rightHandCheck(last, secondLast, origin)) {
                // take left hand turn only
                pointsOnPolygon.add(sortedPoints.pollFirst());
            }
            else {
                pointsOnPolygon.remove(pointsOnPolygon.size() - 1);
            }
        }
        return new ArrayList<Point>(pointsOnPolygon);
    }

    /**
     * set new positions
     * 
     * @param newPositions new positions
     */
    public void setPositions (List<Point> newPositions) {
        mySpritePositions = newPositions;
    }

    /**
     * find the reference point, which is the point that has the smallest angle
     * with x-axis. (break ties by selecting the one with smallest x-coordinate)
     * 
     * @return reference point
     */
    private Point findReferencePoint () {
        Point reference = new Point(0, Integer.MAX_VALUE);
        for (Point p : mySpritePositions) {
            if (p.y < reference.y || (p.y == reference.y && p.x < reference.x)) {
                reference = p;
            }
        }
        return reference;
    }

    private TreeSet<Point> getSortedSetForPoints (final Point reference) {
        TreeSet<Point> pointSet = new TreeSet<Point>(new Comparator<Point>() {
            public int compare (Point p0, Point p1) {
                double angleDiff = angleDifference(p0, p1, reference);
                if ((angleDiff == 0 && reference.distance(p0) < reference.distance(p1)) ||
                    angleDiff < 0) { return -1; }
                return 1;
            }
        });
        for (Point p : mySpritePositions) {
            if (!p.equals(reference)) {
                pointSet.add(p);
            }
        }
        return pointSet;
    }

    private double angleDifference (Point p0, Point p1, Point reference) {
        return angleBetween(p0, reference) - angleBetween(p1, reference);
    }

    private double angleBetween (Point p0, Point p1) {
        return Math.atan2(p0.y - p1.y, p0.x - p1.x);
    }

    private boolean rightHandCheck (Point v1, Point v2, Point origin) {
        return (v1.x - v2.x) * (origin.y - v1.y) - (origin.x - v1.x) * (v1.y - v2.y) <= 0;
    }

    /**
     * calculate polygon area of the cluster
     * 
     * @param x array of x values
     * @param y array of y values
     * @return the area (in double, for the purpose of accuracy)
     */
    public double clusterArea (double[] x, double[] y) {
        myPolygon = convexHull();
        Point firstPoint = myPolygon.get(0);
        myPolygon.add(new Point(firstPoint));
        double area = 0;
        for (int i = 1; i < myPolygon.size(); i++) {
            // apply formula
            area += myPolygon.get(i - 1).x * myPolygon.get(i).y - myPolygon.get(i).x *
                            myPolygon.get(i - 1).y;
        }
        area /= 2;
        return Math.abs(area);
    }
}
