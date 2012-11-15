package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gameobject.MapPlayerObject;
import vooga.turnbased.gameobject.MapTileObject;
import vooga.turnbased.gameobject.MovingMapObject;
import vooga.turnbased.gui.GameWindow;

/**
 * Map Mode on which players move around and interact with other game objects
 * for now, the mastermind behind map mode...does just about everything
 * 
 * @author Tony, Rex
 **/
public class MapMode extends GameMode {
	public static final Point UP = new Point(0, -1);
	public static final Point RIGHT = new Point(1, 0);
	public static final Point DOWN = new Point(0, 1);
	public static final Point LEFT = new Point(-1, 0);
	private final int ID = 0;
	private boolean myIsFixedPlayer;
	private int myNumDisplayRows;
	private int myNumDisplayCols;
	private HashMap<Point, List<MapObject>> mySprites;
	private MapPlayerObject myPlayer;
	private Point myBottomRightCorner;
	private Point myOrigin;
	private int myCurrentTileWidth;
	private int myCurrentTileHeight;
	private Rectangle myCurrentCamera;

	public MapMode(GameManager gm) {
		super(gm);
		myNumDisplayRows = Integer.parseInt(GameWindow
				.importString("CameraHeight"));
		myNumDisplayCols = Integer.parseInt(GameWindow
				.importString("CameraWidth"));
		myBottomRightCorner = new Point(20, 30);
		addHardcodedSprites();
	}

	// only for testing purposes
	public void addHardcodedSprites() {
		mySprites = new HashMap<Point, List<MapObject>>();
		for (int i = 0; i < myBottomRightCorner.x; i++) {
			for (int j = 0; j < myBottomRightCorner.y; j++) {
				Point p = new Point(i, j);
				addGameObject(
						p, new MapTileObject(ID, p, GameWindow
								.importImage("GrassImage")));
			}
		}
		Point center = new Point(7, 5);
		myPlayer = new MapPlayerObject(ID, center,
				GameWindow.importImage("PlayerImage"));
		addGameObject(center, myPlayer);
		
		center = new Point(1, 1);
		MovingMapObject test1 = new MovingMapObject(ID, center,
				GameWindow.importImage("something"));
		addGameObject(center, test1);
	}

	public void addGameObject(Point p, MapObject s) {
		if (mySprites.keySet().contains(p)) {
			mySprites.get(p).add(s);
		} else {
			ArrayList<MapObject> spriteList = new ArrayList<MapObject>();
			spriteList.add(s);
			mySprites.put(p, spriteList);
		}
	}

	@Override
	public void paint(Graphics g) {
		int playerX = 0;
		int playerY = 0;
		// foreach sprite: s.paint(g);
		for (int i = myCurrentCamera.x; i < myCurrentCamera.getMaxX(); i++) {
			for (int j = myCurrentCamera.y; j < myCurrentCamera.getMaxY(); j++) {
				List<MapObject> spritesOnTile = getSpritesOnTile(i, j);
				int xOffset = (i - (myCurrentCamera.x)) * myCurrentTileWidth 
						+ myOrigin.x;
				int yOffset = (j - (myCurrentCamera.y)) * myCurrentTileHeight
						+ myOrigin.y;
				Image background = GameWindow.importImage("TileBackground");
				g.drawImage(background, xOffset, yOffset, myCurrentTileWidth,
						myCurrentTileHeight, null);
				if (spritesOnTile != null) {
					for (MapObject s : spritesOnTile) {
						s.paint(g, xOffset, yOffset, myCurrentTileWidth,
								myCurrentTileHeight);
						if (s.equals(myPlayer)) {
							playerX = xOffset;
							playerY = yOffset;
						}
					}
				}
			}
		}
		myPlayer.paint(g, playerX, playerY, myCurrentTileWidth,
				myCurrentTileHeight);
	}

	public void updateTileInfo() {
		myCurrentTileWidth = getGM().getPaneDimension().width
				/ myNumDisplayCols;
		myCurrentTileHeight = getGM().getPaneDimension().height
				/ myNumDisplayRows;
		myOrigin = initializeOrigin();
	}

