/*
 * Copyright (c) 2004 David Flanagan. All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book,
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package util.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;


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

    // Hashtable mapping ports to Listeners
    private Map<Integer, Listener> myServices;

    // The set of current connections
    private Set<Connection> myConnections;

    // The concurrent connection limit
    private int myMaxConnections;

    // The threadgroup for all our threads
    private ThreadGroup myThreadGroup;

    /**
     * This constructor supports no logging
     **/
    public Server (int maxConnections) {
        myThreadGroup = new ThreadGroup(Server.class.getName());
        this.myMaxConnections = maxConnections;
        myServices = new HashMap<Integer, Listener>();
        myConnections = new HashSet<Connection>(maxConnections);
    }

    /**
     * This method makes the server start providing a new service.
     * It runs the specified Service object on the specified port.
     * 
     * @param service
     * @param port
     **/
    public synchronized void addService (Service service, int port) throws IOException {
        // the hashtable key
        Integer key = new Integer(port);
        // Check whether a service is already on that port
        if (myServices.get(key) != null) { throw new IllegalArgumentException("Port " + port +
                                                                              " already in use."); }
        // Create a Listener object to listen for connections on the port
        Listener listener = new Listener(myThreadGroup, port, service);
        // Store it in the hashtable
        myServices.put(key, listener);
        // Start the listener running.
        listener.start();
    }

    /**
     * This method makes the server stop providing a service on a port.
     * It does not terminate any pending connections to that service, merely
     * causes the server to stop accepting new connections
     * 
     * @param port
     **/
    public synchronized void removeService (int port) {
        // hashtable key
        Integer key = new Integer(port);
        // Look up the Listener object for the port in the hashtable
        final Listener LISTENER = (Listener) myServices.get(key);
        if (LISTENER == null) { return; }
        // Ask the listener to stop
        LISTENER.pleaseStop();
        // Remove it from the hashtable
        myServices.remove(key);
    }

    /**
     * This nested Thread subclass is a "listener". It listens for
     * connections on a specified port (using a ServerSocket) and when it gets
     * a connection request, it calls the servers addConnection() method to
     * accept (or reject) the connection. There is one Listener for each
     * Service being provided by the Server.
     **/
    public class Listener extends Thread {
        ServerSocket listen_socket;    // The socket to listen for connections
        int port;                      // The port we're listening on
        Service service;               // The service to provide on that port
        volatile boolean stop = false; // Whether we've been asked to stop

        /**
         * The Listener constructor creates a thread for itself in the
         * threadgroup. It creates a ServerSocket to listen for connections
         * on the specified port. It arranges for the ServerSocket to be
         * interruptible, so that services can be removed from the server.
         * 
         * @param group
         * @param port
         * @param service
         **/
        public Listener (ThreadGroup group, int port, Service service) throws IOException {
            super(group, "Listener:" + port);
            listen_socket = new ServerSocket(port);
            // give it a non-zero timeout so accept() can be interrupted
            listen_socket.setSoTimeout(5000);
            this.port = port;
            this.service = service;
        }

        /**
         * This is the polite way to get a Listener to stop accepting
         * connections
         ***/
        public void pleaseStop () {
            // Set the stop flag
            this.stop = true;
            // Stop blocking in accept()
            this.interrupt();
            try {
                listen_socket.close();
            } // Stop listening.
            catch (IOException e) {
            }
        }

        /**
         * A Listener is a Thread, and this is its body.
         * Wait for connection requests, accept them, and pass the socket on
         * to the addConnection method of the server.
         **/
        public void run () {
            // loop until we're asked to stop.
            while (!stop) {
                try {
                    Socket client = listen_socket.accept();
                    addConnection(client, service);
                }
                catch (InterruptedIOException e) {
                }
                catch (IOException e) {
                    System.out.println("Cannot add connection.");
                    System.out.println(e.getStackTrace());
                }
            }
        }
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
                // Then tell the client it is being rejected.
                PrintWriter out = new PrintWriter(s.getOutputStream());
                out.print("Connection refused; "
                          + "the server is busy; please try again later.\r\n");
                out.flush();
                // And close the connection to the rejected client.
                s.close();
            }
            catch (IOException e) {
                System.out.println("Cannot create PrintWriter from socket's OutputStream");
                System.out.println(e.getStackTrace());
            }
        }
        // Otherwise, if the limit has not been reached
        else {
            // Create a Connection thread to handle this connection
            Connection c = new Connection(s, service);
            // Add it to the list of current connections
            myConnections.add(c);
            // And start the Connection thread to provide the service
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

    /** Change the current connection limit */
    public synchronized void setMaxConnections (int max) {
        myMaxConnections = max;
    }

    /**
     * This method displays status information about the server on the
     * specified stream. It can be used for debugging, and is used by the
     * Control service later in this example.
     **/
    public synchronized void displayStatus (PrintWriter out) {
        // Display a list of all Services that are being provided
        Iterator<Integer> keys = myServices.keySet().iterator();
        while (keys.hasNext()) {
            Integer port = (Integer) keys.next();
            Listener listener = (Listener) myServices.get(port);
            out.print("SERVICE " + listener.service.getClass().getName() + " ON PORT " + port +
                      "\r\n");
        }

        // Display the current connection limit
        out.print("MAX CONNECTIONS: " + myMaxConnections + "\r\n");

        // Display a list of all current connections
        Iterator<Connection> conns = myConnections.iterator();
        while (conns.hasNext()) {
            Connection c = (Connection) conns.next();
            out.print("CONNECTED TO " + c.myClient.getInetAddress().getHostAddress() + ":" +
                      c.myClient.getPort() + " ON PORT " + c.myClient.getLocalPort() + " FOR SERVICE " +
                      c.myService.getClass().getName() + "\r\n");
        }
    }

    /**
     * This class is a subclass of Thread that handles an individual
     * connection between a client and a Service provided by this server.
     * Because each such connection has a thread of its own, each Service can
     * have multiple connections pending at once. Despite all the other
     * threads in use, this is the key feature that makes this a
     * multi-threaded server implementation.
     **/
    public class Connection extends Thread {
     // The socket to talk to the client through
        private Socket myClient;
     // The service being provided to that client
        private Service myService;

        /**
         * This constructor just saves some state and calls the superclass
         * constructor to create a thread to handle the connection. Connection
         * objects are created by Listener threads. These threads are part of
         * the server's ThreadGroup, so all Connection threads are part of that
         * group, too.
         **/
        public Connection (Socket client, Service service) {
            super("Server.Connection:" + client.getInetAddress().getHostAddress() + ":" +
                  client.getPort());
            this.myClient = client;
            this.myService = service;
        }

        /**
         * This is the body of each and every Connection thread.
         * All it does is pass the client input and output streams to the
         * serve() method of the specified Service object. That method is
         * responsible for reading from and writing to those streams to
         * provide the actual service. Recall that the Service object has
         * been passed from the Server.addService() method to a Listener
         * object to the addConnection() method to this Connection object, and
         * is now finally being used to provide the service. Note that just
         * before this thread exits it always calls the endConnection() method
         * to remove itself from the set of connections
         **/
        public void run () {
            try {
                InputStream in = myClient.getInputStream();
                OutputStream out = myClient.getOutputStream();
                myService.serve(in, out);
            }
            catch (IOException e) {
                System.out.println("Socket Input or Output Stream in non-blocking mode.");
                System.out.println(e.getStackTrace());
            }
            finally {
                endConnection(this);
            }
        }
    }
}