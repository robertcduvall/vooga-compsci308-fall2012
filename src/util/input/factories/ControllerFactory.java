package util.input.factories;

import java.awt.Component;
import wiiusej.Wiimote;
import util.input.core.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/3/12
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */

public class ControllerFactory {
    public static final int WII_CONTROLLER=1;
    public static final int ANDROID_CONTROLLER=2;

    public static Controller createKeyBoardController(Component c) {
        //Create keyboard controller
        return null;
    }
    
    public static Controller createMouseController(Component c) {
        //Create mouse controller
        return null;
    }
    
    public static Controller createWiiController(Wiimote wiiControl) {
        //Create wii controller
        return null;
    }
    
    public static Controller createAndroidController(Object androidControl) {
        //Create android controller
        return null;
    }

}
