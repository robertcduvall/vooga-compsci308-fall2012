package vooga.platformer.leveleditor.leveldrawer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;


/**
 * This class represents a Shape that can easily be moved.
 * 
 * @author Paul Dannenberg
 * 
 */
public class MovableShape extends Path2D.Double implements IEditorObject {

    private static final long serialVersionUID = -3421477641292853542L;
    private static final String ERROR_MESSAGE = "The input Collection has no corners: ";

    /**
     * Given a collection of vertices, creates a Shape representation
     * of them.
     * 
     * @param corners The corners from which the shape will be constructed.
     */
    public MovableShape(List<Vertex> corners) {
        createShape(corners);
    }

    /**
     * Generates the Shape itself from a list of vertices.
     * 
     * @param shapeCorners A collection specifying the corners of the shape.
     */
    private void createShape(List<Vertex> shapeCorners) {
        if (shapeCorners == null || shapeCorners.isEmpty())
            throw new IllegalArgumentException(ERROR_MESSAGE + shapeCorners);

        for (IEditorObject corner : shapeCorners) {
            if (corner.equals(shapeCorners.get(0))) {
                moveTo(shapeCorners.get(0).getCenter().getX(), shapeCorners
                        .get(0).getCenter().getX());
            } else {
                lineTo(corner.getCenter().getX(), corner.getCenter().getY());
            }
        }
    }

    @Override
    public void paint(Graphics2D pen) {
        pen.draw(this);
    }

    /**
     * Moves this object by a specified amount.
     * 
     * @param dx The amount to move the object in the x direction.
     * @param dy The amount to move the object in the y direction.
     */
    public void move(int dx, int dy) {
        transform(AffineTransform.getTranslateInstance(dx, dy));
    }

    /**
     * Returns the center of the bounding region of this shape.
     */
    @Override
    public Point2D getCenter() {
        Rectangle bounds = getBounds();
        return new Point((bounds.x + bounds.width) / 2,
                (bounds.y + bounds.height) / 2);
    }

    /**
     * Sets the center of this object. This is done so as to make
     * the bounding region of this object coincident with x and y.
     */
    @Override
    public void setCenter(int x, int y) {
        move((int) (x - getCenter().getX()), (int) (y - getCenter().getY()));
    }

    @Override
    public boolean contains(int x, int y) {
        return contains((double) x, (double) y);
    }

}
