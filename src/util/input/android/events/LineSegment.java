package util.input.android.events;

/**
 * A class wrapping data from a draw event on an andrid screen. If you are using
 * this data on a screen different from the one it is drawn on
 * you should use getRelative methods and multiply by the width or height of
 * your screen.
 * 
 * @author Ben
 * 
 */
public class LineSegment extends AndroidControllerEvent {

    private int myStartX;
    private int myStartY;
    private int myEndX;
    private int myEndY;
    private int myParentWidth;
    private int myParentHeight;

    /**
     * Create a new line segment.
     * 
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param parentWidth
     * @param parentHeight
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
     * @return returns the relative starting X coordinate of the segment
     *         (between 0 and 1)
     */
    public double getRelativeStartX () {
        return myStartX / (double) myParentWidth;
    }

    /**
     * 
     * @return returns the relative starting Y coordinate of the segment
     *         (between 0 and 1)
     */
    public double getRelativeStartY () {
        return myStartY / (double) myParentHeight;
    }
    /**
     * 
     * @return returns the relative ending X coordinate of the segment (between 0 and 1)
     */
    public double getRelativeEndX () {
        return myEndX / (double) myParentWidth;
    }
    /**
     * 
     * @return returns the relative ending Y coordinate of the segment (between 0 and 1)
     */
    public double getRelativeEndY () {
        return myEndY / (double) myParentHeight;
    }
    /**
     * 
     * @return returns the starting x coordinate in terms of the originating screen.
     */
    public int getStartX () {
        return myStartX;
    }
    /**
     * 
     * @return returns the starting y coordinate in terms of the originating screen.
     */
    public int getStartY () {
        return myStartY;
    }
    /**
     * 
     * @return returns the ending x coordinate in terms of the originating screen.
     */
    public int getEndX () {
        return myEndX;
    }
    /**
     * 
     * @return returns the ending y coordinate in terms of the originating screen.
     */
    public int getEndY () {
        return myEndY;
    }

}
