package util.input.tests.android;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JPanel;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.JoyStickEvent;
import util.input.android.events.LineSegment;
import util.input.core.AndroidController;
import util.input.factories.ControllerFactory;
import util.input.interfaces.listeners.AndroidListener;

/**
 * @author Ben
 * A rough sample game that uses an andorid touch controller to draw on the screen.
 *
 */
public class AndroidDrawGame extends JPanel implements Runnable, AndroidListener {

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
    private AndroidController testController;
    private ArrayList<LineSegment> mySegments;

    public AndroidDrawGame () {
        mySegments = new ArrayList<LineSegment>();
        testController = (AndroidController) ControllerFactory.createAndroidController(1);
        setControls();
        setDoubleBuffered(true);
        this.setFocusable(true);
        myGameLoop = new Thread(this);
    }
    /**
     * Set the controls for this game.
     */
    public void setControls(){
        try {
            testController.setControl(AndroidButtonEvent.TouchController.A, AndroidButtonEvent.BUTTON_PRESSED, this, "changeToRed");
            testController.setControl(AndroidButtonEvent.TouchController.B, AndroidButtonEvent.BUTTON_PRESSED, this, "clearScreen");
            testController.setControl(AndroidButtonEvent.TouchController.X, AndroidButtonEvent.BUTTON_RELEASED, this, "changeToBlue");
            testController.setControl(AndroidButtonEvent.TouchController.Y, AndroidButtonEvent.BUTTON_RELEASED, this, "changeToGreen");
            testController.subscribe(this);
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (IllegalAccessException e){

            e.printStackTrace();
        }
       
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
        pen.setColor(myPenColor);
        Stroke stroke = new BasicStroke (5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        pen.setStroke(stroke);
        for (int i = mySegments.size() - 1; i >= 0; i--) {
            LineSegment l = mySegments.get(i);
            pen.drawLine((int) (l.getRelativeStartX() * getWidth()),
                         (int) (l.getRelativeStartY() * getHeight()),
                         (int) (l.getRelativeEndX() * getWidth()),
                         (int) (l.getRelativeEndY() * getHeight()));
        }

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

    public void addLine (LineSegment l) {
        mySegments.add(l);

    };

    public void changeToRed(){
        myPenColor = Color.RED;
    }

    public void clearScreen(){
        mySegments.clear();
    }

    public void changeToBlue(){
        myPenColor = Color.BLUE;
    }
    public void changeToGreen(){
        myPenColor = Color.GREEN;
    }
    
    
    @Override
    public void onScreenPress (AndroidButtonEvent b) {
        System.out.println("doing extra button work");

    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {
        System.out.println("I received a joystick event!");

    }

    @Override
    public void onControllerDisconnect () {
        System.out.println("controller was disconected! game paused");
        
    }
    /**
     * Overriding on touchMovemnt to keep track of the line segments received from the touch android controller.
     */
    @Override
    public void onTouchMovement (LineSegment l) {
        addLine(l);
    }

}
