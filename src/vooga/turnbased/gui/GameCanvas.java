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
import java.util.ResourceBundle;
import vooga.turnbased.gamecore.GameManager;

public class GameCanvas extends Canvas implements Runnable, KeyListener {

    private GameWindow myGameWindow;
    private GameManager myGameManager;
    private Thread myGameThread;
    private int myDelayTime;

    // InfoPanel infoPanel;

    /**
     * Constructor of the canvas which displays the game
     * @param gameWindow The GameWindow it belongs to
     */
    public GameCanvas (GameWindow gameWindow) {
        myGameWindow = gameWindow;
        myGameThread = new Thread(this);
        myDelayTime = Integer.parseInt(GameWindow.importString("Delay"));
    }

    /**
     * initialize properties when user switch to game
     */
    public void initialize () {
        addKeyListener(this);
        myGameManager = new GameManager(this);
        repaint();
        setFocusable(true);
        requestFocusInWindow();
        myGameThread.start();
    }

    /**
     * paint components of the Canvas
     */
    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Image background = GameWindow.importImage("BackgroundImage");
        //g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), this);
    }

    /**
     * override to implement buffer for painting objects
     */
    @Override
    public void update(Graphics g) {
    	Image offScreenImage = createImage(getSize().width, getSize().height);
    	Graphics offScreenGraphics = offScreenImage.getGraphics();
    	paint(offScreenGraphics);
    	g.drawImage(offScreenImage, 0, 0, null);
    	myGameManager.update();
    }
    
    /**
     * Paint gameobjects and background to the canvas
     */
    @Override
    public void paint(Graphics g) {
        myGameManager.paint(g);
    }
    
    /**
     * main game loop
     */
    @Override
    public void run () {
    	long beforeTime, timeDiff, sleep;
	    beforeTime = System.currentTimeMillis();
        while (!myGameManager.isOver()) {
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
        System.out.println("Typed " + e.getKeyCode());
    }

    /**
     * event handling when any key is pressed
     */
    @Override
    public void keyPressed (KeyEvent e) {
        System.out.println("Pressed " + e.getKeyCode());
        myGameManager.handleKeyPressed(e);
    }

    /**
     * event handling when any key is released
     */
    @Override
    public void keyReleased (KeyEvent e) {
        System.out.println("Released " + e.getKeyCode());
        myGameManager.handleKeyReleased(e);
    }

    public int getDelayTime() {
    	return myDelayTime;
    }
}
