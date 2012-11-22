package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import vooga.turnbased.gameobject.mapobject.MapItemObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapObstacleObject;
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
	private List<MapObject> myHighlightObjects;
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
		myHighlightObjects = new ArrayList<MapObject>();
		mySize = new Dimension(myBottomRightCorner.x, myBottomRightCorner.y);
		myVisited = new boolean[mySize.width][mySize.height];
		myCancelMovement = false;
		if (!findPossiblePath(myStart)) {
			System.out.println("path not found!");
			return;
		}
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
						return Double.compare(a.distance(myEnd), b.distance(myEnd));
					}
				});
		if (validateMove(current, MapMode.LEFT)) {
			myOptions.add(translatePoint(current, MapMode.LEFT));
		}
		if (validateMove(current, MapMode.RIGHT)) {
			myOptions.add(translatePoint(current, MapMode.RIGHT));
		}
		if (validateMove(current, MapMode.UP)) {
			myOptions.add(translatePoint(current, MapMode.UP));
		}
		if (validateMove(current, MapMode.DOWN)) {
			myOptions.add(translatePoint(current, MapMode.DOWN));
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
	
	private Point translatePoint(Point a, Point b) {
		return new Point(a.x + b.x, a.y + b.y);
	}
	
	private void shortestPath(Point start) {
		LinkedList<Point> bfsQueue = new LinkedList<Point>();
		bfsQueue.add(start);
		while (bfsQueue.size() != 0) {
			if (validateMove(start, MapMode.LEFT)) {
				bfsQueue.add(translatePoint(start, MapMode.LEFT));
			}
		}
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
	 * check if the player can move to the tile at the coordinate x, y
	 * @param x x-coordinate in the current map
	 * @param y y-coordinate in the current map
	 * @return if the player can move to the tile at the coordinate x, y
	 */
	protected boolean canMoveTo(int x, int y) {
		for (MapObject m: myMap.getSpritesOnTile(x, y)) {
			if (m instanceof MapObstacleObject) {
				return false;
			}
		}
		return true;
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
		if (!canMoveTo(position.x + direction.x, position.y + direction.y)) {
			return false;
		}
		return true;
	}
	
	/**
	 * stop the moving process and the highlighted path disappeared
	 */
	public void stop() {
		for (MapObject m: myHighlightObjects) {
			m.setVisible(false);
		}
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
			if (myCancelMovement) {
				break;
			}
			myMap.checkCollision(myMovingObject, direction);
		}
	}
	
	private MapItemObject generatePathIndicator(Point p) {
		return new MapItemObject(0, "NO_ACTION", p, GameWindow
                .importImage("HighlightPath"), myMap);
	}
	
	private void highlightPath() {
		for (Point p: myPath) {
			MapObject m = generatePathIndicator(p);
			myMap.addMapObject(p, m);
			myHighlightObjects.add(m);
		}
	}
}
