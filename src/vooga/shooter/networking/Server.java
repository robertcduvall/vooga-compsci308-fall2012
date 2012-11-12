package vooga.shooter.networking;

import java.net.*;
import java.io.*;


public class Server {
    
    public static void main (String[] args) throws IOException {


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

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader clientInput =
                new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String input, output = "";

        clientOutput.println("server: Connected");
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
            input = clientInput.readLine().trim();
            if (input.equals("STOP"))
                break;
            System.out.println(input);
            output = serverInput.readLine();
            clientOutput.println(output);
            if (output.equals("STOP"))
                break;
        }

        clientOutput.println("server: closing connection");
        clientOutput.close();
        clientInput.close();
        clientSocket.close();
        serverSocket.close();
    }
}
