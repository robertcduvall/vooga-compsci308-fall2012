package util.input.core;

import java.lang.reflect.InvocationTargetException;
import util.input.android.bluetoothserver.AndroidBluetoothServer;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidControllerConfigMessage;
import util.input.android.events.AndroidSensorEvent;
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
    AndroidBluetoothServer myServer;

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
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {
        try {
            performReflections(j, "onJoyStickMove", j.getID());
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
    }

    public void onControllerDisconnect () {
        try {
            broadcast("onControllerDisconnect");
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
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
            performReflections(l, "onTouchMovement");
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }

    }
    public void setControlOptions(boolean isGameBoyActive, boolean isPlaystationActive, boolean isTouchControlActive, boolean isAccelerometerActive){
       AndroidControllerConfigMessage settings = new AndroidControllerConfigMessage(isGameBoyActive, isPlaystationActive, isTouchControlActive, isAccelerometerActive);
       myServer.notifyController(settings);
    }
    
    public void messageServer(){
        //myServer.notifyController(new AndroidButtonEvent(4, 8));
    }

    @Override
    public void onAccelerometerEvent (AndroidSensorEvent e) {
        try {
            performReflections(e, "onAccelerometerEvent");
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        
    }

}
