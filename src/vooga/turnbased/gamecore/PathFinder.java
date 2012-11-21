package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import vooga.turnbased.gameobject.mapobject.MapItemObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MovingMapObject;
import vooga.turnbased.gui.GameWindow;

/**
 * path finding for a MovingMapObject to go to a target position
 * @author rex
 *
 */
public class PathFinder implements Runnable {

	private static final int ATTEMPT_INTERVAL = 60;
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
		findPossiblePath(myStart);
		highlightPath();
		myMovementThread = new Thread(this);
		myMovementThread.start();
	}

	/**
	 * depth first search using heuristics
	 * @param current current position
	 * @return if the path finding is successful
	 */
	protected boolean findPossiblePath(Point current) {
		if (checkVisited(current.x, current.y)) {
			return false;
		}
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
			if (findPossiblePath(p)) {
				return true;
			} else {
				myPath.remove(myPath.size() - 1);
			}
		}
		return false;
	}
	
	/**
	 * check if the point is visited
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return true if the (x, y) position is already visited
	 */
	public boolean checkVisited(int x, int y) {
		if (myVisited[x][y]) {
			return true;
		}
		myVisited[x][y] = true; //mark visited if the point has not yet been visited
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
	public synchronized void run() {
		if (myPath.isEmpty()) {
			return;
		}
		Point previousPoint = myStart;
		Point currentPoint;
		for (int i = 0; i < myPath.size(); i++) {
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
	
	private MapItemObject generatePathIndicator(Point p) {
		return new MapItemObject(0, "NO_ACTION", p, GameWindow
                .importImage("HighlightPath"), myMap);
	}
	
	private void highlightPath() {
		for (Point p: myPath) {
			myMap.addMapObject(p, generatePathIndicator(p));
		}
	}
}
