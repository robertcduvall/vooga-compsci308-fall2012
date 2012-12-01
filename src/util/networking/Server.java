package util.networking;


import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * This class is a generic framework for a flexible, multi-threaded server.
 * It listens on any number of specified ports, and, when it receives a
 * connection on a port, passes input and output streams to a specified Service
 * object which provides the actual service. It can limit the number of
 * concurrent connections, and logs activity to a specified stream.
 * 
 * @author Copyright (c) 2004 David Flanagan
 * @author Simplified and modified by Connor Gordon and Oren Bukspan
 **/
public class Server {

    private static final int DEFAULT_MAX_CONNECTIONS = 100;
    private static final int TIMEOUT = 5000;

    private Map<Integer, Listener> myServices;
    private Set<Connection> myConnections;
    private int myMaxConnections;
    private ThreadGroup myThreadGroup;

    /**
     * Instantiates a server with at most DEFAULT_MAX_CONNECTIONS connections.
     * @throws UnknownHostException Could not determine HostName.
     */
    public Server () throws UnknownHostException {
        this(DEFAULT_MAX_CONNECTIONS);
    }

    /**
     * Instantiates a server with at most maxConnections connections.
     * 
     * @param maxConnections The maximum number of connections to this server.
     * @throws UnknownHostException Could not determine HostName.
     **/
    public Server (int maxConnections) throws UnknownHostException {
        myThreadGroup = new ThreadGroup(Server.class.getName());
        this.myMaxConnections = maxConnections;
        myServices = new HashMap<Integer, Listener>();
        myConnections = new HashSet<Connection>(maxConnections);

        //Print host name - to make sure we have correct address
        InetAddress addr = InetAddress.getLocalHost();
        String hostname = addr.getHostName();
        System.out.println(hostname);
    }

    /**
     * This method makes the server start providing a new service.
     * It runs the specified Service object on the specified port.
     * 
     * @param service The Service object to run on the server.
     * @param port The port on which to run this service.
     * @throws IOException if the specified port is already in use on this
     *         server.
     **/
    public synchronized void addService (Service service, int port) throws IOException {
        Integer key = new Integer(port);
        if (myServices.get(key) != null) { 
            throw new IllegalArgumentException("Port " + port + " already in use."); 
        }
        Listener listener = new Listener(myThreadGroup, port, service);
        myServices.put(key, listener);
        listener.start();
    }

    /**
     * This method makes the server stop providing a service on a port.
     * It does not terminate any pending connections to that service, merely
     * causes the server to stop accepting new connections
     * 
     * @param port The port from which to remove a service.
     **/
    public synchronized void removeService (int port) {
        Integer key = new Integer(port);
        final Listener LISTENER = (Listener) myServices.get(key);
        if (LISTENER == null) { 
            return; 
        }
        LISTENER.pleaseStop();
        myServices.remove(key);
    }

    /**
     * This is the method that Listener objects call when they accept a
     * connection from a client. It either creates a Connection object
     * for the connection and adds it to the list of current connections,
     * or, if the limit on connections has been reached, it closes the
     * connection.
     **/
    protected synchronized void addConnection (Socket s, Service service) {
        // If the connection limit has been reached
        if (myConnections.size() >= myMaxConnections) {
            try {
                PrintWriter out = new PrintWriter(s.getOutputStream());
                out.print("Connection refused; " +
                           "the server is busy; please try again later.\r\n");
                out.flush();
                s.close();
            }
            catch (IOException e) {
                System.out.println("Cannot create PrintWriter from socket's OutputStream");
                System.out.println(e.getStackTrace());
            }
        }
        else {
            Connection c = new Connection(s, service, this);
            myConnections.add(c);
            c.start();
        }
    }

    /**
     * A Connection thread calls this method just before it exits. It removes
     * the specified Connection from the set of connections.
     **/
    protected synchronized void endConnection (Connection c) {
        myConnections.remove(c);
    }

    /**
     * Change the current connection limit
     * @param max The maximum number of connections.
     * */
    public synchronized void setMaxConnections (int max) {
        myMaxConnections = max;
    }

    /**
     * This nested Thread subclass is a "listener". It listens for
     * connections on a specified port and calls addConnection() to
     * process the connection according to Server specifications. There 
     * is one Listener for each Service being provided by the Server.
     **/
    public class Listener extends Thread {
        private ServerSocket myListenSocket;
        private Service myService;
        private volatile boolean myStop = false;

        /**
         * On a new thread, creates a ServerSocket to listen for connections 
         * on the specified port. 
         *
         * @param group ThreadGroup to add this listener Thread to.
         * @param port Port on which to listen for connections.
         * @param service Service to call for every connection on this port.
         * @throws IOException Error creating Socket.
         **/
        public Listener (ThreadGroup group, int port, Service service) throws IOException {
            super(group, "Listener:" + port);
            myListenSocket = new ServerSocket(port);
            //myListenSocket.setSoTimeout(TIMEOUT);
            this.myService = service;
        }

        /**
         * This is the polite way to get a Listener to stop accepting
         * connections
         ***/
        public void pleaseStop () {
            this.myStop = true;
            this.interrupt();
            try {
                myListenSocket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Wait for connection requests, accept them, and pass the socket on
         * to the addConnection method of the server.
         **/
        public void run () {
            while (!myStop) {
                try {
                    Socket client = myListenSocket.accept();
                    addConnection(client, myService);
                }
                catch (InterruptedIOException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    System.out.println("Cannot add connection.");
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }
}
