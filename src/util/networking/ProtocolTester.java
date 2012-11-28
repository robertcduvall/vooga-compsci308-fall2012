package util.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProtocolTester {

    /**
     * A main() method for running the server as a standalone program.  The
     * command-line arguments to the program should be pairs of servicenames
     * and port numbers.  For each pair, the program will dynamically load the
     * named Service class, instantiate it, and tell the server to provide
     * that Service on the specified port.  The special -control argument
     * should be followed by a password and port, and will start special
     * server control service running on the specified port, protected by the
     * specified password.
     * @throws UnknownHostException 
     **/
    public static void main(String[] args) throws UnknownHostException {
        //Print host name - to make sure we have correct address
        InetAddress addr = InetAddress.getLocalHost();
        String hostname = addr.getHostName();
        System.out.println(hostname);
        Server s = new Server(Logger.getLogger(Server.class.getName()),
                              Level.INFO, 10);
        int port = 5222;
        /*try {
            s.addService(new Service(), port);
        }
        catch (IOException e) {
            e.printStackTrace();
        }*/
        
        
        ProtocolXMPP xmpp = new ProtocolXMPP();
        System.out.println(xmpp.openStream("server"));
    }
    
}
