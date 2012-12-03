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
     * 
     * @param host
     * @param port
     * @throws IOException
     */
    public Client (String host, int port) throws IOException {

        myServer = new Socket(host, port);
        System.out.println("Connected to " + myServer.getInetAddress() + ":" + myServer.getPort());
        startListening();
    }
    
    private void startListening() {
        Receiver r = new Receiver();
        r.start();
    }
    
    public void send(String text) {
        try {
            System.out.println("SENDING" + text);
            PrintWriter out = new PrintWriter(myServer.getOutputStream());
            out.println(text);
        }
        catch (IOException e) {
        }
    }
    
    public abstract void processInputFromServer(String input);
    
    class Receiver extends Thread{
        public void run () {
            try {
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(myServer.getInputStream()));
                
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
