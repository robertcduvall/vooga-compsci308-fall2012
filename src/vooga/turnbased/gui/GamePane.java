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
import vooga.turnbased.gamecore.GameManager;


@SuppressWarnings("serial")
/**
 * The canvas that paints game objects every for every myDelayTime milliseconds
 * Responds to input events
 * 
 * @author Rex, Vo
 */
public class GamePane extends DisplayPane implements Runnable {

    private GameManager myGameManager;
    private Thread myGameThread;
    private static int delayBetweenGameLoopCycles;

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
        //addMouseListener(new GameMouseListener());
        initMouseListener();
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
    public void update () {
        myGameManager.update();
    }

    /**
     * Paint gameobjects and background to the canvas using double buffering
     */
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
    
    private void initMouseListener() { //TODO: subscribe to input team
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked (MouseEvent e) {
                myGameManager.addMouseAction(e.getPoint(), e.getButton());
            }
        });
        
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged (MouseEvent e) {
                myGameManager.handleMouseDragged(e);
            }

            @Override
            public void mouseMoved (MouseEvent e) {
            }
        });
    }

}
