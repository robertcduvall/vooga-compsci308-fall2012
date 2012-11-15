/**
 * Manage Game Sprites, and change the game modes when certain events are
 * triggered
 * Check gameover conditions and goals of games
 * 
 * @author rex, Vo
 */
package vooga.turnbased.gamecore;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import vooga.turnbased.gameobject.BattleObject;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.MapObject;
import vooga.turnbased.gameobject.TestMonster;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.sprites.Sprite;

//public class GameManager implements Observer {
public class GameManager {

	private final GamePane myGamePane;
	private GameMode myCurrentGameMode;
	// private Factory myFactory;
	// private MapObject myPlayer;
	private final boolean isOver;
	private HashMap<Integer, Sprite> mySprites;

	/**
	 * Constructor of GameManager
	 * 
	 * @param gameCanvas
	 *            The GameCanvas it paints to
	 */
	public GameManager(GamePane gameCanvas) {
		myGamePane = gameCanvas;
		isOver = false;
		// mySprites =
		// myFactory.initializeSprites(myGameCanvas.getInitialMapFile());
		mySprites = new HashMap<Integer, Sprite>();
		generateHardcodedSprites();
		myCurrentGameMode = new MapMode(this, MapObject.class);
		// myCurrentGameMode.initializeMap();
	}

	private void generateHardcodedSprites() { // factory will do this job
												// eventually...
		Sprite s = new Sprite();
		s.addGameObject(new TestMonster(0, GameEvent.NO_ACTION, 1, 1, 3));

		mySprites.put(s.getID(), s);

		s = new Sprite();
		s.addGameObject(new TestMonster(1, GameEvent.NO_ACTION, 1, 1, 3));

		mySprites.put(s.getID(), s);

	}

	public ArrayList<GameObject> getModesSprites(Class c) {
		ArrayList<GameObject> modeObjects = new ArrayList<GameObject>();
		for (Sprite s : mySprites.values()) {
			modeObjects.add(s.getObject(c));
		}
		return modeObjects;
	}

	public boolean isOver() {
		return isOver;
	}

	public void update() {
		myCurrentGameMode.update();
	}

	/**
	 * paint the images to the buffer
	 * 
	 * @param g
	 *            the Graphics object of the offScreenImage
	 */
	public void paint(Graphics g) {
		myCurrentGameMode.paint(g);
	}

	public void handleEvent(GameEvent eventName, ArrayList<Integer> myInvolvedIDs) {
		switch (eventName) {
		case NO_ACTION:
			break;
		case MAP_COLLISION:
			if(myInvolvedIDs.size() >= 2) {
				new BattleMode(this, BattleObject.class);
			}
			break;
		}
	}

	// @Override
	// public void update(Observable arg0, Object arg1) {
	// }

	public void handleKeyPressed(KeyEvent e) {
		myCurrentGameMode.handleKeyPressed(e);
	}

	public void handleKeyReleased(KeyEvent e) {
		myCurrentGameMode.handleKeyReleased(e);
	}

	public Dimension getPaneDimension() {
		return myGamePane.getSize();
	}

	public int getDelayTime() {
		return myGamePane.getDelayTime();
	}

	public enum GameEvent {
		MAP_COLLISION, NO_ACTION
	}
}
