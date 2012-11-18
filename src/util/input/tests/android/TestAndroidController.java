package util.input.tests.android;

import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.JoyStickEvent;
import util.input.android.events.LineSegment;
import util.input.core.AndroidController;
import util.input.core.Controller;
import util.input.factories.ControllerFactory;
import util.input.interfaces.listeners.AndroidListener;

/**
 * A class to test the android controller code.
 * @author Ben Schwab
 *
 */
public class TestAndroidController implements AndroidListener {

    private AndroidController myController;
    private AndroidDrawGame myGame;
    public static void main(String [] args){
       
        
    }
    
    public TestAndroidController(AndroidDrawGame parent){
        myGame = parent;
        Controller<AndroidListener> testController = ControllerFactory.createAndroidController();
        myController = (AndroidController) testController;
        System.out.println("you are running a test android controller");
        testController.subscribe(this);
        try {
            testController.setControl(AndroidButtonEvent.Playstation.X, AndroidButtonEvent.BUTTON_PRESSED, this, "jump");
            testController.setControl(AndroidButtonEvent.Playstation.CIRCLE, AndroidButtonEvent.BUTTON_PRESSED, this, "duck");
            testController.setControl(AndroidButtonEvent.Playstation.CIRCLE, AndroidButtonEvent.BUTTON_RELEASED, this, "stopDuck");
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        catch (IllegalAccessException e){

            e.printStackTrace();
        }
        
    }

    public void jump(){
        System.out.println("I Jumped!");
    }
    public void duck(){
        System.out.println("I ducked");
    }
    public void stopDuck(){
        System.out.println("I stoped ducking");
    }

    @Override
    public void onScreenPress (AndroidButtonEvent b) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {
        System.out.println("I received a joystick event!");

    }

    @Override
    public void onControllerDisconnect () {
       //myController.restartController();
        System.out.println("controller was disconected! game paused");
        
    }

    @Override
    public void onTouchMovement (LineSegment l) {
        myGame.addLine(l);
        System.out.println("received touch movement");
        
    }
    

}
