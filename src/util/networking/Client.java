package util.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * This program connects to a server at a specified host and port.
 * It reads text from the console and sends it to the server.
 * It reads text from the server and sends it to the console.
 * 
 * @author Copyright (c) 2004 David Flanagan
 * @author Simplified and modified by Connor Gordon and Oren Bukspan
 **/
public abstract class Client {

    private Socket myServer;

    /**
     * Connects the Client to the Server at a specified Port
     * 
     * @param host server address
     * @param port server access point
     * @throws IOException
     */
    public Client (String host, int port) throws IOException {

        myServer = new Socket(host, port);
        System.out.println("Connected to " + myServer.getInetAddress() + ":" + myServer.getPort());
        startListening();
    }

    private void startListening () {
        Receiver r = new Receiver();
        r.start();
    }

    /**
     * Write data from Client to the Server
     * 
     * @param text data that is outputted to server
     */
    public void send (String text) {
        try {
            System.out.println("SENDING" + text);
            PrintWriter out = new PrintWriter(myServer.getOutputStream());
            out.println(text);
            out.flush();
        }
        catch (IOException e) {
        }
    }

    /**
     * Given input from the Server, process it appropriately
     * 
     * @param input Server data streamed to client
     */
    public abstract void processInputFromServer (String input);

    /**
     * A Thread that continuously processes ServerInput
     * 
     * @author Oren Bukspan
     * 
     */
    class Receiver extends Thread {
        /**
         * Starts the thread and allows it to run infinitely.
         */
        public void run () {
            try {
                BufferedReader fromServer =
                        new BufferedReader(new InputStreamReader(myServer.getInputStream()));

                while (true && fromServer != null) {
                    String input = fromServer.readLine();
                    processInputFromServer(input);
                }

                myServer.close();
            }
            catch (IOException e) {
            }
        }
    }
}
