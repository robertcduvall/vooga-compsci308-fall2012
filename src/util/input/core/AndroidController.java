package util.input.core;

import java.lang.reflect.InvocationTargetException;
import util.input.android.bluetoothserver.AndroidBluetoothServer;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidControllerConfigMessage;
import util.input.android.events.AndroidSensorEvent;
import util.input.android.events.AndroidServerMessage;
import util.input.android.events.JoyStickEvent;
import util.input.android.events.LineSegment;
import util.input.inputhelpers.UKeyCode;
import util.input.interfaces.listeners.AndroidListener;


/**
 * This class allows users to enter input through an Android app.
 * 
 * @author Ben, Lance
 * 
 */
public class AndroidController extends Controller<AndroidListener> implements AndroidListener {


    /**
     * Create a new android controller.
     * 
     * @param controllerNum
     */

    private int myControllerNum;
    private AndroidBluetoothServer myServer;

    /**
     * Create a new Android controller.
     *
     * @param controllerNum - The controller number
     */
    public AndroidController (int controllerNum) {
        super();
        myControllerNum = controllerNum;
        myServer = new AndroidBluetoothServer(controllerNum);
        myServer.subscribe(this);
        myServer.startServer();
    }

    @Override
    public void onScreenPress (AndroidButtonEvent b) {
        try {
            performReflections(b, "onScreenPress", UKeyCode.codify(b.getPressType(), b.getID()));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {
        try {
            performReflections(j, "onJoyStickMove", j.getID());
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void onControllerDisconnect () {
        try {
            performReflections("onControllerDisconnect", Controller.NO_ACTION);
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    /**
     * Restart the server that this controller is connected to. User will have
     * to connect in app before connection is reestablished.
     */
    public void restartController () {
        AndroidBluetoothServer server = new AndroidBluetoothServer(myControllerNum);
        server.subscribe(this);
        server.startServer();
    }

    @Override
    public void onTouchMovement (LineSegment l) {
        try {
            performReflections(l, "onTouchMovement", Controller.NO_ACTION);
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }

    }

    /**
     * Send a message from your game to an android controller.
     * @param message the message to send.
     */
    public void messageServer (AndroidServerMessage message) {
        myServer.notifyController(message);
    }
    /**
     * Set what controllers are shown to a user.
     * @param isGameBoyActive show the gameboy?
     * @param isPlaystationActive show the playstation controller?
     * @param isTouchControlActive show the touch controller?
     * @param isAccelerometerActive enable the accelerometer?
     */
    public void setControlOptions (boolean isGameBoyActive,
            boolean isPlaystationActive, boolean isTouchControlActive,
            boolean isAccelerometerActive) {
        AndroidControllerConfigMessage settings =
                new AndroidControllerConfigMessage(
                isGameBoyActive, isPlaystationActive, isTouchControlActive,
                isAccelerometerActive);
        myServer.notifyController(settings);
    }


    @Override
    public void onAccelerometerEvent (AndroidSensorEvent e) {
        try {
            performReflections(e, "onAccelerometerEvent", Controller.NO_ACTION);
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

}
