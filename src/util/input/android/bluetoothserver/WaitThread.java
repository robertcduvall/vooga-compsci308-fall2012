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
 * This thread waits for a bluetooth connection from an Android phone to be established.
 * 
 * @author Ben Schwab on top of BlueCove open source project. 
 *(There are a handful of undocumented classes from the BlueCove API, therefore I don't know how to best handle their errors
 *as their API is not well documented.)
 */
public class WaitThread implements Runnable{
    
    private UUID myServerID;
    private AndroidBluetoothServer myServer;
    
    /** Constructor */
    public WaitThread(int controllerNumber, AndroidBluetoothServer server) {
        myServer = server;
        determineServerID(controllerNumber);
    }

    private void determineServerID (int controllerNumber) {
        switch(controllerNumber){
            case AndroidBluetoothServer.CONTROLLER_ONE:
                myServerID =  new UUID("04c6093b00001000800000805f9b34fb", false);
                break;
            case AndroidBluetoothServer.CONTROLLER_TWO:
                myServerID =  new UUID("14c6093b00001000800000805f9b34fb", false);
                break;
            case AndroidBluetoothServer.CONTROLLER_THREE:
                myServerID =  new UUID("24c6093b00001000800000805f9b34fb", false);
                break;
            case AndroidBluetoothServer.CONTROLLER_FOUR:
                myServerID =  new UUID("34c6093b00001000800000805f9b34fb", false);
                break;
        }
        
    }

    @Override
    public void run() {
        waitForConnection();		
    }

    /** Waiting for connection from devices */
    private void waitForConnection() {
        // retrieve the local Bluetooth device object
        LocalDevice local = null;

        StreamConnectionNotifier notifier;
        StreamConnection connection = null;

        // setup the server to listen for connection
        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            //UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
            System.out.println(myServerID.toString());

            String url = "btspp://localhost:" + myServerID.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier)Connector.open(url);
        } catch (BluetoothStateException e) {
            System.out.println("Bluetooth is not turned on.");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            //Throw server error
            e.printStackTrace();
            return;
        }

        // waiting for connection
            try {
                System.out.println("waiting for connection...");
                connection = notifier.acceptAndOpen();
                Thread processThread = new Thread(new ProcessConnectionThread(connection, myServer));
                processThread.start();

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
    }
}
