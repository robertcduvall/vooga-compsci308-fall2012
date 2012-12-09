package util.networking;

import java.net.Socket;


/**
 * The Service interface has only one method - the serve() method.
 * It takes in a socket and a server and is designed to be called
 * by a Connection - with each Socket representing one Connection.
 * 
 * @author Oren Bukspan
 * @author Connor Gordon
 **/
public interface Service {

    /**
     * Designed to be called by a Connection - with each Socket
     * representing a single client. The method should handle
     * interactions with the clients I/O streams.
     * 
     * @param socket The Socket representing a single client.
     * @param server The Server that this service is running on.
     */
    void serve (Socket socket, Server server);

}
