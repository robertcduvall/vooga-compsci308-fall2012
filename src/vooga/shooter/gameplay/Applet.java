package vooga.shooter.gameplay;

import java.awt.*;
import java.awt.event.*;
import vooga.shooter.gameplay.Game;
import javax.swing.*;


/**
 * Creates an applet that be viewed over the web.
 *
 * This applet supports:
 *  - animation via the Timer
 *  - mouse input via the MouseListener
 *  - keyboard input via the KeyListener
 *
 * @author Robert C. Duvall
 */
@SuppressWarnings("serial")
public class Applet extends JApplet {
        // constants
    public static final Dimension SIZE = new Dimension(600, 400);
    public static final int NO_KEY_PRESSED = -1;
    // animate 25 times per second if possible (in milliseconds)
    public static final int ONE_SECOND = 1000;
    public static final int FRAMES_PER_SECOND = 10;

    // state
    private Timer myTimer;
    // user's game to be animated
    private Game myGame;
    // input state
    private int myLastKeyPressed;
    private Point myLastMousePosition;


    /**
     * Initializes the applet --- called by the browser.
     */
    @Override
    public void init ()
    {
        // create container based on dimensions given on website
        //init(new Dimension(Integer.parseInt(getParameter("width")),
        //                   Integer.parseInt(getParameter("height"))));
        // hard code for running within Eclipse
        init(SIZE);
    }


    /**
     * Initializes the applet and starts the animation --- called by main.
     */
    public void init (Dimension size)
    {
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
    public void start ()
     {
        // create the game! 
        myGame = new Game();
        // create a timer to animate the canvas
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND, 
            new ActionListener()
            {
                @Override
                public void actionPerformed (ActionEvent e)
                {
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
    public void stop ()
    {
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
    public void paint (Graphics pen)
    {
        pen.setColor(Color.BLACK);
        pen.fillRect(0, 0, getSize().width, getSize().height);
        myGame.paint(pen);
    }

    
    /**
     * Returns the last key pressed by the player (or -1 if none pressed).
     * 
     * @see java.awt.event.KeyEvent
     */
    public int getLastKeyPressed ()
    {
        return myLastKeyPressed;
    }


    /**
     * Returns the last position of the mouse in the canvas.
     */
    public Point getLastMousePosition ()
    {
        return myLastMousePosition;
    }


    /**
     * Create listeners that will update state based on user input.
     */
    private void setInputListeners ()
    {
        myLastKeyPressed = NO_KEY_PRESSED;
        addKeyListener(new KeyAdapter()
            {
                @Override
                public void keyPressed (KeyEvent e)
                {
                    myLastKeyPressed = e.getKeyCode();
                }
                @Override
                public void keyReleased (KeyEvent e)
                {
                    myLastKeyPressed = NO_KEY_PRESSED;
                }
            });
        myLastMousePosition = new Point();
        addMouseMotionListener(new MouseMotionAdapter()
            {
                @Override
                public void mouseMoved (MouseEvent e)
                {
                    myLastMousePosition = e.getPoint();
                }
            });
        }
}
