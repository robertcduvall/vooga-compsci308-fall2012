package util.networking.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import util.networking.Server;
import util.networking.Service;

/**
 *
 * @author Oren Bukspan
 */
public class ChatService implements Service {

    private ChatProtocol myProtocol;
    private ChatServer myServer;
    private Map<String, Socket> myUsersToSockets;

    public ChatService(ChatProtocol cp) {
        myProtocol = cp;
    }

    @Override
    public void serve (Socket socket, Server server) {
        myServer = (ChatServer) server;
        
        
    }

    /**
     * Get the requested port for the protocol.
     * @return Requested port for the protocol.
     */
    public int getPort () {
        return myProtocol.getPort();
    }
}
