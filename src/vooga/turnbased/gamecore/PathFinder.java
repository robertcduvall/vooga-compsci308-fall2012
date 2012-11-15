package vooga.turnbased.gamecore;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vooga.turnbased.gameobject.MapObject;

public class PathFinder {
	
	private List<Point> myDirections;
	private Map<Point, List<MapObject>> myMap;
	private Point myStart;
	private Point myEnd;
	
	public PathFinder(Map<Point, List<MapObject>> map, Point start, Point target) {
		myMap = map;
		myStart = start;
		myEnd = target;
		myDirections = new ArrayList<Point>();
	}
	
	public void depthFirstSearch(Point current) {
		if (current.equals(myEnd)) {
			return; //found the path
		}
		
	}
}
