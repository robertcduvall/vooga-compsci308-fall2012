package util.input.android.bluetoothserver;

import util.input.android.events.AndroidButtonEvent;
import util.input.core.AndroidController;
import util.input.factories.ControllerFactory;

public class TestAndroidController {
    
    public static void main(String [] args){
        AndroidController testController = ControllerFactory.createAndroidController();
        System.out.println("you are running a test android controller");
        TestAndroidController myTester = new TestAndroidController();
        try {
            testController.setControl(AndroidButtonEvent.Playstation.X, AndroidButtonEvent.BUTTON_PRESSED, myTester, "jump");
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public void jump(){
        System.out.println("I Jumped!");
    }
    public void duck(){
        System.out.println("I ducked");
    }

}
