package util.input.tests.twocontroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidSensorEvent;
import util.input.android.events.JoyStickEvent;
import util.input.android.events.LineSegment;
import util.input.core.AndroidController;
import util.input.core.KeyboardController;
import util.input.core.WiiController;
import util.input.factories.ControllerFactory;
import util.input.interfaces.listeners.AndroidListener;
import util.input.tests.android.TestAndroidController;

/**
 * @author Ben
 * A rough sample game using the input system. Uses three android controllers and one wii controller.
 *
 */
public class TwoController extends JPanel implements Runnable, AndroidListener {
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

    // The objects to be controlled by the controllers
    public Square squareOne;
    public Square squareTwo;
    public Square squareThree;
    public Square squareFour;

    private ArrayList<LineSegment> mySegments;

    public TwoController () {
        mySegments = new ArrayList<LineSegment>();
        squareOne = new Square(new Point(50, 50), Color.BLUE);
        squareTwo = new Square(new Point(200, 200), Color.RED);
        squareThree = new Square(new Point(500, 500), Color.GREEN);
        squareFour = new Square(new Point(300, 300), Color.CYAN);
        setDoubleBuffered(true);
        this.setFocusable(true);
        intializeControllers();
        myGameLoop = new Thread(this);
    }

    /**
     * Use this method as a guide on how to initialize multiple controllers.
     */
    public void intializeControllers () {
        
        // create a android controller for player 1
        AndroidController myAndroidController =
                (AndroidController) ControllerFactory.createAndroidController(1);

        // map the first controller actions
        try {
            myAndroidController.setControl(AndroidButtonEvent.Playstation.UP,
                                           AndroidButtonEvent.BUTTON_PRESSED, squareOne,
                                           "enableMoveUp");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.DOWN,
                                           AndroidButtonEvent.BUTTON_PRESSED, squareOne,
                                           "enableMoveDown");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.LEFT,
                                           AndroidButtonEvent.BUTTON_PRESSED, squareOne,
                                           "enableMoveLeft");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.RIGHT,
                                           AndroidButtonEvent.BUTTON_PRESSED, squareOne,
                                           "enableMoveRight");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.UP,
                                           AndroidButtonEvent.BUTTON_RELEASED, squareOne,
                                           "disableMoveUp");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.DOWN,
                                           AndroidButtonEvent.BUTTON_RELEASED, squareOne,
                                           "disableMoveDown");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.LEFT,
                                           AndroidButtonEvent.BUTTON_RELEASED, squareOne,
                                           "disableMoveLeft");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.RIGHT,
                                           AndroidButtonEvent.BUTTON_RELEASED, squareOne,
                                           "disableMoveRight");
            myAndroidController.subscribe(this);

        }
        catch (NoSuchMethodException e) {

            e.printStackTrace();
        }
        catch (IllegalAccessException e) {

            e.printStackTrace();
        }
        
        // create an android controller for player 2
        AndroidController myAndroidControllerTwo =
                (AndroidController) ControllerFactory.createAndroidController(2);
        try {
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.UP,
                                              AndroidButtonEvent.BUTTON_PRESSED, squareTwo,
                                              "enableMoveUp");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.DOWN,
                                              AndroidButtonEvent.BUTTON_PRESSED, squareTwo,
                                              "enableMoveDown");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.LEFT,
                                              AndroidButtonEvent.BUTTON_PRESSED, squareTwo,
                                              "enableMoveLeft");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.RIGHT,
                                              AndroidButtonEvent.BUTTON_PRESSED, squareTwo,
                                              "enableMoveRight");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.UP,
                                              AndroidButtonEvent.BUTTON_RELEASED, squareTwo,
                                              "disableMoveUp");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.DOWN,
                                              AndroidButtonEvent.BUTTON_RELEASED, squareTwo,
                                              "disableMoveDown");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.LEFT,
                                              AndroidButtonEvent.BUTTON_RELEASED, squareTwo,
                                              "disableMoveLeft");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.RIGHT,
                                              AndroidButtonEvent.BUTTON_RELEASED, squareTwo,
                                              "disableMoveRight");
        }
        catch (NoSuchMethodException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // create and android controller for player three
        
        AndroidController myAndroidControllerThree =
                (AndroidController) ControllerFactory.createAndroidController(3);
        try {
            myAndroidControllerThree.setControl(AndroidButtonEvent.Playstation.UP,
                                                AndroidButtonEvent.BUTTON_PRESSED, squareFour,
                                                "enableMoveUp");
            myAndroidControllerThree.setControl(AndroidButtonEvent.Playstation.DOWN,
                                                AndroidButtonEvent.BUTTON_PRESSED, squareFour,
                                                "enableMoveDown");
            myAndroidControllerThree.setControl(AndroidButtonEvent.Playstation.LEFT,
                                                AndroidButtonEvent.BUTTON_PRESSED, squareFour,
                                                "enableMoveLeft");
            myAndroidControllerThree.setControl(AndroidButtonEvent.Playstation.RIGHT,
                                                AndroidButtonEvent.BUTTON_PRESSED, squareFour,
                                                "enableMoveRight");
            myAndroidControllerThree.setControl(AndroidButtonEvent.Playstation.UP,
                                                AndroidButtonEvent.BUTTON_RELEASED, squareFour,
                                                "disableMoveUp");
            myAndroidControllerThree.setControl(AndroidButtonEvent.Playstation.DOWN,
                                                AndroidButtonEvent.BUTTON_RELEASED, squareFour,
                                                "disableMoveDown");
            myAndroidControllerThree.setControl(AndroidButtonEvent.Playstation.LEFT,
                                                AndroidButtonEvent.BUTTON_RELEASED, squareFour,
                                                "disableMoveLeft");
            myAndroidControllerThree.setControl(AndroidButtonEvent.Playstation.RIGHT,
                                                AndroidButtonEvent.BUTTON_RELEASED, squareFour,
                                                "disableMoveRight");
        }
        catch (NoSuchMethodException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // create a keyboard controller for player four
        KeyboardController myKeyboardController =
                (KeyboardController) ControllerFactory.createKeyboardController(this);
        try {
            myKeyboardController.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED,
                                            squareThree, "enableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED,
                                            squareThree, "enableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED,
                                            squareThree, "enableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED,
                                            squareThree, "enableMoveRight");
            myKeyboardController.setControl(KeyEvent.VK_UP, KeyboardController.RELEASED,
                                            squareThree, "disableMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.RELEASED,
                                            squareThree, "disableMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.RELEASED,
                                            squareThree, "disableMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.RELEASED,
                                            squareThree, "disableMoveRight");
        }
        catch (NoSuchMethodException e) {

            e.printStackTrace();
        }
        catch (IllegalAccessException e) {

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
        squareOne.draw(pen);
        squareTwo.draw(pen);
        squareThree.draw(pen);
        squareFour.draw(pen);

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

    /*
     * Methods of the android interface to allow for more complex moves --
     * (joystick movement)
     */
    @Override
    public void onScreenPress (AndroidButtonEvent b) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {    
        double mag = j.getMyMagnitude() * 4;
        double angle = j.getMyAngle();
        int x = (int) (mag * Math.cos(Math.toRadians(angle)));
        int y = (int) (-mag * Math.sin(Math.toRadians(angle)));
        squareOne.setNextMove(x, y);
    }

    @Override
    public void onControllerDisconnect () {
        // do something on disconnect such as pausing the game

    }

    @Override
    public void onTouchMovement (LineSegment l) {
        //not relevant for non touch controller enabled games
    }

    @Override
    public void onAccelerometerEvent (AndroidSensorEvent e) {
        // TODO Auto-generated method stub
        
    }

}
