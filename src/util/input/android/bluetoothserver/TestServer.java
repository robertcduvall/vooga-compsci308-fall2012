package util.input.android.bluetoothserver;

public class TestServer {

    public static void main(String[] args) {
        AndroidBluetoothServer server = new AndroidBluetoothServer(0);
        server.startServer();
    }

}
