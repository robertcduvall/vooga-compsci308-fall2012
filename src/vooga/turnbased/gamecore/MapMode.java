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
						p,
						new MapTileObject(ID, p, GameWindow
								.importImage("GrassImage"), myCurrentCamera));
			}
		}
		Point center = new Point(7, 5);
		myPlayer = new MapPlayerObject(ID, center,
				GameWindow.importImage("PlayerImage"), myCurrentCamera);
		addGameObject(center, myPlayer);
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
		myOrigin = new Point(-myCurrentTileWidth, -myCurrentTileHeight);
	}

	private void updateCameraPosition() {
		Point playerCoord = myPlayer.getPreviousLocation();
		myCurrentCamera = new Rectangle(playerCoord.x - (myNumDisplayCols - 1)
				/ 2 - 1, playerCoord.y - (myNumDisplayRows - 1) / 2 - 1,
				myNumDisplayCols + 2, myNumDisplayRows + 2);

		Point displacement = myPlayer
				.calcScreenDisplacement(myCurrentTileWidth,
						myCurrentTileHeight, getGM().getDelayTime());
		myOrigin.x += displacement.x;
		myOrigin.y += displacement.y;
		for (Point p : mySprites.keySet()) {
			for (MapObject s : getSpritesOnTile(p.x, p.y)) {
				s.update(getGM().getDelayTime());
			}
		}
	}

	private List<MapObject> getSpritesOnTile(int i, int j) {
		return mySprites.get(new Point(i, j));
	}

	public void moveSprite(MapObject s, Point dest) {
		if (dest.x >= 0 && dest.x < myBottomRightCorner.x && dest.y >= 0
				&& dest.y < myBottomRightCorner.y) {
			Point oldCoord = s.getLocation();

			if (mySprites.get(oldCoord).contains(s)) {
				mySprites.get(oldCoord).remove(s);
				addGameObject(dest, s);
				s.setLocation(dest);
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
			moveSprite(myPlayer, myPlayer.getLocation(LEFT));
			myPlayer.setDirection(LEFT);
			break;
		case KeyEvent.VK_UP:
			moveSprite(myPlayer, myPlayer.getLocation(UP));
			myPlayer.setDirection(UP);
			break;
		case KeyEvent.VK_RIGHT:
			moveSprite(myPlayer, myPlayer.getLocation(RIGHT));
			myPlayer.setDirection(RIGHT);
			break;
		case KeyEvent.VK_DOWN:
			moveSprite(myPlayer, myPlayer.getLocation(DOWN));
			myPlayer.setDirection(DOWN);
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
	}
}