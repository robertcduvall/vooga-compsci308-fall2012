package vooga.turnbased.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import vooga.turnbased.gamecore.GameLoopMember;
import vooga.turnbased.gamecore.GameManager;


@SuppressWarnings("serial")
/**
 * The canvas that paints game objects every for every myDelayTime milliseconds
 * Responds to input events
 * 
 * @author Rex, Vo
 */
public class GamePane extends DisplayPane implements Runnable, GameLoopMember {

    private GameManager myGameManager;
    private Thread myGameThread;
    private Point myMousePressedPosition;
    private static int delayBetweenGameLoopCycles;

    // InfoPanel infoPanel;

    /**
     * Constructor of the canvas which displays the game
     * 
     * @param gameWindow
     *        The GameWindow it belongs to
     */
    public GamePane (GameWindow gameWindow) {
        super(gameWindow);
        myGameThread = new Thread(this);
        delayBetweenGameLoopCycles = Integer.parseInt(GameWindow.importString("Delay"));
        addMouseListeners();
        myGameManager = new GameManager(this);
        enableFocus();
    }

    /**
     * initialize properties when user switch to game
     */
    public void initialize () {
        repaint();
        myGameThread.start();
    }

    /**
     * update game
     */
    @Override
    public void update () {
        myGameManager.update();
    }

    /**
     * Paint gameobjects and background to the canvas using double buffering
     */
    @Override
    public void paint (Graphics g) {
        Image nextFrameImage = createImage(getSize().width, getSize().height);
        Graphics nextFrameGraphics = nextFrameImage.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        myGameManager.paint(nextFrameGraphics);
        g2d.drawImage(nextFrameImage, 0, 0, null);
    }

    /**
     * main game loop
     */
    @Override
    public void run () {
        long beforeTime, timeDiff, sleep;
        while (!myGameManager.isOver()) {
            beforeTime = System.currentTimeMillis();
            update();
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = delayBetweenGameLoopCycles - timeDiff;
            if (sleep < 0) {
                sleep = 0;
            }
            try {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int getDelayTime () {
        return delayBetweenGameLoopCycles;
    }

    private class GameMouseListener implements MouseListener {
        @Override
        public void mouseClicked (MouseEvent e) {
            myGameManager.handleMouseClicked(e);
        }

        @Override
        public void mouseEntered (MouseEvent e) {
        }

        @Override
        public void mouseExited (MouseEvent e) {
        }

        @Override
        public void mousePressed (MouseEvent e) {
            myMousePressedPosition = e.getPoint();
        }

        @Override
        public void mouseReleased (MouseEvent e) {
        }
    }

    private void addMouseListeners () {
        addMouseListener(new GameMouseListener());
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged (MouseEvent e) {
                myGameManager.handleMouseDragged(e);
            }

            @Override
            public void mouseMoved (MouseEvent arg0) {
            }
        });
    }

    public Point getMousePressedPosition () {
        return myMousePressedPosition;
    }
}
