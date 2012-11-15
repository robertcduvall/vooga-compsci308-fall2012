package util.input.android.bluetoothserver;

public class TestServer {
    
    public static void main (String[] args){
        AndroidBluetoothServer server = new AndroidBluetoothServer(0);
        server.startServer();
        
      // KeyStroke k = KeyStroke.getKeyStroke(898989898, 0);
       //System.out.println(k.getKeyCode());
    }

}
