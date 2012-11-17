package util.input.android.bluetoothserver;

import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.microedition.io.StreamConnection;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidControllerEvent;


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
                AndroidControllerEvent androidEvent =
                        (AndroidControllerEvent) objectStream.readObject();
                handleObject(androidEvent);
                // System.out.println(androidEvent.getClass().toString()+"worked");

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleObject (AndroidControllerEvent androidEvent) {
        if (androidEvent.getClass() == AndroidButtonEvent.class) {
            AndroidButtonEvent b = (AndroidButtonEvent) androidEvent;
            myServer.notify(b);
        }

    }

}
