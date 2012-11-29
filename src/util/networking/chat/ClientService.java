package util.networking.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import util.networking.Service;

/**
 *
 * @author Oren Bukspan
 */
public class ClientService implements Service {

    private Socket mySocket;
    
    public ClientService(Socket s) {
        mySocket = s;
    }
    
    
    @Override
    public void serve (InputStream in, OutputStream out) throws IOException {
        // TODO Auto-generated method stub

    }

}
