package vooga.turnbased.gamecore.pathutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

public class BreadthFirstSearch extends PathSearch{
    
    public BreadthFirstSearch (Point start, Point end, Dimension size) {
        super(start, end, size);
    }
    public boolean findPath (Point start) {
        LinkedList<Point> bfsQueue = new LinkedList<Point>();
        bfsQueue.add(start);
        while (bfsQueue.size() != 0) {
            //if (validateMove(start, MapMode.LEFT)) {
                //bfsQueue.add(translatePoint(start, MapMode.LEFT));
            //}
        }
        return false;
    }

}
