/**
 * The canvas that paints game objects every for every myDelayTime milliseconds
 * Responds to input events 
 * @author Rex, Vo
 */
package vooga.turnbased.gui;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import vooga.turnbased.gamecore.GameManager;

@SuppressWarnings("serial")
public class GamePane extends DisplayPane implements Runnable {

    private GameManager myGameManager;
    private Thread myGameThread;
    private int myDelayTime;

    // InfoPanel infoPanel;

    /**
     * Constructor of the canvas which displays the game
     * @param gameWindow The GameWindow it belongs to
     */
    public GamePane (GameWindow gameWindow) {
        super(gameWindow);
        myGameThread = new Thread(this);
        myDelayTime = Integer.parseInt(GameWindow.importString("Delay"));
        addKeyListener(this);
        myGameManager = new GameManager(this);
        enableFocus();
    }
    
    /**
     * initialize properties when user switch to game
     */
    public void initialize () {
        //addKeyListener(this);
        //myGameManager = new GameManager(this);
        repaint();
        //setFocusable(true);
        //requestFocusInWindow();
        myGameThread.start();
    }

    /**
     * override to implement buffer for painting objects
     */
    @Override
    public void update(Graphics g) {
    	myGameManager.update();
    	Image offScreenImage = createImage(getSize().width, getSize().height);
    	Graphics offScreenGraphics = offScreenImage.getGraphics();
    	paint(offScreenGraphics);
    	g.drawImage(offScreenImage, 0, 0, null);
    }
    
    /**
     * Paint gameobjects and background to the canvas
     */
    //@Override
    public void paint(Graphics g) {
    	myGameManager.paint(g);
    }
    
    /**
     * main game loop
     */
    @Override
    public void run () {
    	long beforeTime, timeDiff, sleep;
        while (!myGameManager.isOver()) {
        	beforeTime = System.currentTimeMillis();
            myGameManager.update();
            repaint();
            
            timeDiff = System.currentTimeMillis() - beforeTime;
	        sleep = myDelayTime - timeDiff;
            try {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * event handling when user types anything
     */
    @Override
    public void keyTyped (KeyEvent e) {
        //System.out.println("Typed " + e.getKeyCode());
    }

    /**
     * event handling when any key is pressed
     */
    @Override
    public void keyPressed (KeyEvent e) {
        //System.out.println("Pressed " + e.getKeyCode());
        myGameManager.handleKeyPressed(e);
    }

    /**
     * event handling when any key is released
     */
    @Override
    public void keyReleased (KeyEvent e) {
        //System.out.println("Released " + e.getKeyCode());
        myGameManager.handleKeyReleased(e);
    }

    public int getDelayTime() {
    	return myDelayTime;
    }
    
}
