package util.input.android.bluetoothserver;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.microedition.io.StreamConnection;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidControllerEvent;



/**
 * 
 * @author BlueCove opensource Android project
 * Modified by Ben Schwab
 *
 */

public class ProcessConnectionThread implements Runnable{


    private AndroidCommandProcessor myProcessor;
    private StreamConnection mConnection;

    // Constant that indicate command from devices
    private static final int EXIT_CMD = -1;
    private static final int KEY_RIGHT = 1;
    private static final int KEY_LEFT = 2;
    private AndroidBluetoothServer myServer;

    public ProcessConnectionThread(StreamConnection connection, AndroidBluetoothServer server)
    {
        mConnection = connection;
        myProcessor = new AndroidGameBoyCommandProcessor();
        myServer = server;
    }

    @Override
    public void run() {
        try {

            // prepare to receive data


            //DataInputStream objectInputStream = mConnection.openDataInputStream();
            //byte[] myObject = new byte[1024];
            //  objectInputStream.readFully(myObject);
            InputStream inputStream = mConnection.openInputStream();
            System.out.println("waiting for input");

            while (true) {
                //int command = inputStream.read();
                AndroidControllerEvent t = new AndroidControllerEvent();

                ObjectInputStream objectStream = new ObjectInputStream(inputStream);

                AndroidControllerEvent androidEvent= (AndroidControllerEvent)objectStream.readObject();
                // objectStream.reset();
                //inputStream.close();
                //objectStream.
                handleObject(androidEvent);
                System.out.println(androidEvent.getClass().toString()+"worked");
               // processCommand(androidEvent);

                /*if (command == EXIT_CMD)
                {	
                    System.out.println("finish process");
                    break;
                }

                processCommand(command); */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleObject (AndroidControllerEvent androidEvent) {
        if(androidEvent.getClassName() == AndroidButtonEvent.class){
            AndroidButtonEvent b = (AndroidButtonEvent) androidEvent;
            myServer.notify(b);
            System.out.println( b.getEventCode());
            System.out.println("called notify");
        }

    }

 

    /**
     * Process the command from client
     * @param command the command code
     */
    private void processCommand(AndroidButtonEvent event) {
        myServer.notify(event);
    }
}
