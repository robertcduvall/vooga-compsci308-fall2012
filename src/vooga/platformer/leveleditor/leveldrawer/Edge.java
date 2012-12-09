package vooga.platformer.leveleditor.leveldrawer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;


/**
 * This class resembles an edge which connects two
 * vertices.
 * 
 * @author Paul Dannenberg
 * 
 */
public class Edge implements IEditorObject {

    private Vertex myFirst, mySecond;
    private static final String TOSTRING_MESSAGE = "Connected to: ";
    private static final int EDGE_THICKNESS = 10;

    /**
     * Creates a new Edge between two vertices.
     * 
     * @param first A vertex to connect to.
     * @param second Another vertex to connect to.
     */
    public Edge(Vertex first, Vertex second) {
        myFirst = first;
        mySecond = second;
    }

    @Override
    public void paint(Graphics2D pen) {
        pen.drawLine((int) myFirst.getCenter().getX(), (int) myFirst
                .getCenter().getY(), (int) mySecond.getCenter().getX(),
                (int) mySecond.getCenter().getY());
    }

    /**
     * Sets the first vertex of the edge.
     * 
     * @param first The new first vertex.
     */
    public void setFirst(Vertex first) {
        myFirst = first;
    }

    /**
     * Sets the second vertex of the edge.
     * 
     * @param second The new second vertex.
     */
    public void setSecond(Vertex second) {
        mySecond = second;
    }

    /**
     * Returns true if x and y are close to the center of the edge.
     */
    @Override
    public boolean contains(int x, int y) {
        return average(myFirst.getCenter(), mySecond.getCenter())
                .distance(x, y) < EDGE_THICKNESS;

    }

    /**
     * Averages the coordinates specified by the method parameters.
     * In other words, this finds the midpoint of two Point2D objects.
     * 
     * @param firstPoint The first point to include in the calculation.
     * @param secondPoint The second point to include in the calculation.
     * @return An object representing the midpoint.
     */
    private Point2D average(Point2D firstPoint, Point2D secondPoint) {
        return new Point.Double((firstPoint.getX() + secondPoint.getX()) / 2,
                (firstPoint.getY() + secondPoint.getY()) / 2);
    }

    /**
     * Returns the point along the edge equidistant from both vertices.
     */
    @Override
    public Point2D getCenter() {
        return average(myFirst.getCenter(), mySecond.getCenter());
    }

    /**
     * Sets the midpoint of this object and moves the Vertices to which
     * it is joined accordingly.
     */
    @Override
    public void setCenter(int x, int y) {
        Point2D currentCenter = getCenter();
        myFirst.move((int) (x - currentCenter.getX()),
                (int) (y - currentCenter.getY()));
        mySecond.move((int) (x - currentCenter.getX()),
                (int) (y - currentCenter.getY()));
    }

    /**
     * Checks whether or not this object shares a vertex with another
     * Edge.
     * 
     * @param other The other Edge that could share a common Vertex.
     * @param vertex The Vertex that should be tested to see if its
     *        other edge is specified by the parameter, other.
     * @return true if this object shares the specified vertex with
     *         the Edge, other. False, otherwise.
     */
    public boolean isSharing(Edge other, Vertex vertex) {
        return myFirst.equals(other.mySecond) || mySecond.equals(other.myFirst);
    }

    @Override
    public String toString() {
        return TOSTRING_MESSAGE + myFirst.toString() + mySecond.toString();
    }
}
