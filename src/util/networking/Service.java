package util.networking;

import java.net.Socket;


/**
 *.
 * 
 * @author Oren Bukspan
 * @author Connor Gordon
 **/
public interface Service {

    /**
     * 
     * @param socket
     * @param server
     */
    public void serve (Socket socket, Server server);

}
