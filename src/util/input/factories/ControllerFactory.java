package util.input.factories;

import java.awt.Component;
import util.input.android.bluetoothserver.AndroidBluetoothServer;
import util.input.core.AndroidController;
import util.input.core.Controller;
import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.input.core.WiiController;
import wiiusej.Wiimote;


/**
 * Create the desired controller here.
 * 
 * @author Amay
 * 
 */
public class ControllerFactory {

    /**
     * Use the keyboard as a controller.
     * 
     * @param c - The component to which this controller will be added to
     * @return - The Controller object
     */
    @SuppressWarnings("rawtypes")
    public static Controller createKeyboardController (Component c) {
        // Create keyboard controller
        return new KeyboardController(c);
    }

    /**
     * Use the mouse as a controller.
     * 
     * @param c - The component to which this controller will be added to
     * @return - The Controller object
     */
    @SuppressWarnings("rawtypes")
    public static Controller createMouseController (Component c) {
        // Create mouse controller
        return new MouseController(c);
    }

    /**
     * Use the Wii as a controller.
     *
     * @return - The Controller object
     */
    @SuppressWarnings("rawtypes")
    public static Controller createWiiController (int controllerNum) {
        // Create wii controller
        return new WiiController(controllerNum);
    }

    /**
     * Use the Android as a controller.
     * 
     * @return - The Controller object
     */
    @SuppressWarnings("rawtypes")
    public static Controller createAndroidController (int controllerNum) {
        // Create android controller
        return new AndroidController(controllerNum);
    }

}
