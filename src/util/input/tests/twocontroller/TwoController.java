package util.input.tests.twocontroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidControllerEvent;
import util.input.android.events.LineSegment;
import util.input.core.AndroidController;
import util.input.core.WiiController;
import util.input.factories.ControllerFactory;
import util.input.tests.android.TestAndroidController;



public class TwoController extends JPanel implements Runnable  {
    /**
     * 
     * @param gameSurface
     */
    public static final int GAME_FPS = 30;
    /**
     * Delay time.
     */
    private final int myDelay = 1000 / GAME_FPS;
    private boolean myIsRunning;
    private Thread myGameLoop;
    private Color myPenColor = Color.BLUE;

    private ArrayList<LineSegment> mySegments;

    public TwoController () {
        mySegments = new ArrayList<LineSegment>();
        //TestAndroidController myController = new TestAndroidController(this);
        WiiController myWiiController = (WiiController)ControllerFactory.createWiiController();
        AndroidController myAndroidController = (AndroidController)ControllerFactory.createAndroidController();
        try {
            myAndroidController.setControl(AndroidButtonEvent.Playstation.CIRCLE, AndroidButtonEvent.BUTTON_PRESSED, this, "testMethod");
            myWiiController.setControl(WiiController.WIIMOTE_BUTTON_A, WiiController.BUTTON_PRESSED, this, "testMethod");
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setDoubleBuffered(true);
        this.setFocusable(true);
        myGameLoop = new Thread(this);
    }

    /**
     * Paint the board by telling Update active level
     * 
     * @param g Graphics used to draw.
     */
    @Override
    public void paint (Graphics g) {
        Graphics2D pen = (Graphics2D) g;
        pen.setColor(Color.BLACK);
        pen.fillRect(0, 0, getWidth(), getHeight());
  

    }

    @Override
    public void run () {
        long beforeTime;
        long timeDiff;
        long sleep;
        beforeTime = System.currentTimeMillis();
        while (true) {
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = myDelay - timeDiff;
            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    /**
     * Starts the JPanel's animation thread
     */
    public void startGame () {
        if (!myIsRunning) {
            myGameLoop.start();
            myIsRunning = true;
        }
    }

    /**
     * Pause the game thread.
     */
    @SuppressWarnings("deprecation")
    public void pauseGame () {
        if (myIsRunning) {
            myGameLoop.stop();
            myIsRunning = false;
        }
    }
    
    public void testMethod(){
        System.out.println("control received");
    }

 

}
