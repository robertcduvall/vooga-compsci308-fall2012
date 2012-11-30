package util.networking.chat;

import java.io.IOException;
import java.net.UnknownHostException;
import util.networking.Server;

/**
 * 
 * @author Oren Bukspan
 */
public class ChatServer extends Server {

    private static final int DEFAULT_MAX_CONNECTIONS = 100;

    /**
     * Instantiates a new ChatServer that uses ChatProtocol protocol and
     * has at most DEFAULT_MAX_CONNECTIONS connections to the server.
     * 
     * @param protocol The ChatProtocol that the server should use to chat.
     * @throws UnknownHostException Could not determine Server's HostName.
     */
    public ChatServer (ChatProtocol protocol) throws UnknownHostException {
        this(protocol, DEFAULT_MAX_CONNECTIONS);
    }

    /**
     * Instantiates a new ChatServer that uses ChatProtocol protocol and
     * has at most maxConnections connections to the server.
     * 
     * @param protocol The ChatProtocol that the server should use to chat.
     * @param maxConnections the maximum number of connections to the server
     * @throws UnknownHostException Could not determine Sever's HostName.
     */
    public ChatServer (ChatProtocol protocol, int maxConnections) throws UnknownHostException {
        super(maxConnections);
        ChatService myChatService = new ChatService(protocol);
        try {
            this.addService(myChatService, myChatService.getPort());
        }
        catch (IOException e) {
            System.out.println("Specified port is already in use.");
            e.printStackTrace();
        }
    }
}
