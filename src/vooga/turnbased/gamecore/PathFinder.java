package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import vooga.turnbased.gameobject.MapObject;

public class PathFinder {
	
	private List<Point> myDirections;
	private Map<Point, List<MapObject>> myMap;
	private boolean[][] myVisited;
	private Point myStart;
	private Point myEnd;
	private Dimension mySize;
	
	public PathFinder(Map<Point, List<MapObject>> map, Point start, Point target, Point myBottomRightCorner) {
		myMap = map;
		myStart = start;
		myEnd = target;
		myDirections = new ArrayList<Point>();
		mySize = new Dimension(myBottomRightCorner.x, myBottomRightCorner.y);
		myVisited = new boolean[mySize.width][mySize.height];
		System.out.println(depthFirstSearch(myStart));
		for (int i=0; i<myDirections.size(); i++) {
			System.out.println(myDirections.get(i));
		}
	}
	
	public boolean depthFirstSearch(Point current) {
		if (myVisited[current.x][current.y]) {
			return false;
		}
		myVisited[current.x][current.y] = true;
		if (current.equals(myEnd)) {
			return true; //found the path
		}
		PriorityQueue <Point> myOptions = new PriorityQueue<Point>(2, new Comparator<Point>() {
			public int compare(Point a, Point b) {
				return (distance(a, myEnd) - distance(b, myEnd));
			}
		});
		if (validateMove(current, MapMode.LEFT)) {
			myOptions.add(new Point(current.x + MapMode.LEFT.x, current.y + MapMode.LEFT.y));
		}
		if (validateMove(current, MapMode.RIGHT)) {
			myOptions.add(new Point(current.x + MapMode.RIGHT.x, current.y + MapMode.RIGHT.y));
		}
		if (validateMove(current, MapMode.UP)) {
			myOptions.add(new Point(current.x + MapMode.UP.x, current.y + MapMode.UP.y));
		}
		if (validateMove(current, MapMode.DOWN)) {
			myOptions.add(new Point(current.x + MapMode.DOWN.x, current.y + MapMode.DOWN.y));
		}
		while (!myOptions.isEmpty()) {
			Point p = new Point(myOptions.poll());
			myDirections.add(p);
			if (depthFirstSearch(p)) {
				return true;
			} else {
				myDirections.remove(myDirections.size()-1);
			}
		}
		return false;
	}
	
	private int distance(Point a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}
	
	public boolean validateMove(Point position, Point direction) {
		if ((position.x + direction.x >= mySize.width) || (position.x + direction.x < 0)) {
			return false;
		}
		if ((position.y + direction.y >= mySize.height) || (position.y + direction.y < 0)) {
			return false;
		}
		return true;
	}
}
