package util.input.android.bluetoothserver;

public class AndroidBluetoothServer {

    private Thread myWaitThread;
    public static final int CONTROLLER_ONE = 0;
    public static final int CONTROLLER_TWO = 1;
    public static final int CONTROLLER_THREE = 2;
    public static final int CONTROLLER_FOUR = 3;

    public AndroidBluetoothServer(int controllerNumber) {
        myWaitThread = new Thread(new WaitThread(controllerNumber));
    }

    public void startServer() {
        myWaitThread.start();
    }

}
