package vooga.turnbased.gamecore.graphutility;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.Comparator;


public class ClusterUtility {

    private List<Point> mySpritePositions;

    public ClusterUtility (List<Point> positions) {
        mySpritePositions = positions;
    }

    /**
     * Get the convex of a graph, used when determining if player enters certain
     * polygon defined by positions of other objects (such as a particular kind
     * of monsters, as in Diablo II:)
     * 
     * @param mySpritePositions input
     * @return output
     */
    public List<Point> convexHull () {
        LinkedList<Point> pointsOnPolygon = new LinkedList<Point>();
        Point horizon = new Point(0, Integer.MAX_VALUE);
        for (Point p : mySpritePositions) {
            if (p.y < horizon.y || (p.y == horizon.y && p.x < horizon.x)) {
                horizon = p;
            }
        }
        Point reference = new Point(horizon);
        TreeSet<Point> sortedPoints = getSortedSetForPoints(reference);
        pointsOnPolygon.add(reference);
        for (Point p : mySpritePositions) {
            if (!p.equals(reference)) sortedPoints.add(p);
        }
        while (!sortedPoints.isEmpty()) {
            if (pointsOnPolygon.size() < 2) pointsOnPolygon.add(sortedPoints.pollFirst());
            if (sortedPoints.isEmpty()) {
                break;
            }
            Point last = pointsOnPolygon.getLast();
            Point secondLast = pointsOnPolygon.get(pointsOnPolygon.size() - 2);
            Point origin = sortedPoints.first();
            if (!rightHandCheck(last, secondLast, origin)) {
                pointsOnPolygon.add(sortedPoints.pollFirst());// take left hand turn only
            }
            else pointsOnPolygon.remove(pointsOnPolygon.size() - 1);
        }
        return new ArrayList<Point>(pointsOnPolygon);
    }

    public void setPositions (List<Point> newPositions) {
        mySpritePositions = newPositions;
    }

    private TreeSet<Point> getSortedSetForPoints (final Point reference) {
        TreeSet<Point> pointSet = new TreeSet<Point>(new Comparator<Point>() {
            public int compare (Point p0, Point p1) {
                double angleDifference = angleDifference(p0, p1, reference);
                if (angleDifference < 0 ||
                    (angleDifference == 0 && reference.distance(p0) < reference.distance(p1))) {
                    return -1; 
                }
                return 1;
            }
        });
        return pointSet;
    }

    private double angleDifference (Point p0, Point p1, Point reference) {
        return angleBetween(p0, reference) - angleBetween(p1, reference);
    }
    
    private double angleBetween (Point p0, Point p1) {
        return Math.atan2(p0.y - p1.y, p0.x - p1.x);
    }
    
    private boolean rightHandCheck(Point v1, Point v2, Point origin) {
        return (v1.x - v2.x) * (origin.y - v1.y) - (origin.x - v1.x) * (v1.y - v2.y) <= 0;
    }
}
