package vooga.platformer.leveleditor.leveldrawer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import vooga.platformer.leveleditor.IEditorMode;


/**
 * This class allows users to draw shapes. These shapes can be
 * open or closed. However, this class only
 * supports the drawing of shapes with straight edges
 * (e.g. squares, triangles but NOT circles, ellipses etc.).
 * 
 * @author Paul Dannenberg
 * 
 */
public class DrawingMode implements IEditorMode {

    private List<IEditorObject> myDrawn;
    private LinkedList<Edge> mySides;
    private LinkedList<Vertex> myCorners;
    private Edge myEdgeToCursor;
    private Vertex myCursor;

    /**
     * Creates a new instance of this class.
     * 
     */
    public DrawingMode() {
        myDrawn = new ArrayList<IEditorObject>();
        mySides = new LinkedList<Edge>();
        myCorners = new LinkedList<Vertex>();
    }

    /**
     * Places a point that will represent a corner.
     * 
     * @param x The x coordinate of the corner.
     * @param y The y coordinate of the corner.
     */
    public void placeCorner(int x, int y) {
        Vertex next = new Vertex(x, y);
        if (!isCurrentlyDrawing()) {
            placeFirstCorner(x, y, next);
        } else {
            mySides.add(new Edge(myCorners.peekLast(), next));
            myCorners.add(next);
            myEdgeToCursor.setFirst(next);
        }
    }

    private void placeFirstCorner(int x, int y, Vertex next) {
        myCorners.add(next);
        myCursor = new Vertex(x, y);
        myEdgeToCursor = new Edge(next, myCursor);
    }

    /**
     * Closes a shape, transforming a path of vertices and
     * edges into a polygon.
     */
    public void enclose() {
        mySides.add(new Edge(myCorners.peekLast(), myCorners.peekFirst()));
    }

    /**
     * Converts the points laid out by the user into a LevelShape.
     * 
     */
    private void finalizeShape() {
        MovableShape drawnShape = new MovableShape(myCorners);
        if (mySides.peekLast().isSharing(mySides.peekLast(),
                myCorners.peekFirst())) {
            drawnShape.closePath();
        }
        myDrawn.add(drawnShape);
    }

    private void reset() {
        mySides.clear();
        myCorners.clear();
    }

    /**
     * 
     * @return true if a shape is in the process of being drawn.
     *         A shape is currently being drawn if the user has
     *         already placed at least one corner.
     */
    private boolean isCurrentlyDrawing() {
        return !myCorners.isEmpty();
    }

    /**
     * Determines if the first placed corner needs to be
     * highlighted. Also creates an
     * edge from the most recently placed corner to the cursor
     * location. Basically, this method is responsible for
     * making the drawing process look pretty.
     */
    @Override
    public void sendCursorPosition(int x, int y) {
        if (isCurrentlyDrawing()) {
            myCorners.getFirst().determineColor(x, y);
            updateEdgeToCursor(x, y);
            myCursor.setCenter(x, y);
        }
    }

    /**
     * Places a corner or closes an open path, forming a closed shape if
     * the primaryButtonPress occurred over the first placed corner.
     */
    @Override
    public void primaryButtonPress(int x, int y) {
        if (isCurrentlyDrawing() && myCorners.peekFirst().contains(x, y)) {
            enclose();
            finalizeShape();
            reset();
        } else {
            placeCorner(x, y);
        }
    }

    /**
     * Finishes drawing a shape.
     */
    @Override
    public void secondaryButtonPress(int x, int y) {
        finalizeShape();
        reset();
    }

    /**
     * Draws an edge from the most recently placed
     * vertex to the cursor.
     */
    private void updateEdgeToCursor(int x, int y) {
        myEdgeToCursor.setFirst(myCorners.peekLast());
    }

    /**
     * Not as readable, but it saves me a whole for-each loop!
     * 
     * @param pen The object with which to paint.
     * @param toPaint The object to iterate over and paint all elements.
     */
    private <T extends IEditorObject> void paintCurrent(Graphics2D pen,
            Iterable<T> toPaint) {
        for (T shapePiece : toPaint) {
            shapePiece.paint(pen);
        }
    }

    /**
     * Draws all the currently active edges and corners as well
     * as all shapes that have already been completed.
     */
    @Override
    public void paint(Graphics2D pen) {
        if (isCurrentlyDrawing()) {
            myEdgeToCursor.paint(pen);
            myCursor.paint(pen);
        }
        paintCurrent(pen, mySides);
        paintCurrent(pen, myCorners);
        paintCurrent(pen, myDrawn);
    }

}
