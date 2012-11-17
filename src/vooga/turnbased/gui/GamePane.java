/**
 * The canvas that paints game objects every for every myDelayTime milliseconds
 * Responds to input events 
 * @author Rex, Vo
 */
package vooga.turnbased.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import util.input.core.Controller;
import vooga.turnbased.gamecore.GameManager;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;

@SuppressWarnings("serial")
public class GamePane extends DisplayPane implements Runnable {

	private GameManager myGameManager;
	private Thread myGameThread;
	private int myDelayTime;

	// InfoPanel infoPanel;

	/**
	 * Constructor of the canvas which displays the game
	 * 
	 * @param gameWindow
	 *            The GameWindow it belongs to
	 */
	public GamePane(GameWindow gameWindow) {
		super(gameWindow);
		myGameThread = new Thread(this);
		myDelayTime = Integer.parseInt(GameWindow.importString("Delay"));
		addKeyListener(this);
		addMouseListener(new GameMouseListener());
		myGameManager = new GameManager(this);
		enableFocus();
		configureInputHandling();
	}

	/**
	 * initialize properties when user switch to game
	 */
	public void initialize() {
		repaint();
		myGameThread.start();
	}

	/**
	 * update game
	 */
	@Override
	public void update(Graphics g) {
		myGameManager.update();
	}

	/**
	 * Paint gameobjects and background to the canvas using double buffering
	 */
	// @Override
	public void paint(Graphics g) {
		Image nextFrameImage = createImage(getSize().width, getSize().height);
		Graphics nextFrameGraphics = nextFrameImage.getGraphics();
		myGameManager.paint(nextFrameGraphics);
		g.drawImage(nextFrameImage, 0, 0, null);
	}

	/**
	 * main game loop
	 */
	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		while (!myGameManager.isOver()) {
			beforeTime = System.currentTimeMillis();
			myGameManager.update();
			repaint();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = myDelayTime - timeDiff;
			if (sleep < 0) {
				sleep = 0;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void configureInputHandling() {
    	// handle actions that shouldn't be passed down to gamemanager
    	}

	/**
	 * event handling when user types anything
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println("Typed " + e.getKeyCode());
	}

	/**
	 * event handling when any key is pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("Pressed " + e.getKeyCode());
		myGameManager.handleKeyPressed(e);
	}

	/**
	 * event handling when any key is released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// System.out.println("Released " + e.getKeyCode());
		myGameManager.handleKeyReleased(e);
	}

	public int getDelayTime() {
		return myDelayTime;
	}

	private class GameMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			myGameManager.handleMouseClicked(e);
		}
	}
}
