package util.input.configurecontrollergui.controller;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import util.input.configurecontrollergui.util.Config;
import util.input.configurecontrollergui.view.RadioButtonView;

public class GridController {

    private List<Double> verticalLines;
    private List<Double> horizontalLines;
    private int myNumRows;
    private int myNumCols;
    private Point2D myOffset;
    
    public GridController(int numRows, int numCols) {
        myNumRows = numRows;
        myNumCols = numCols;
        verticalLines = new ArrayList<Double>();
        horizontalLines = new ArrayList<Double>();
    }
    
    public void initializePoints() {
        int verticalLineSpacing = (int) (Config.GRID_IMAGE_SIZE.getWidth()/myNumCols);
        int horizontalLineSpacing = (int) Config.GRID_IMAGE_SIZE.getHeight()/myNumRows;
        for(int i=0; i<=myNumCols; i++) {
            verticalLines.add(myOffset.getX() + (double) i*verticalLineSpacing);
        }
        for(int i=0; i<=myNumRows; i++) {
            horizontalLines.add(myOffset.getY() + (double) i*horizontalLineSpacing);
        }
    }
    
    public int findGridNumber(Point mouseClick) {
        double x = mouseClick.getX();
        double y = mouseClick.getY();
        int row = 0;
        int col = 0;
        for(int i=0; i<horizontalLines.size() - 1; i++) {
            if ((y > horizontalLines.get(i)) && (y < horizontalLines.get(i+1))) {
                row = i;
                break;
            }
        }
        for(int i=0; i<verticalLines.size() - 1; i++) {
            if ((x > verticalLines.get(i)) && (x < verticalLines.get(i+1))) {
                col = i;
                break;
            }
        }
        
        int gridNumber = row*myNumCols + col;
        return gridNumber;
    }
    
    public List<Double> getVerticalLines(){
        return verticalLines;
    }
    
    public List<Double> getHorizontalLines(){
        return horizontalLines;
    }

    public void setOffset (Point2D offset) {
        myOffset=offset;
    }
    
}
