package gui;

import gamecore.GameManager;

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

    public GameCanvas (GameWindow gameWindow) {
        myGameWindow = gameWindow;
        myGameThread = new Thread(this);
        myResources = myGameWindow.getResources();
        myDelayTime = Integer.parseInt(myResources.getString("Delay"));
    }

    public void initialize () {
        addMenu();
        addKeyListener(this);
        myGameManager = new GameManager(this);
        repaint();
        setFocusable(true);
        requestFocusInWindow();
        myGameThread.start();
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Image background = GameWindow.importImage(myResources, "BackgroundImage");
        g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), this);
    }

    private void addMenu () {
        // TODO Auto-generated method stub
    }

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

    @Override
    public void keyTyped (KeyEvent e) {
        System.out.println("Typed " + e.getKeyCode());
    }

    @Override
    public void keyPressed (KeyEvent e) {
        System.out.println("Pressed " + e.getKeyCode());
    }

    @Override
    public void keyReleased (KeyEvent e) {
        System.out.println("Released " + e.getKeyCode());
    }

}
