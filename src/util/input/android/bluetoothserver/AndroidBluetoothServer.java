package util.input.android.bluetoothserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.bluetooth.UUID;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidSensorEvent;
import util.input.android.events.AndroidServerMessage;
import util.input.android.events.JoyStickEvent;
import util.input.android.events.LineSegment;
import util.input.interfaces.listeners.AndroidListener;


/**
 * This class uses the BlueCove bluetooth API to create a server to receive
 * events from an android app. The server will notify all members
 * subscribed to it when an AndroidControllerEvent occurs.
 * Any number of game controllers could potentially
 * subscribe to receive notifications from this class.
 * 
 * @author Ben Schwab
 * 
 */
public class AndroidBluetoothServer {

    private Thread myWaitThread;
    private List<AndroidListener> myListeners;
    private int myControllerNumber;
    private UUID myServerID1 = new UUID("04c6093b00001000800000805f9b34fb", false);
    private UUID myServerID2 = new UUID("14c6093b00001000800000805f9b34fb", false);
    private UUID myServerID3 = new UUID("24c6093b00001000800000805f9b34fb", false);
    private UUID myServerID4 = new UUID("34c6093b00001000800000805f9b34fb", false);
    private Messenger myMessenger;
    private Queue<AndroidServerMessage> myMessageQueue = new LinkedList<AndroidServerMessage>();

    /**
     * Create a new bluetooth server. You must specify the number of the
     * controller it will listen to.
     * 
     * @param controllerNumber the number of this controller. Use class
     *        constants.
     */
    public AndroidBluetoothServer (int controllerNumber) {
        myWaitThread = new Thread(new WaitThread(controllerNumber, this));
        myListeners = new ArrayList<AndroidListener>();
        myControllerNumber = controllerNumber;

    }

    /**
     * Start the server listening for bluetooth connections.
     */
    public void startServer () {
        myWaitThread.start();
    }

    /**
     * Subscribe to receive updated from this controller.
     * 
     * @param subscriber A class which will directly receive updates from this
     *        controller.
     */
    public void subscribe (AndroidListener subscriber) {
        myListeners.add(subscriber);
    }

    /**
     * notify all subscribers of the button event
     * 
     * @param buttonEvent the event that just occurred.
     */
    protected void notify (AndroidButtonEvent buttonEvent) {
        for (AndroidListener listener : myListeners) {
            listener.onScreenPress(buttonEvent);
        }
    }

    /**
     * notify all subscribers of the joystick event
     * 
     * @param buttonEvent the event that just occurred.
     */
    protected void notify (JoyStickEvent joystickEvent) {
        for (AndroidListener listener : myListeners) {
            listener.onJoyStickMove(joystickEvent);
        }
    }

    protected void notifyDisconnect () {
        for (AndroidListener listener : myListeners) {
            listener.onControllerDisconnect();
        }
    }

    /**
     * Notify all subscribers of the line segment from an android touch screen.
     * 
     * @param l the line segment to be shared.
     */
    protected void notify (LineSegment l) {
        for (AndroidListener listener : myListeners) {
            listener.onTouchMovement(l);
        }

    }

    /**
     * Notify all subscribers of the sensor event to be shared.
     * 
     * @param e the sensor event to be shared
     */
    public void notify (AndroidSensorEvent e) {
        for (AndroidListener listener : myListeners) {
            listener.onAccelerometerEvent(e);
        }

    }

    protected UUID getActiveUUID () {

        // as controller numbers are controller numbers, they don't seem very
        // magical to me.
        switch (myControllerNumber) {
            case 1:
                return myServerID1;
            case 2:
                return myServerID2;
            case 3:
                return myServerID3;
            case 4:
                return myServerID4;
            default:
                return myServerID1;

        }
    }

    protected void setMessenger (Messenger messenger) {
        myMessenger = messenger;
        while (myMessageQueue.size() > 0) {
            myMessenger.write(myMessageQueue.remove());
            System.out.println("writing message");
        }
    }

    /**
     * Send the connected controller a message. If there is no controller
     * connected, the message is added to a queue to be sent once a connection
     * is established.
     * 
     * @param m the message to send to the controller
     * @return true if the message was immediately sent. False if it was added
     *         to the message queue.
     */
    public boolean notifyController (AndroidServerMessage m) {
        if (myMessenger != null) {
            myMessenger.write(m);
            return true;
        }
        else {
            myMessageQueue.add(m);
        }
        return false;
    }

}
