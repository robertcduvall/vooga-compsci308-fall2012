/**
 * The canvas that paints game objects every for every myDelayTime milliseconds
 * Responds to input events 
 * @author Rex, Vo
 */
package vooga.turnbased.gui;

import vooga.turnbased.gamecore.GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ResourceBundle;

public class GameCanvas extends Canvas implements Runnable, KeyListener {

    private GameWindow myGameWindow;
    private GameManager myGameManager;
    private Thread myGameThread;
    private ResourceBundle myResources;
    private int myDelayTime;

    // InfoPanel infoPanel;

    /**
     * Constructor of the canvas which displays the game
     * @param gameWindow The GameWindow it belongs to
     */
    public GameCanvas (GameWindow gameWindow) {
        myGameWindow = gameWindow;
        myGameThread = new Thread(this);
        myResources = myGameWindow.getResources();
        myDelayTime = Integer.parseInt(myResources.getString("Delay"));
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
        Image background = GameWindow.importImage(myResources, "BackgroundImage");
        g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), this);
    }

    /**
     * main game loop
     */
    @Override
    public void run () {
        while (!myGameManager.isOver()) {
            myGameManager.update();
            myGameManager.paint();
            try {
                Thread.sleep(myDelayTime);
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
    }

    /**
     * event handling when any key is released
     */
    @Override
    public void keyReleased (KeyEvent e) {
        System.out.println("Released " + e.getKeyCode());
    }

}
