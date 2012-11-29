package util.input.android.bluetoothserver;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.microedition.io.StreamConnection;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidSensorEvent;
import util.input.android.events.JoyStickEvent;
import util.input.android.events.LineSegment;


/**
 * This thread will process information sent by an Android controller.
 * 
 * @author Ben Schwab using BlueCove API
 * 
 * 
 */

public class ProcessConnectionThread implements Runnable {

    private StreamConnection myConnection;
    private AndroidBluetoothServer myServer;

    /**
     * Create a new connection processor.
     * 
     * @param connection The active android connection.
     * @param server The server that holds this thread.
     */
    public ProcessConnectionThread (StreamConnection connection, AndroidBluetoothServer server) {
        myConnection = connection;
        myServer = server;
    }

    @Override
    public void run () {
        try {

            InputStream inputStream = myConnection.openInputStream();
            while (true) {
                ObjectInputStream objectStream = new ObjectInputStream(inputStream);
                Serializable androidEvent = (Serializable) objectStream.readObject();
                handleObject(androidEvent);

            }
        }
        // The Bluecove API did not specify the specific type of exceptions that
        // can be thrown. But an excpetion should break the conenction and
        // disconnect the controller.
        catch (Exception e) {
            myServer.notifyDisconnect();
        }
    }

    private void handleObject (Serializable androidEvent) {
        if (androidEvent instanceof AndroidButtonEvent) {
            AndroidButtonEvent b = (AndroidButtonEvent) androidEvent;
            myServer.notify(b);
        }
        if (androidEvent instanceof JoyStickEvent) {
            JoyStickEvent j = (JoyStickEvent) androidEvent;
            myServer.notify(j);
        }
        if (androidEvent instanceof LineSegment) {
            LineSegment l = (LineSegment) androidEvent;
            myServer.notify(l);
        }
        if (androidEvent instanceof AndroidSensorEvent) {
            AndroidSensorEvent e = (AndroidSensorEvent) androidEvent;
            myServer.notify(e);
        }

    }

}
