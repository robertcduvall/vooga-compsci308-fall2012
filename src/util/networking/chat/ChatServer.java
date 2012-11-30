package util.networking.chat;

import java.io.IOException;
import java.util.Map;
import util.networking.Connection;
import util.networking.Server;


/**
 * 
 * @author Oren Bukspan
 */
public class ChatServer extends Server {

    private Map<String, Connection> myClients;

    /**
     * Instantiates a new ChatServer with
     * util.networking.Server.DEFAULT_MAX_CONNECTIONS connections.
     */
    public ChatServer () {
        super();
    }

    /**
     * Instantiates a new ChatServer with at most maxConnections connections to
     * the server.
     * 
     * @param maxConnections the maximum number of connections to the server
     */
    public ChatServer (int maxConnections) {
        super(maxConnections);
    }

    /**
     * 
     * @param cp
     * @param maxConnections
     * @throws IOException
     */
    public ChatServer (ChatProtocol cp, int maxConnections) throws IOException {
        super(maxConnections);
        ChatService myChatService = new ChatService(cp);
        this.addService(myChatService, myChatService.getPort());
    }
}
