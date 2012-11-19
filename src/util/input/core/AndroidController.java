package util.input.core;

import java.lang.reflect.InvocationTargetException;
import util.input.android.bluetoothserver.AndroidBluetoothServer;
import util.input.android.events.AndroidButtonEvent;
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
     * @param controllerNum 
     */
    public AndroidController (int controllerNum) {
        super();
        AndroidBluetoothServer server = new AndroidBluetoothServer(controllerNum);
        server.subscribe(this);
        server.startServer();
    }

    @Override
    public void onScreenPress (AndroidButtonEvent b) {
        try {
            performReflections(b, "onScreenPress", UKeyCode.codify(b.getPressType(), b.getID()));
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        } 
    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {
        try {
            performReflections(j, "onJoyStickMove", j.getID());
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        } 
    }

    public void onControllerDisconnect () {
        try {
            broadcast("onControllerDisconnect");
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        } 
    }
    
    public void restartController(){
        AndroidBluetoothServer server = new AndroidBluetoothServer(0);
        server.subscribe(this);
        server.startServer();
    }

    @Override
    public void onTouchMovement (LineSegment l) {
        try {
            performReflections(l, "onTouchMovement");
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
            e1.printStackTrace();
        } 
        
    }

}
