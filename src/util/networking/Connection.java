package util.networking;

import java.net.Socket;

/**
 * This class is a subclass of Thread that handles an individual
 * connection between a client and a Service provided by this server.
 **/
public class Connection extends Thread {
    private Socket myClient;
    private Service myService;
    private Server myServer;

    /**
     * This constructor stores the state from the parameters and names the 
     * thread accordingly. Calls the Thread constructor to create a thread
     * to handle the connection.
     * @param client The Socket that is to be represented by this connection.
     * @param service The service that will interact with the client.
     * @param server The server creating this Connection.
     **/
    public Connection (Socket client, Service service, Server server) {
        super("Server.Connection:" + client.getInetAddress().getHostAddress() + ":" +
              client.getPort());
        this.myClient = client;
        this.myService = service;
        this.myServer = server;
    }

    /**
     * This is the body of each and every Connection thread.
     * It calls the serve() method of the specified Service object with a 
     * Socket and a reference to this server. Before this thread exits it 
     * always calls the endConnection() method to remove itself from the 
     * set of connections.
     **/
    public void run () {
        myService.serve(myClient, myServer);
        myServer.endConnection(this);
    }
}
