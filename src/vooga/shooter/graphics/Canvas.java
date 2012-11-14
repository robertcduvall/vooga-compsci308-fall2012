package vooga.shooter.graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.Timer;


/**
 * 
 * @author David Spruill
 *         Applet initialization code by Robert Duvall
 */
public class Canvas extends JApplet {
    private static final Dimension defaultSize = new Dimension(800, 800);
    private static final int ONE_SECOND = 1000;
    private static final int FRAMES_PER_SECOND = 30;
    private Timer myTimer;
    
    /**
     * Initializes the applet --- called by the browser.
     */
    public void init() {
        init(defaultSize);
    }

    /**
     * Initilizes the applet, but is called by the main method
     * 
     * @param size the window size
     */
    public void init(Dimension size) {
        // set dimensions for animation area
        // note, applet's size is not actually set until after this method
        setSize(size);
        setPreferredSize(size);
        // set applet to receive user input
        
        // this method doesn't exist. please fix this!
        // setInputListeners();
        
        setFocusable(true);
        requestFocus();
    }

    /**
     * Starts the applet's action, i.e., starts the animation.
     */
    @Override
    public void start() {

        // create a timer to animate the canvas
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND, 
            new ActionListener()
            {
                @Override
                public void actionPerformed (ActionEvent e)
                {
                    // myGame doesn't exist! please fix
                    // myGame.update();
                    
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
     * Called by Java to paint the frame
     * 
     * @param g The graphics object passed by Java, will be cast as
     *        SpecialGraphics
     */
    public void paint(Graphics g) {

    }

    /**
     * Called at regular intervals set up by the action listener instantiated in
     * the start method.
     */
    public void update() {

    }
}
