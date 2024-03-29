package vooga.shooter.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {

    private static int DEFAULT_MAX_CONNECTIONS = Integer.MAX_VALUE;
    private int myMaxConnections;
    private List<Socket> myUnassignedClientSockets;
    private List<Socket> myClientSockets;
    //private List<Object> myGames;

    public Server() throws IOException {
        this(DEFAULT_MAX_CONNECTIONS);
    }
    
    public Server (int maxConnections) throws IOException {
        ServerSocket serverSocket = null;
        myMaxConnections = maxConnections;
        
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            System.out.println(hostname);
            serverSocket = new ServerSocket(1235);
        }
        catch (IOException e) {
            System.err.println("Could not listen on port: 1235.");
            System.exit(1);
        }

        BufferedReader serverInput =  new BufferedReader(new InputStreamReader(System.in));
        
        while (myClientSockets.size() < myMaxConnections) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                myUnassignedClientSockets.add(clientSocket);
                myClientSockets.add(clientSocket);
            }
            catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            if (myUnassignedClientSockets.size() == 2) {
                startGame(myUnassignedClientSockets.remove(0), myUnassignedClientSockets.remove(0));
            }
            if ("exit".equals(serverInput.readLine())) {
                break;
            }
        }
        serverSocket.close();
    }

    private void startGame (Socket client1, Socket client2) throws IOException {
        Game g = new Game(client1, client2, new OnlineLevel());
        new Thread(g).start();
    }

    public static void main (String[] args) throws IOException {
        new Server();
    }
}
