package vooga.platformer.leveleditor.leveldrawer;

import java.awt.geom.Point2D;
import vooga.platformer.leveleditor.IPaintable;


/*
 * There aren't really any methods in this interface
 * which really associate this interface with its
 * name IEditorObject, but I can't think up a better
 * name. The fact that I can't really settle on a name
 * is probably a sign that this interface isn't very
 * good...
 */
/**
 * This interface specifies the basic behavior of all
 * level editor objects that the user can interact with
 * on the screen - mainly (but not limited to) a drag
 * and drop fashion.
 * 
 * @author Paul Dannenberg
 * 
 */
public interface IEditorObject extends IPaintable {

    /**
     * Tests to see whether or not the IEditorObject
     * contains the point defined by x and y in its
     * interior.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return true if the point (x, y) lies within
     *         the bounds of the IEditorObject. False
     *         otherwise.
     */
    boolean contains(int x, int y);

    /**
     * Gets the center of the object. The definition
     * of center used is left to the implementing
     * classes.
     * 
     * @return The coordinates defining the center of
     *         the object.
     */
    Point2D getCenter();

    /**
     * Sets the object's center to a particular location.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    void setCenter(int x, int y);

}
