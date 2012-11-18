package util.input.android.bluetoothserver;

import java.util.ArrayList;
import java.util.List;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.JoyStickEvent;
import util.input.interfaces.listeners.AndroidListener;


/**
 * This class uses the BlueCove bluetooth API to create a server to receive
 * events from an android app. The server will notify all members 
 * subscribed to it when an AndroidControllerEvent occurs.
 *  Any number of game controllers could potentially
 * subscribe to receive notifications from this class.
 * 
 * @author Ben Schwab
 * 
 */
public class AndroidBluetoothServer {

    private Thread myWaitThread;
    private List<AndroidListener> myListeners;

    /**
     * Create a new bluetooth server. You must
     * @param controllerNumber the number of this controller. Use class constants.
     */
    public AndroidBluetoothServer (int controllerNumber) {
        myWaitThread = new Thread(new WaitThread(controllerNumber, this));
        myListeners = new ArrayList<AndroidListener>();
    }
    /**
     * Start the server listening for bluetooth connections.
     */
    public void startServer () {
        myWaitThread.start();
    }
    /**
     * Subscribe to receive updated from this controller.
     * @param subscriber A class which will directly receive updates from this controller.
     */
    public void subscribe (AndroidListener subscriber) {
        myListeners.add(subscriber);
    }

    /**
     * notify all subscribers of the button event
     * @param buttonEvent the event that just occurred.
     */
    protected void notify (AndroidButtonEvent buttonEvent) {
        for (AndroidListener listener : myListeners) {
            listener.onScreenPress(buttonEvent);
        }
    }
    /**
     * notify all subscribers of the joystick event
     * @param buttonEvent the event that just occurred.
     */
    protected void notify(JoyStickEvent joystickEvent){
        for (AndroidListener listener : myListeners) {
            listener.onJoyStickMove(joystickEvent);
        }
    }
    
    protected void notifyDisconnect(){
        for (AndroidListener listener : myListeners) {
            listener.onControllerDisconnect();
        }
    }

}
