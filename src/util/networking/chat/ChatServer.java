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

    /**
     * 
     * @param logStream
     * @param maxConnections
     */
    public ChatServer(OutputStream logStream, int maxConnections) {
        super(logStream, maxConnections);
    }

    /**
     * 
     * @param logger
     * @param logLevel
     * @param maxConnections
     */
    public ChatServer (Logger logger, Level logLevel, int maxConnections) {
        super(logger, logLevel, maxConnections);
    }

    
    
    
    
}

