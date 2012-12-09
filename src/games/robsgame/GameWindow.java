package games.robsgame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * 
 * @author Robert Bruce
 * This code isn't the best & is from the first assignment.
 * I just wanted to add it to the arcade so we had more
 * games to play.
 */
@SuppressWarnings("serial")
public class GameWindow extends JApplet implements Settings {
	// state
	private Timer myTimer;
	// user's game to be animated
	private TGame myGame;
	// input state
	public int myLastKeyPressed;
	private Set<Integer> currentlyPressed;
	private Point myLastMousePosition;

	/**
	 * Initializes the applet --- called by the browser.
	 */
	@Override
	public void init() {
		// create container based on dimensions given on website
		// init(new Dimension(Integer.parseInt(getParameter("width")),
		// Integer.parseInt(getParameter("height"))));
		// hard code for running within Eclipse
		init(WINDOW_SIZE);
	}

	/**
	 * Initializes the applet and starts the animation --- called by main.
	 */
	public void init(Dimension size) {
		// set dimensions for animation area
		// note, applet's size is not actually set until after this method
		setSize(size);
		setPreferredSize(size);
		// set applet to receive user input
		setInputListeners();
		setFocusable(true);
		requestFocus();
	}

	/**
	 * Starts the applet's action, i.e., starts the animation.
	 */
	@Override
	public void start() {
		// create the game!
		myGame = new TGame(this);
		// Create the input arraylist to prevent null-pointer exceptions
		currentlyPressed = new HashSet<Integer>();
		// create a timer to animate the canvas
		myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						myGame.update();
						// indirectly causes paint to be called
						repaint();
					}
				});
		myTimer.start();
	}

	/**
	 * Stops the applet's action, i.e., the animation.
	 */
	@Override
	public void stop() {
		myTimer.stop();
	}

	/**
	 * Never called by you directly, instead called by Java runtime when area of
	 * screen covered by this container needs to be displayed (i.e., creation,
	 * uncovering, change in status)
	 * 
	 * @param pen
	 *            smart pen to draw on the canvas with
	 */
	@Override
	public void paint(Graphics pen) {
		pen.setColor(Color.WHITE);
		pen.fillRect(0, 0, getSize().width, getSize().height);
		myGame.paint((Graphics2D) pen);
	}

	/**
	 * Returns the last key pressed by the player (or -1 if none pressed).
	 * 
	 * @see java.awt.event.KeyEvent
	 */
	public int getLastKeyPressed() {
		return myLastKeyPressed;
	}

	/**
	 * Returns the last position of the mouse in the canvas.
	 */
	public Point getLastMousePosition() {
		return myLastMousePosition;
	}

	/**
	 * Create listeners that will update state based on user input.
	 */
	private void setInputListeners() {
		myLastKeyPressed = NO_KEY_PRESSED;
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				myLastKeyPressed = e.getKeyCode();
				currentlyPressed.add(myLastKeyPressed);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				currentlyPressed.remove(e.getKeyCode());
				myLastKeyPressed = NO_KEY_PRESSED;
			}
		});
		myLastMousePosition = new Point();
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				myLastMousePosition = e.getPoint();
			}
		});
	}

	public Set<Integer> getPressedArray() {
		return currentlyPressed;
	}
}
