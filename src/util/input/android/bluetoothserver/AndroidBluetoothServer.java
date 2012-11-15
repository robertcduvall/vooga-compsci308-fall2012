package util.input.android.bluetoothserver;

import java.util.ArrayList;
import java.util.List;
import util.input.android.events.AndroidButtonEvent;
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
    public static final int CONTROLLER_ONE = 0;
    public static final int CONTROLLER_TWO = 1;
    public static final int CONTROLLER_THREE = 2;
    public static final int CONTROLLER_FOUR = 3;
    public List<AndroidListener> myListeners;

    public AndroidBluetoothServer (int controllerNumber) {
        myWaitThread = new Thread(new WaitThread(controllerNumber, this));
        myListeners = new ArrayList<AndroidListener>();
    }

    public void startServer () {
        myWaitThread.start();
    }

    public void subscribe (AndroidListener subscriber) {
        myListeners.add(subscriber);
        System.out.println("subscribed");
    }

    public void notify (AndroidButtonEvent b) {
        System.out.println(myListeners.size());
        for (AndroidListener listener : myListeners) {
            listener.onScreenPress(b);
            System.out.println("notifying");
        }
    }

}
