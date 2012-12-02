package vooga.turnbased.gamecore.graphutility;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PathFinder {
    private List<Point> myPath;
    private boolean myIsMultiDestination;
    
    public PathFinder() {
        myPath = new ArrayList<Point>();
        myIsMultiDestination = false;
    }
    
    protected void setPath(List<Point> path) {
        myPath = path;
    }
    
    public void setMultiDestination(boolean isMultiDestination) {
        myIsMultiDestination = isMultiDestination;
    }
    
    protected boolean pathIsEmpty() {
        return myPath.isEmpty();
    }
    
    public List<Point> getImmutablePath() {
        return Collections.unmodifiableList(myPath);
    }
    
    protected Point getPathUsingIndex(int index) {
        return myPath.get(index);
    }
    
}
