package vooga.shooter.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public abstract class AbstractServer {

    private static final int DEFAULT_PORT = 1235;
    private ServerSocket myServerSocket;
    private Socket myClientSocket;
    private PrintWriter myClientOutput;
    private BufferedReader myClientInput;
    private BufferedReader myServerInput;

    public void openServerSocket () {
        openServerSocket(DEFAULT_PORT);
    }

    public void openServerSocket (int port) {
        myServerSocket = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            System.out.println(hostname);
            myServerSocket = new ServerSocket(port);
        }
        catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }
    }

    public void initializeClientSocket () {
        myClientSocket = null;
        try {
            myClientSocket = myServerSocket.accept();
        }
        catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }

    private void shutDownServer () throws IOException {
        myClientOutput.println("server: closing connection");
        myClientOutput.close();
        myClientInput.close();
        myClientSocket.close();
        myServerSocket.close();
    }

    public void run () throws IOException {
        myClientOutput = new PrintWriter(myClientSocket.getOutputStream(), true);
        myClientInput = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
        String input;

        myClientOutput.println("server: Connected");
        myServerInput = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            input = myClientInput.readLine().trim();
            if ("STOP".equals(input)) {
                break;
            }
            interpretClientInput();
        }
        shutDownServer();
    }

    public abstract void interpretClientInput ();
    
    public abstract void createGameWithClients(ClientReference c1, ClientReference c2);
}
