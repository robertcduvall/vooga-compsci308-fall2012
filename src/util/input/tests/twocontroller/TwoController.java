package util.input.tests.twocontroller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidControllerEvent;
import util.input.android.events.JoyStickEvent;
import util.input.android.events.LineSegment;
import util.input.core.AndroidController;
import util.input.core.KeyboardController;
import util.input.core.WiiController;
import util.input.factories.ControllerFactory;
import util.input.interfaces.listeners.AndroidListener;
import util.input.tests.android.TestAndroidController;



public class TwoController extends JPanel implements Runnable, AndroidListener  {
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
    private Square squareOne;
    private Square squareTwo;
    private Square squareThree;
    

    private ArrayList<LineSegment> mySegments;

    public TwoController () {
        mySegments = new ArrayList<LineSegment>();
        //TestAndroidController myController = new TestAndroidController(this);
        squareOne = new Square(new Point(50,50), Color.BLUE);
        squareTwo = new Square(new Point(200,200), Color.RED);
        squareThree = new Square(new Point(500,500), Color.GREEN);
        //WiiController myWiiController = (WiiController)ControllerFactory.createWiiController();
        
        
        intializeControllers();
       
        setDoubleBuffered(true);
        this.setFocusable(true);
        myGameLoop = new Thread(this);
    }

    private void intializeControllers () {
 AndroidController myAndroidController = (AndroidController)ControllerFactory.createAndroidController(1);
        
        try {
            myAndroidController.setControl(AndroidButtonEvent.Playstation.UP, AndroidButtonEvent.BUTTON_PRESSED, squareOne, "toggleMoveUp");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.DOWN, AndroidButtonEvent.BUTTON_PRESSED, squareOne, "toggleMoveDown");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.LEFT, AndroidButtonEvent.BUTTON_PRESSED, squareOne, "toggleMoveLeft");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.RIGHT, AndroidButtonEvent.BUTTON_PRESSED, squareOne, "toggleMoveRight");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.UP, AndroidButtonEvent.BUTTON_RELEASED, squareOne, "toggleMoveUp");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.DOWN, AndroidButtonEvent.BUTTON_RELEASED, squareOne, "toggleMoveDown");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.LEFT, AndroidButtonEvent.BUTTON_RELEASED, squareOne, "toggleMoveLeft");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.RIGHT, AndroidButtonEvent.BUTTON_RELEASED, squareOne, "toggleMoveRight");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.CIRCLE, AndroidButtonEvent.BUTTON_PRESSED, this, "testMethod");
            myAndroidController.subscribe(this);
            //myWiiController.setControl(WiiController.WIIMOTE_BUTTON_A, WiiController.BUTTON_PRESSED, this, "testMethod");
        }
        catch (NoSuchMethodException e) {
            
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            
            e.printStackTrace();
        }
        
        AndroidController myAndroidControllerTwo = (AndroidController)ControllerFactory.createAndroidController(2);
        try {
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.UP, AndroidButtonEvent.BUTTON_PRESSED, squareTwo, "toggleMoveUp");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.DOWN, AndroidButtonEvent.BUTTON_PRESSED, squareTwo, "toggleMoveDown");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.LEFT, AndroidButtonEvent.BUTTON_PRESSED, squareTwo, "toggleMoveLeft");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.RIGHT, AndroidButtonEvent.BUTTON_PRESSED, squareTwo, "toggleMoveRight");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.UP, AndroidButtonEvent.BUTTON_RELEASED, squareTwo, "toggleMoveUp");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.DOWN, AndroidButtonEvent.BUTTON_RELEASED, squareTwo, "toggleMoveDown");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.LEFT, AndroidButtonEvent.BUTTON_RELEASED, squareTwo, "toggleMoveLeft");
            myAndroidControllerTwo.setControl(AndroidButtonEvent.Playstation.RIGHT, AndroidButtonEvent.BUTTON_RELEASED, squareTwo, "toggleMoveRight");
        
        }
        catch (NoSuchMethodException e) {
         
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
         
            e.printStackTrace();
        }
        
        myAndroidControllerTwo.subscribe(new AndroidListener(){

            @Override
            public void onScreenPress (AndroidButtonEvent b) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onJoyStickMove (JoyStickEvent j) {
                double mag = j.getMyMagnitude()*4;
                double angle = j.getMyAngle();
                int x = (int) (mag*Math.cos(Math.toRadians(angle)));
                int y = (int) (-mag*Math.sin(Math.toRadians(angle)));
                squareTwo.setNextMove(x,y);
                
            }

            @Override
            public void onControllerDisconnect () {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onTouchMovement (LineSegment l) {
                // TODO Auto-generated method stub
                
            }
            
        });
        
        KeyboardController  myKeyboardController = (KeyboardController)ControllerFactory.createKeyboardController(this);
        try {
            myKeyboardController.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED, squareThree, "toggleMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED, squareThree, "toggleMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, squareThree, "toggleMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED, squareThree, "toggleMoveRight");
            myKeyboardController.setControl(KeyEvent.VK_UP, KeyboardController.RELEASED, squareThree, "toggleMoveUp");
            myKeyboardController.setControl(KeyEvent.VK_DOWN, KeyboardController.RELEASED, squareThree, "toggleMoveDown");
            myKeyboardController.setControl(KeyEvent.VK_LEFT, KeyboardController.RELEASED, squareThree, "toggleMoveLeft");
            myKeyboardController.setControl(KeyEvent.VK_RIGHT, KeyboardController.RELEASED, squareThree, "toggleMoveRight");
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
        
    }

    @Override
    public void onScreenPress (AndroidButtonEvent b) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {
        //System.out.println(j.getMyAngle());
        double mag = j.getMyMagnitude()*4;
        //System.out.println(j.getMyMagnitude());
        double angle = j.getMyAngle();
        int x = (int) (mag*Math.cos(Math.toRadians(angle)));
        int y = (int) (-mag*Math.sin(Math.toRadians(angle)));
       // System.out.println(x+" "+y);
        squareOne.setNextMove(x,y);
    }

    @Override
    public void onControllerDisconnect () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onTouchMovement (LineSegment l) {
        // TODO Auto-generated method stub
        
    }

 

}
