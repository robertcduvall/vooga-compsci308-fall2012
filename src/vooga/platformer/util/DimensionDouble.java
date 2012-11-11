package vooga.platformer.util;

import java.awt.geom.Dimension2D;

public class DimensionDouble extends Dimension2D {
    private double myX;
    private double myY;
    
    /**
     *  Creates an instance of <code>DimensionDouble</code> with a width of zero and a height of zero.
     */
    public DimensionDouble() {
        myX = 0.0;
        myY = 0.0;
    }
   
    /**
     * Creates an instance of <code>DimensionDouble</code> whose width and height are the same as for the specified dimension.
     * @param d The specified dimension for the <code>width</code> and <code>height</code> values
     */
    public DimensionDouble (Dimension2D d) {
        myX = d.getWidth();
        myY = d.getHeight();
    }
    
    /**
     * Constructs a <code>DimensionDouble</code> and initializes it to the specified <code>width</code> and <code>height</code>.
     * @param width
     * @param height
     */
    public DimensionDouble(double width, double height) {
        myX = width;
        myY = height;
    }
    

    @Override
    public double getHeight () {
        return myY;
    }

    @Override
    public double getWidth () {
        return myX;
    }

    @Override
    public void setSize (double width, double height) {
        myX = width;
        myY = height;
    }

}
