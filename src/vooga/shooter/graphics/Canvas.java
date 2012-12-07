package vooga.shooter.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JApplet;
import javax.swing.Timer;
import util.input.core.KeyboardController;
import util.input.core.MouseController;


/**
 * 
 * @author David Spruill, Kathleen Lan
 *         Applet initialization code by Robert Duvall
 */
public class Canvas extends JApplet {
    private static final Dimension DEFAULT_SIZE = new Dimension(600, 400);
    private static final int ONE_SECOND = 1000;
    private static final int FRAMES_PER_SECOND = 20;
    private Timer myTimer;
    private DrawableComponent myScreen;

    /**
     * This is the constructor for the applet
     * 
     * @param game an object of the game
     */
    public Canvas (DrawableComponent game) {
        myScreen = game;
        init();
    }

    /**
     * Initializes the applet --- called by the browser.
     */
    public void init () {
        init(DEFAULT_SIZE);
    }

    /**
     * Initilizes the applet, but is called by the main method
     *
     * @param size the window size
     */
    public void init (Dimension size) {
        // set dimensions for animation area
        // note, applet's size is not actually set until after this method
        setSize(size);
        setPreferredSize(size);
        // set applet to receive user input

        // this method doesn't exist. please fix this!
        setInputListeners();

        setFocusable(true);
        requestFocus();
    }

    /**
     * Sets up input listeners/controllers.
     */
    private void setInputListeners () {
        addMouseMotionListener(new MouseController(this));
        addKeyListener(new KeyboardController(this));
        myScreen.setKeyboardListener(getKeyListeners()[0]);
        myScreen.setMouseListener(getMouseMotionListeners()[0]);
    }

    /**
     * Starts the applet's action, i.e., starts the animation.
     */
    @Override
    public void start () {

        // create a timer to animate the canvas
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND,
                new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent e) {
                        update();

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
     * Called by Java to paint the frame
     * 
     * @param g The graphics object passed by Java
     */
    public void paint (Graphics g) {
        BufferedImage buff = new BufferedImage(getSize().width,
                getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) buff.getGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getSize().width, getSize().height);
        myScreen.paint(g2d);
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(buff, null, 0, 0);
    }

    /**
     * Called at regular intervals set up by the action listener instantiated in
     * the start method.
     */
    public void update () {
        myScreen.update();
    }

    public void setGame (DrawableComponent g) {
        myScreen = g;
    }
}
