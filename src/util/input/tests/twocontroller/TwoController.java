package util.input.tests.twocontroller;

import src.util.input.tests.android.ArrayList;
import src.util.input.tests.android.BasicStroke;
import src.util.input.tests.android.Color;
import src.util.input.tests.android.Graphics;
import src.util.input.tests.android.Graphics2D;
import src.util.input.tests.android.InterruptedException;
import src.util.input.tests.android.LineSegment;
import src.util.input.tests.android.Override;
import src.util.input.tests.android.Stroke;
import src.util.input.tests.android.SuppressWarnings;
import src.util.input.tests.android.TestAndroidController;
import src.util.input.tests.android.Thread;

public class TwoController {
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

    public AndroidDrawGame () {
        mySegments = new ArrayList<LineSegment>();
        TestAndroidController myController = new TestAndroidController(this);
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

 

}
