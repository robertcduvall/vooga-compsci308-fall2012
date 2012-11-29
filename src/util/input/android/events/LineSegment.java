package util.input.android.events;

import java.io.Serializable;


/**
 * A data wrapper that represents a line segment drawn on an android touch
 * controller. Provides methods that give relative locations to translate to
 * screens of different sizes than the touch screen of the android device.
 * 
 * @author Ben Schwab
 * 
 */
public class LineSegment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -570200229732755260L;
    private int myStartX;
    private int myStartY;
    private int myEndX;
    private int myEndY;
    private int myParentWidth;
    private int myParentHeight;

    /**
     * Create a new line segment
     * 
     * @param startX the starting x location of the line
     * @param startY the starting y location of the line
     * @param endX the ending x location of the line
     * @param endY the ending y location of the line
     * @param parentWidth the width of the screen the line originated on
     * @param parentHeight the height of the screen the line originated on
     */
    public LineSegment (int startX, int startY, int endX, int endY, int parentWidth,
                        int parentHeight) {
        myStartX = startX;
        myStartY = startY;
        myEndX = endX;
        myEndY = endY;
        myParentWidth = parentWidth;
        myParentHeight = parentHeight;
    }

    /**
     * 
     * @return
     */
    public double getRelativeStartX () {
        return myStartX / (double) myParentWidth;
    }

    /**
     * 
     * @return
     */
    public double getRelativeStartY () {
        return myStartY / (double) myParentHeight;
    }

    /**
     * 
     * @return
     */
    public double getRelativeEndX () {
        return myEndX / (double) myParentWidth;
    }

    /**
     * 
     * @return
     */
    public double getRelativeEndY () {
        return myEndY / (double) myParentHeight;
    }

    /**
     * 
     * @return
     */
    public int getStartX () {
        return myStartX;
    }

    /**
     * 
     * @return
     */
    public int getStartY () {
        return myStartY;
    }

    /**
     * 
     * @return
     */
    public int getEndX () {
        return myEndX;
    }

    /**
     * 
     * @return
     */
    public int getEndY () {
        return myEndY;
    }

}
