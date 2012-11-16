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
import vooga.turnbased.gameobject.MovingMapObject;

/**
 * path finding for a MovingMapObject to go to a target position
 * @author rex
 *
 */
public class PathFinder implements Runnable {

	private static final int ATTEMPT_INTERVAL = 30;
	private List<Point> myPath;
	private MapMode myMap;
	private boolean[][] myVisited;
	private boolean myCancelMovement;
	private Point myStart;
	private Point myEnd;
	private Dimension mySize;
	private Thread myMovementThread;
	private MovingMapObject myMovingObject;

	/**
	 * constructor
	 * @param map MapMode on which the path finding is applied
	 * @param object MapObject to be moved
	 * @param target target position
	 * @param myBottomRightCorner bottom right corner of the entire map
	 */
	public PathFinder(MapMode map, MovingMapObject object, Point target,
			Point myBottomRightCorner) {
		myMap = map;
		myMovingObject = object;
		myStart = object.getLocation();
		myEnd = target;
		myPath = new ArrayList<Point>();
		mySize = new Dimension(myBottomRightCorner.x, myBottomRightCorner.y);
		myVisited = new boolean[mySize.width][mySize.height];
		myCancelMovement = false;
		depthFirstSearch(myStart);
		myMovementThread = new Thread(this);
		myMovementThread.start();
	}

	/**
	 * depth first search using heuristics
	 * @param current current position
	 * @return if the path finding is successful
	 */
	public boolean depthFirstSearch(Point current) {
		if (myVisited[current.x][current.y]) {
			return false;
		}
		myVisited[current.x][current.y] = true;
		if (current.equals(myEnd)) {
			return true; // found the path
		}
		PriorityQueue<Point> myOptions = new PriorityQueue<Point>(2,
				new Comparator<Point>() {
					public int compare(Point a, Point b) {
						return (distance(a, myEnd) - distance(b, myEnd));
					}
				});
		if (validateMove(current, MapMode.LEFT)) {
			myOptions.add(new Point(current.x + MapMode.LEFT.x, current.y
					+ MapMode.LEFT.y));
		}
		if (validateMove(current, MapMode.RIGHT)) {
			myOptions.add(new Point(current.x + MapMode.RIGHT.x, current.y
					+ MapMode.RIGHT.y));
		}
		if (validateMove(current, MapMode.UP)) {
			myOptions.add(new Point(current.x + MapMode.UP.x, current.y
					+ MapMode.UP.y));
		}
		if (validateMove(current, MapMode.DOWN)) {
			myOptions.add(new Point(current.x + MapMode.DOWN.x, current.y
					+ MapMode.DOWN.y));
		}
		while (!myOptions.isEmpty()) {
			Point p = new Point(myOptions.poll());
			myPath.add(p);
			if (depthFirstSearch(p)) {
				return true;
			} else {
				myPath.remove(myPath.size() - 1);
			}
		}
		return false;
	}

	/**
	 * find distance between two points in grid system
	 * @param a first point
	 * @param b second point
	 * @return distance in grid system
	 */
	private int distance(Point a, Point b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	/**
	 * check if a movement is possible, or will get out of boundaries
	 * @param position original position of the object
	 * @param direction direction it takes
	 * @return if a movement is possible
	 */
	public boolean validateMove(Point position, Point direction) {
		if ((position.x + direction.x >= mySize.width)
				|| (position.x + direction.x < 0)) {
			return false;
		}
		if ((position.y + direction.y >= mySize.height)
				|| (position.y + direction.y < 0)) {
			return false;
		}
		return true;
	}
	
	/**
	 * stop the moving process
	 */
	public void stop() {
		myCancelMovement = true;
	}

	/**
	 * loop that moves the object
	 */
	@Override
	public void run() {
		if (myPath.isEmpty()) {
			return;
		}
		Point previousPoint = myPath.get(0);
		Point currentPoint;
		for (int i = 1; i < myPath.size(); i++) {
			if (myCancelMovement) {
				break;
			}
			currentPoint = myPath.get(i);
			Point direction = new Point(currentPoint.x - previousPoint.x,
					currentPoint.y - previousPoint.y);
			previousPoint = currentPoint;
			while (myMovingObject.isMoving()) {
				try {
					Thread.sleep(ATTEMPT_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			myMap.moveSprite(myMovingObject, direction);
		}
	}
}
