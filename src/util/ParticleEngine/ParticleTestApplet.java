package util.ParticleEngine;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Creates an applet that be viewed over the web.
 * 
 * This applet supports:
 * - animation via the Timer
 * - mouse input via the MouseListener
 * - keyboard input via the KeyListener
 * 
 * @author Robert C. Duvall - with a little additions from David Spruill to make
 *         this situation-specific (If there is any bad code here - in all
 *         liklihood it's mine and not Professor Duvall)
 */
@SuppressWarnings("serial")
public class ParticleTestApplet extends JApplet {
    // constants
    public static final Dimension SIZE = new Dimension(600, 600);
    // animate 25 times per second if possible (in milliseconds)
    public static final int ONE_SECOND = 1000;
    public static final int FRAMES_PER_SECOND = 20;

    // state
    private Timer myTimer;
    // user's game to be animated
    private ParticleEngine engine;
    // input state
    private String myLastKeyPressed;
    private Point myLastMousePosition;

    /**
     * Initializes the applet --- called by the browser.
     */
    @Override
    public void init () {
        // create container based on dimensions given on website
        // init(new Dimension(Integer.parseInt(getParameter("width")),
        // Integer.parseInt(getParameter("height"))));
        // hard code for running within Eclipse
        init(SIZE);
    }

    /**
     * Initializes the applet and starts the animation --- called by main.
     */
    public void init (Dimension size) {
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
    public void start () {
        Image particleImage = new ImageIcon(
                DensityTester.class.getResource("explosion.png")).getImage();
        engine = new ParticleEngine(10000, particleImage, new Point(300, 300), new Point(0,-1), 20, 200000);
        // create a timer to animate the canvas
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND,
                new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent e) {
                        
                        engine.update();
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
    public void stop () {
        myTimer.stop();
    }

    /**
     * Never called by you directly, instead called by Java runtime
     * when area of screen covered by this container needs to be
     * displayed (i.e., creation, uncovering, change in status)
     * 
     * @param pen smart pen to draw on the canvas with
     */
    @Override
    public void paint (Graphics pen) {
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getSize().width, getSize().height);
        engine.draw((Graphics2D) pen);
    }

    /**
     * Returns the last key pressed by the player (or "" if none pressed).
     */
    public String getLastKeyPressed () {
        return myLastKeyPressed;
    }

    /**
     * Returns the last position of the mouse in the canvas.
     */
    public Point getLastMousePosition () {
        return myLastMousePosition;
    }

    /**
     * Create listeners that will update state based on user input.
     */
    private void setInputListeners () {
        myLastKeyPressed = "";
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                myLastKeyPressed = KeyEvent.getKeyText(e.getKeyCode());
            }

            @Override
            public void keyReleased (KeyEvent e) {
                myLastKeyPressed = "";
            }
        });
        myLastMousePosition = new Point();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
                myLastMousePosition = e.getPoint();
            }
        });
    }
}