	/**
	 * move the camera according to player's movement
	 * do not move when player reaches the edge
	 * There are 2 rows and columns of margin for smooth movement 
	 */
	private void updateCameraPosition() {
		Point displacement = myPlayer
				.calcScreenDisplacement(myCurrentTileWidth,	myCurrentTileHeight);
		Point topLeftCoord = calculateTopLeftCoordinate();
		if (topLeftCoord.x * myCurrentTileWidth + myPlayer.getDirection().x < 0) {
			topLeftCoord.x = 0;  //player near the left boundary
			displacement.x = 0;  //screen fixed when player moves to the edge
		}
		else if ((topLeftCoord.x + myNumDisplayCols) * myCurrentTileWidth +
				myPlayer.getDirection().x > myBottomRightCorner.x * myCurrentTileWidth) {
			topLeftCoord.x = myBottomRightCorner.x - myNumDisplayCols;
			displacement.x = 0;
		}
		if (topLeftCoord.y * myCurrentTileHeight + myPlayer.getDirection().y < 0) {
			topLeftCoord.y = 0;  //player near the top boundary
			displacement.y = 0;
		}
		else if ((topLeftCoord.y + myNumDisplayRows) * myCurrentTileHeight +
				myPlayer.getDirection().y > myBottomRightCorner.y * myCurrentTileHeight) {
			topLeftCoord.y = myBottomRightCorner.y - myNumDisplayRows; 
			displacement.y = 0;
		}
		myCurrentCamera = new Rectangle(topLeftCoord.x - 1, topLeftCoord.y - 1,
				myNumDisplayCols + 2, myNumDisplayRows + 2);  
		myOrigin = changeOriginForPlayer(displacement);
	}
	
	/**
	 * iterate through the map and update MapObjects at each position
	 */
	public void updateMapObjects() {
		for (Point p : mySprites.keySet()) {
			for (MapObject s : getSpritesOnTile(p.x, p.y)) {
				s.update(getGM().getDelayTime());
			}
		}
	}

	/**
	 * change the top left corner of the screen when the player moves
	 * @param displacement the displacement of the screen
	 * @return new origin point
	 */
	private Point changeOriginForPlayer(Point displacement) {
		Point result = new Point( myOrigin.x + displacement.x, myOrigin.y + displacement.y);
		if (result.x == 0) { //screen movement done!
			result = initializeOrigin();
		}
		return result;
	}

	/**
	 * calculate top left corner coordinate of the grid
	 * 
	 * @return top-left coordinate
	 */
	private Point calculateTopLeftCoordinate() {
		myIsFixedPlayer = true;
		int x = myPlayer.getPreviousLocation().x;
		int y = myPlayer.getPreviousLocation().y;
		x -= (myNumDisplayCols - 1) / 2;
		y -= (myNumDisplayRows - 1) / 2;
		return new Point(x, y);
	}

	private List<MapObject> getSpritesOnTile(int i, int j) {
		return mySprites.get(new Point(i, j));
	}

	public void moveSprite(MovingMapObject s, Point dir) {
		Point dest = myPlayer.getLocation(dir);
		if (dest.x >= 0 && dest.x < myBottomRightCorner.x && dest.y >= 0
				&& dest.y < myBottomRightCorner.y) {
			Point oldCoord = s.getLocation();

			if (mySprites.get(oldCoord).contains(s)) {
				mySprites.get(oldCoord).remove(s);
				addGameObject(dest, s);
				s.setLocation(dest);
				s.setDirection(dir); //start moving in update() when direction is set
			}
		}
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		// foreach sprite: s.handleKeyPressed(e); s.update();
		int keyCode = e.getKeyCode();
		if (myPlayer.isMoving()) {
			return;
		}
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
			moveSprite(myPlayer, LEFT);
			break;
		case KeyEvent.VK_UP:
			moveSprite(myPlayer, UP);
			break;
		case KeyEvent.VK_RIGHT:
			moveSprite(myPlayer, RIGHT);
			break;
		case KeyEvent.VK_DOWN:
			moveSprite(myPlayer, DOWN);
			break;
		}
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {

	}

	@Override
	public void update() {
		updateTileInfo();
		updateCameraPosition();
		updateMapObjects();
	}
	
	private Point initializeOrigin() {
		return new Point(-myCurrentTileWidth, -myCurrentTileHeight);
	}
}
