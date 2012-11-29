package util.networking.chat;

import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.networking.Server;

/**
 *
 * @author Oren Bukspan
 */
public class ChatServer extends Server {

    private List<ClientService> myClients;
    
    /**
     * 
     * @param maxConnections
     */
    public ChatServer (int maxConnections) {
        super(maxConnections);
    }
}

