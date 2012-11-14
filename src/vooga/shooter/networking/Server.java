package vooga.shooter.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {

    private List<Socket> myClientSockets;
    //private List<Object> myGames;

    public Server () throws IOException {
        ServerSocket serverSocket = null;
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
        
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                myClientSockets.add(clientSocket);
            }
            catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            if (myClientSockets.size() == 2) {
                startGame(myClientSockets.remove(0), myClientSockets.remove(0));
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
