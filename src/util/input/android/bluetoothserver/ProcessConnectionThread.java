package util.input.android.bluetoothserver;

import java.io.InputStream;


/**
 * 
 * @author BlueCove opensource Android project
 *         Modified by Ben Schwab
 * 
 */

public class ProcessConnectionThread implements Runnable {

    private AndroidCommandProcessor myProcessor;
    private StreamConnection mConnection;

    // Constant that indicate command from devices
    private static final int EXIT_CMD = -1;
    private static final int KEY_RIGHT = 1;
    private static final int KEY_LEFT = 2;

    public ProcessConnectionThread(StreamConnection connection) {
        mConnection = connection;
        myProcessor = new AndroidGameBoyCommandProcessor();
    }

    @Override
    public void run() {
        try {

            // prepare to receive data
            InputStream inputStream = mConnection.openInputStream();

            System.out.println("waiting for input");

            while (true) {
                int command = inputStream.read();

                if (command == EXIT_CMD) {
                    System.out.println("finish process");
                    break;
                }

                processCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process the command from client
     * 
     * @param command the command code
     */
    private void processCommand(int command) {
        myProcessor.processCommand(command);
    }
}
