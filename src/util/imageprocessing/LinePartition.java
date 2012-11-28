package util.imageprocessing;

import java.awt.Point;
import java.util.Iterator;

/**
 * Reusable class
 * To partition a line into specified number of sections
 * 
 * @author Rex
 */
public class LinePartition implements Iterator{
    private Number[] myStartCoordinates;
    private Number[] myEndCoordinates;
    private Number[][] myPartitionPoints;
    private int myDimension;
    private int myNumberOfPoints;
    private int myNumberOfSections;
    private int myCurrentPosition;

    /**
     * Constructor with Number[] as parameters
     * These points are in multi-dimensional space
     * partition a line into sections of equal length
     * @param start Start point
     * @param end End point
     * @param numberOfPoints number of points desired
     */
    public LinePartition (Number[] start, Number[] end, int numberOfPoints) {
        myDimension = start.length;
        myStartCoordinates = start;
        myEndCoordinates = end;
        initializePartitionPoints(numberOfPoints);
    }

    /**
     * Constructor with Point as parameters (2-dimensional)
     * @param start Start Point
     * @param end End Point
     * @param numberOfPoints number of points desired
     */
    public LinePartition (Point start, Point end, int numberOfPoints) {  
        myDimension = 2;
        myStartCoordinates = new Number[]{start.x, start.y};
        myEndCoordinates = new Number[]{end.x, end.y};
        initializePartitionPoints(numberOfPoints);
    }

    /** 
     * initialize necessary variables related to myPartitionPoints 
     * and calculate myPartitionPoints
     * 
     * @param numberOfPoints number of points desired
     */
    private void initializePartitionPoints(int numberOfPoints) {  
        myCurrentPosition = 0;
        myNumberOfPoints = numberOfPoints;
        myPartitionPoints = new Number[numberOfPoints][myDimension];
        computePoints();
    }

    /**
     * compute points on the line that partition it into specified number of
     * sections
     *
     * @param start Start point
     * @param end End point
     */
    private void computePoints () {
        myNumberOfSections = myNumberOfPoints - 1;
        double[] distanceComponents = new double[myDimension];
        for (int i = 0; i < myDimension; i++) {  
            distanceComponents[i] = (myEndCoordinates[i].doubleValue() 
                    - myStartCoordinates[i].doubleValue())
                    / myNumberOfSections;
        }
        for (int i = 0; i < myNumberOfPoints; i++) {
            for (int j = 0; j < myDimension; j++) {  
                myPartitionPoints[i][j] = myStartCoordinates[j].doubleValue()
                    + distanceComponents[j] * i;
            }
        }
    }

    /** 
     * Main class used for testing purpose
     * 
     * @param args Not used
     */
    /*
    public static void main(String[] args) {
        Point myFirstPoint = new Point(0, 0);
        Point mySecondPoint = new Point(3, 3);
        Number[] myThirdPoint = new Number[]{0, 1.5, 3, 4.5};
        Number[] myForthPoint = new Number[]{10, 9, 8, 7};
        LinePartition myLP = new LinePartition(myThirdPoint, myForthPoint, 4);
        while (myLP.hasNext()) {  
            Number[] myCoordinates = myLP.next();
            for (Number i: myCoordinates) {
                System.out.print(i.doubleValue() + "  ");
                //System.out.print(i.intValue() + "  ");
            }
            System.out.println();
        }
    }
    */

    /**
     * get the next point
     * @return the next point
     */
    @Override
    public Number[] next () {
        Number[] result = myPartitionPoints[myCurrentPosition];
        myCurrentPosition++;
        return result;
    }

    /**
     * get the next Point if it is a 2D Point
     * @return the next Point
     */
    public Point nextPoint() {  
        if (myDimension != 2)
            return null;
        Number[] result = next();
        return new Point(result[0].intValue(), result[1].intValue());
    }

    /**
     * if there is another point yet to be visited
     * @return if all the points are visited
     */
    @Override
    public boolean hasNext() {
        return myCurrentPosition < myNumberOfPoints;
    }

    /**
     * simply go on to the next point (not very useful in this case)
     */
    @Override
    public void remove() {
        myCurrentPosition++; 
    }

    /**
     * start from the first point
     */
    public void goBackToStart () {
        myCurrentPosition = 0;
    }

    /**
     * change the coordinate of the Start point
     * @param start Start Point
     */
    public void changeStart (Number[] start) {
        myStartCoordinates = start;
        computePoints();
    }

    /**
     * change the coordinate of the End point
     * @param end End Point
     */
    public void changeEnd (Number[] end) {
        myEndCoordinates = end;
        computePoints();
    }

    /**
     * change the number of sections to be partitioned
     * @param numberOfSections number of sections it divides into
     */
    public void changeNumberOfSections (int numberOfSections) {
        myNumberOfSections = numberOfSections;
        computePoints();
    }
}
