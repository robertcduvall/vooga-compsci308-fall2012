package util.input.factories;

import java.awt.Component;
import util.input.core.Controller;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.input.core.WiiController;
import wiiusej.Wiimote;



/**
 * Create the desired controller here
 * 
 * @author Amay
 *
 */
public class ControllerFactory {

    /**
     * Use the keyboard as a controller
     * 
     * @param c - The component to which this controller will be added to
     * @return - The Controller object
     */
    public static Controller createKeyBoardController(Component c) {
        //Create keyboard controller
        return new KeyboardController(c);
    }
    
    /**
     * Use the mouse as a controller
     *
     * @param c - The component to which this controller will be added to
     * @return - The Controller object
     */
    public static Controller createMouseController(Component c) {
        //Create mouse controller
        return new MouseController(c);
    }
    
    public static Controller createWiiController(Wiimote wiiControl) {
        //Create wii controller
        return new WiiController(wiiControl);
    }
    
    public static Controller createAndroidController(Object androidControl) {
        //Create android controller
        return null;
    }

}
