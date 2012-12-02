package vooga.platformer.leveleditor.leveldrawer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;


/**
 * This class represents a point in 2D space that
 * can be drawn.
 * 
 * @author Paul Dannenberg
 * 
 */
public class Vertex implements IEditorObject {

    private Point2D myLocation;
    private static final int DIAMETER = 10;
    private Color myColor = Color.BLACK;

    /**
     * Creates a Vertex at particular coordinates.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Vertex(int x, int y) {
        myLocation = new Point(x, y);
    }

    /**
     * Creates a Vertex at particular coordinates.
     * 
     * @param location The location at which to
     *        create the new Vertex object.
     */
    public Vertex(Point2D location) {
        myLocation = location;
    }

    /**
     * Draws this object using a Graphics2D object.
     */
    @Override
    public void paint(Graphics2D pen) {
        Color savedColor = pen.getColor();
        drawCircle(pen, myColor);
        pen.setColor(savedColor);
    }

    /**
     * Draws a circle to represent this object.
     * 
     * @param pen To be used to draw this object.
     * @param circleColor The color this object will
     *        appear as on the screen.
     */
    private void drawCircle(Graphics2D pen, Color circleColor) {
        Ellipse2D.Double circle = new Ellipse2D.Double(myLocation.getX(),
                myLocation.getY(), DIAMETER, DIAMETER);
        pen.setColor(circleColor);
        pen.fill(circle);
    }

    /**
     * If x and y lie inside the region defined by
     * the radius of this object, switches this
     * object's color to red. Otherwise the object
     * will appear black.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void determineColor(int x, int y) {
        myColor = contains(x, y) ? Color.red : Color.black;
    }

    /**
     * Returns the center of the circle as it
     * appears on the screen.
     */
    @Override
    public Point2D getCenter() {
        return new Point2D.Double(myLocation.getX() + DIAMETER / 2,
                myLocation.getY() + DIAMETER / 2);
    }

    /**
     * Returns true if x and y are contained
     * within this object.
     */
    @Override
    public boolean contains(int x, int y) {
        return getCenter().distance(x, y) < DIAMETER / 2;
    }

    /**
     * Sets the center of this object to the
     * coordinates x and y.
     */
    @Override
    public void setCenter(int x, int y) {
        myLocation.setLocation(x, y);
        // This isn't quite right. Should
        // account for the diameter of this
        // object, I think.
    }

    /**
     * Moves the object by a specified amount.
     * 
     * @param dx The amount the object should move in the x
     *        direction.
     * @param dy The amount the object should move in the y
     *        direction.
     */
    public void move(int dx, int dy) {
        myLocation.setLocation(myLocation.getX() + dx, myLocation.getY() + dy);
    }

    @Override
    public String toString() {
        return myLocation.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vertex)) return false;
        return myLocation.equals(((Vertex) other).myLocation);
    }

    @Override
    public int hashCode() {
        return myLocation.hashCode();
    }
}
