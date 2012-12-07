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
import vooga.turnbased.gamecore.GameLoop;
import vooga.turnbased.gamecore.GameManager;


@SuppressWarnings("serial")
/**
 * The canvas that paints game objects every for every myDelayTime milliseconds
 * Responds to input events
 * 
 * @author Rex, volodymyr
 */
public class GamePane extends DisplayPane implements Runnable, GameLoop {

    public static final int MOUSE_PRESSED = 0;
    public static final int MOUSE_RELEASED = 1;
    public static final int MOUSE_DRAGGED = 2;
    public static final int MOUSE_CLICKED = 3;
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
        delayBetweenGameLoopCycles = Integer.parseInt(GameWindow.importString("Delay"));
        initMouseListener();
        enableFocus();
    }

    /**
     * initialize properties when user switch to game
     */
    public void initialize () {
        myGameManager = new GameManager(this, getGameWindow().getXmlPath());
        myGameThread = new Thread(this);
        myGameThread.setDaemon(true);
        myGameThread.start();
        repaint();
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
     * @param g the graphics pen you are writing with
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
        long beforeTime;
        long timeDiff;
        long sleep;
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

    /**
     * Returns the delay time
     * @return Returns the time between game cycles
     */
    public static int getDelayTime () {
        return delayBetweenGameLoopCycles;
    }

    private void initMouseListener() { 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                myGameManager.addMouseAction(MOUSE_PRESSED, e.getPoint(), e.getButton());
            }
            @Override
            public void mouseReleased (MouseEvent e) {
                myGameManager.addMouseAction(MOUSE_RELEASED, e.getPoint(), e.getButton());
            }
            @Override
            public void mouseClicked (MouseEvent e) {
                myGameManager.addMouseAction(MOUSE_CLICKED, e.getPoint(), e.getButton());
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged (MouseEvent e) {
                myGameManager.addMouseAction(MOUSE_DRAGGED, e.getPoint(), e.getButton());
            }

            @Override
            public void mouseMoved (MouseEvent e) {
            }
        });
    }
    /**
     * Returns to menu pane 
     */
    public void returnToMenu() {
        myGameThread.stop();
        getGameWindow().changeActivePane(GameWindow.MENU);
    }
}
