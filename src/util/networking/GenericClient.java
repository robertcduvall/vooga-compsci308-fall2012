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
public class GenericClient {

    private Socket myServer;
    private OutputStream myUserOutputStream;
    
    /**
     * 
     * @param host
     * @param port
     * @throws IOException
     */
    public GenericClient (String host, int port, OutputStream os) throws IOException {

        myServer = new Socket(host, port);
        myUserOutputStream = os;
        System.out.println("Connected to " + myServer.getInetAddress() + ":" + myServer.getPort());
        startListening();
    }
    
    private synchronized void fireEvent(ClientEvent ce){
        //To be implemented
    }
    
    private synchronized void addListener(ClientListener cl){
        
    }
    
    private synchronized void removeListener(ClientListener cl){
        
    }
    
    private void startListening() {
        Receiver r = new Receiver();
        r.start();
    }
    
    public void send(String text) {
        try {
            PrintWriter out = new PrintWriter(myServer.getOutputStream());
            out.println(text);
        }
        catch (IOException e) {
        }
    }
    
    class Receiver extends Thread{
        public void run () {
            try {
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(myServer.getInputStream()));
                PrintWriter toUser = new PrintWriter(myUserOutputStream);
                
                while (true) {
                    String input = fromServer.readLine();
                    toUser.println(input);
                    myServer.close();
                }
            }
            catch (IOException e) {
            }
        }
    }
}
