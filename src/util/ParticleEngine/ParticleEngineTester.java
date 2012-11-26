package util.ParticleEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.Timer;

import vooga.shooter.gameplay.Game;

/**
 * Some code borrowed from Canvas class in graphics package of shooter game package (which, in turn, was borrowed from Professor Duvall's code).
 * 
 *  This JApplet updates a set of particle engines that are created by a "Tester" class (e.g. DensityTester) which implements the abstract class 
 *  ParticleEngineTestingUnit. For example, The DensityTester stores a List of particle engines which have the same parameters except
 *  for density, which is varied across the particle engines. 
 *  
 *  To determine which parameter or feature is tested, the user specifies which type of ParticleEngineTestingUnit to instantiate in the 
 *  setUpParticleEngines method (see below). The "Tester" classes are not limited to testing a single parameter. The user can create 
 *  a "Tester" class that holds any number of particle engine objects with any variations of parameters desired.
 *  
 *  ****Work-in-progress: This class is subject to change.***
 *  
 * @author Kathleen, JApplet code borrowed from Professor Duvall
 *
 */

public class ParticleEngineTester extends JApplet {
    private static final Dimension defaultSize = new Dimension(800, 800);
    private static final int ONE_SECOND = 1000;
    private static final int FRAMES_PER_SECOND = 30;
    private Timer myTimer;
    private Game myGame;
    private List<ParticleEngine> myParticleEngines = new ArrayList<ParticleEngine>();
    
    /**
     * Initializes the applet --- called by the browser.
     */
    @Override
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
        
        setFocusable(true);
        requestFocus();
        
        setUpParticleEngines();
    }
 
    /**
     * Instantiates myParticleEngines as a copy of the List of ParticleEngine objects in the specified ParticleEngineTestingUnit subclass.
     */
    public void setUpParticleEngines(){
    	//ParticleEngineTestingUnit myTestingUnit = new AngleDistributionTester(); //THIS IS THE ONLY LINE THAT NEEDS TO BE CHANGED BY USER.
    	ParticleEngineTestingUnit myTestingUnit = new OutwardShootingTester(); //uncomment this line and comment the 
    	                                                                         //previous line to run the OutwardShootingTester
    	myParticleEngines = myTestingUnit.getParticleEngines();
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
    public void stop() {
        myTimer.stop();
    }

    /**
     * Called by Java to paint the frame
     * 
     * @param g The graphics object passed by Java, will be cast as
     *        SpecialGraphics
     */
    @Override
	public void paint(Graphics g) {
    	g.setColor(Color.BLACK);
        g.fillRect(0, 0, getSize().width, getSize().height);
    	for (ParticleEngine e : myParticleEngines)
    		e.draw(g);
    }

    /**
     * Called at regular intervals set up by the action listener instantiated in
     * the start method.
     */
    public void update() {
    	for (ParticleEngine e : myParticleEngines)
    		e.update();
    }
}
