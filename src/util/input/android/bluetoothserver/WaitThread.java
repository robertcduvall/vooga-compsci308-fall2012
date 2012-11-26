package util.input.android.bluetoothserver;

import java.io.IOException;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;


/**
 * This thread waits for a bluetooth connection from an Android phone to be
 * established.
 * 
 * @author Ben Schwab on top of BlueCove open source project.
 *         (There are a handful of undocumented classes from the BlueCove API,
 *         therefore I don't know how to best handle their errors
 *         as their API is not well documented.)
 */
public class WaitThread implements Runnable {

    private AndroidBluetoothServer myServer;
   

    /**
     * Create a thread to wait for a connection
     * @param controllerNumber the number of the connected controller
     * @param server the server this thread belongs to
     */
    public WaitThread (int controllerNumber, AndroidBluetoothServer server) {
        myServer = server;

    }

    @Override
    public void run () {
        waitForConnection();
    }

    /**
     * Wait for conenction from android phone.
     */
    private void waitForConnection () {
        LocalDevice local = null;
        StreamConnectionNotifier notifier;
        StreamConnection connection = null; 
        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);
            String url = "btspp://localhost:" + myServer.getActiveUUID().toString() + ";name=RemoteBluetooth";
            System.out.println("btspp://localhost:" + myServer.getActiveUUID().toString() + ";name=RemoteBluetooth");
            notifier = (StreamConnectionNotifier) Connector.open(url);
        }
        catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not turned on.");
            e.printStackTrace();
            return;
        }
        catch (IOException e) {
            // Throw server error
            e.printStackTrace();
            return;
        }

        // waiting for connection
        try {
            //System.out.println("waiting for connection...");
            connection = notifier.acceptAndOpen();
            Thread processThread = new Thread(new ProcessConnectionThread(connection, myServer));
            processThread.start();
            myServer.setMessenger(new Messenger(connection));
          


        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

   
}
