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
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import vooga.turnbased.gameobject.BattleObject;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.TestMonster;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.sprites.Sprite;

public class GameManager implements Observer {

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
		myCurrentGameMode = new MapMode(this);
		// myCurrentGameMode.initializeMap();
	}

	private void generateHardcodedSprites() {
		Sprite s = new Sprite();

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

	/**
	 * Creates a new Battle by constructing a new BattleMode. Currently the
	 * monsters used are hardcoded.
	 */
	public void newBattle() {
		// changes these from being hardcoded later.
		ArrayList<BattleObject> team1 = new ArrayList<BattleObject>();
		BattleObject playerChar = new TestMonster(0, 1, 1, 3);
		team1.add(playerChar);
		ArrayList<BattleObject> team2 = new ArrayList<BattleObject>();
		BattleObject otherChar = new TestMonster(1, 1, 1, 3);
		team2.add(otherChar);
		new BattleMode(this, team1, team2);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	}

	public void handleKeyPressed(KeyEvent e) {
		myCurrentGameMode.handleKeyPressed(e);
	}

	public void handleKeyReleased(KeyEvent e) {
		myCurrentGameMode.handleKeyReleased(e);
	}
	
	public void handleMouseClicked(MouseEvent e) {
		myCurrentGameMode.handleMouseClicked(e);
	}

	public Dimension getPaneDimension() {
		return myGamePane.getSize();
	}

	public int getDelayTime() {
		return myGamePane.getDelayTime();
	}
}
