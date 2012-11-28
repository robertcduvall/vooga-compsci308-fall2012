package util.particleEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JApplet;
import javax.swing.Timer;
import vooga.shooter.gameplay.Game;


/**
 * Some code borrowed from Canvas class in graphics package of shooter game
 * package (which, in turn, was borrowed from Professor Duvall's code).
 * 
 * This JApplet updates a set of particle engines that are created by a "Tester"
 * class (e.g. DensityTester) which implements the abstract class
 * ParticleEngineTestingUnit. For example, The DensityTester stores a List of
 * particle engines which have the same parameters except 
 * for density, which is varied across the particle engines.
 * 
 * To determine which parameter or feature is tested, the user specifies which
 * type of ParticleEngineTestingUnit to instantiate in the
 * setUpParticleEngines method (see below). The "Tester" classes are not limited
 * to testing a single parameter. The user can create
 * a "Tester" class that holds any number of particle engine objects with any
 * variations of parameters desired.
 * 
 * ****Work-in-progress: This class is subject to change.***
 * 
 * @author Kathleen, JApplet code borrowed from Professor Duvall
 *      edited by David Spruill
 * 
 */

public class ParticleEngineTester extends JApplet {
    private static final Dimension defaultSize = new Dimension(800, 800);
    private static final int ONE_SECOND = 1000;
    private static final int FRAMES_PER_SECOND = 30;
    private Timer myTimer;
    private Game myGame;
    private List<ParticleEngine> myParticleEngines = new ArrayList<ParticleEngine>();
    private List<ParticleSystem> mySystems = new ArrayList<ParticleSystem>();
    private int myLastKeyPressed;
    private int NO_KEY_PRESSED = -1;
    private Font font;
    private long lastTime=0;
    private Point position = new Point(defaultSize.width/2,defaultSize.height/2);

    /**
     * Initializes the applet --- called by the browser.
     */
    @Override
    public void init() {
        init(defaultSize);
        font = new Font("Times New Roman", Font.PLAIN, 12);
    }

    /**
     * Initilizes the applet, but is called by the main method
     * 
     * @param size the window size
     */
    public void init (Dimension size) {
        setSize(size);
        setPreferredSize(size);

        setFocusable(true);
        requestFocus();
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                myLastKeyPressed = e.getKeyCode();
                manageEngine(myLastKeyPressed);
            }

            @Override
            public void keyReleased (KeyEvent e) {
                myLastKeyPressed = NO_KEY_PRESSED;
            }
        });

    }

    /**
     * Allows for the user to alter the simulation as it goes.
     * It's kinda crude in that it uses if statements, but keep
     * in mind that this class' sole use is for testing...
     * 
     * @param myLastKeyPressed the last key pressed
     */
    private void manageEngine (int myLastKeyPressed) {

        if (myLastKeyPressed == KeyEvent.VK_A)
            mySystems.add(new Explosion());
        if (myLastKeyPressed == KeyEvent.VK_S)
            mySystems.add(new Trail());
        if (myLastKeyPressed == KeyEvent.VK_DOWN)
            for(ParticleSystem p:mySystems) {p.move(new Point(0,5)); p.setVelocity(new Point(0,3));}
        if (myLastKeyPressed == KeyEvent.VK_UP)
            for(ParticleSystem p:mySystems) {p.move(new Point(0,-5)); p.setVelocity(new Point(0,-3));}
        if (myLastKeyPressed == KeyEvent.VK_RIGHT)
            for(ParticleSystem p:mySystems) {p.move(new Point(5,0)); p.setVelocity(new Point(-3,0));}
        if (myLastKeyPressed == KeyEvent.VK_LEFT)
            for(ParticleSystem p:mySystems) {p.move(new Point(-5,0)); p.setVelocity(new Point(3,0));}

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
     * Called by Java to paint the frame.  I've found that by 
     * drawing the frame onto a buffer and then drawing that buffer
     * all at once, much smoother graphics can be obtained.
     * 
     * @param g The graphics object passed by Java, will be cast as
     *        SpecialGraphics
     */
    @Override
    public void paint (Graphics g) {
        BufferedImage buff = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) buff.getGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getSize().width, getSize().height);
        
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.drawString(""+(1/((System.currentTimeMillis()-lastTime)/1000.0f))+" fps",100,100);
        lastTime = System.currentTimeMillis();

        for (ParticleSystem e : mySystems)
            e.draw(g2d);
        
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(buff, null, 0, 0);
    }

    /**
     * Called at regular intervals set up by the action listener instantiated in
     * the start method.
     */
    public void update () {
        for (ParticleSystem e : mySystems)
            e.update();
    }
}
