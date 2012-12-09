package util.networking.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import util.networking.Server;
import util.networking.Service;


/**
 * 
 * @author Oren Bukspan
 */
public class ChatService implements Service {

    private ChatProtocol myProtocol;
    private ChatServer myServer;
    private Map<String, Socket> myUsersToSockets;

    /**
     * Creates a chat service with the given ChatProtocol
     * 
     * @param protocol The ChatProtocol to run on this server.
     */
    public ChatService (ChatProtocol protocol) {
        myProtocol = protocol;
        myUsersToSockets = Collections.synchronizedMap(new HashMap<String, Socket>());
    }

    @Override
    public void serve (Socket socket, Server server) {
        System.out.println("Server adding client " + socket.getInetAddress());
        myServer = (ChatServer) server;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
        }

        while (true && in != null) {
            try {
                String input = in.readLine();
                if (input != null && !"".equals(input.trim())) {
                    System.out.println("Server received from " + socket.getInetAddress() + ": " +
                                       input);
                    ChatCommand type = myProtocol.getType(input);
                    Method m;
                    m =
                            this.getClass().getDeclaredMethod(type.getMethodName(), String.class,
                                                              Socket.class);
                    m.setAccessible(true);
                    m.invoke(this, input, socket);
                }
            }
            catch (IOException e) {
                if ("Connection reset".equals(e.getMessage())) {
                    break;
                }
            }
            catch (SecurityException e) {
                System.out.println("2: " + e.getMessage());
            }
            catch (NoSuchMethodException e) {
                System.out.println("3: " + e.getMessage());
            }
            catch (IllegalArgumentException e) {
                System.out.println("4: " + e.getMessage());
            }
            catch (IllegalAccessException e) {
                System.out.println("5: " + e.getMessage());
            }
            catch (InvocationTargetException e) {
                System.out.println("6: " + e.getMessage());
            }
        }
        try {
            logout(socket);
        }
        catch (IOException e) {
        }
    }

    private void logout (Socket socket) throws IOException {
        // remove the user from the list and close the socket
        System.out.println("Server removing client " + socket.getInetAddress());
        synchronized (myUsersToSockets) {
            for (String user : myUsersToSockets.keySet()) {
                if (myUsersToSockets.get(user).equals(socket)) {
                    removeUser(user, socket);
                    break;
                }
            }
        }
        socket.close();
    }

    @SuppressWarnings("unused")
    private void processLogout (String input, Socket socket) {
        String user = myProtocol.getUser(input);
        if (authorized(user, socket)) {
            removeUser(user, socket);
            write(socket, myProtocol.createLoggedIn(user, false));
            try {
                socket.close();
            }
            catch (IOException e) {
            }
        }
        else {
            write(socket, myProtocol.createError("Not authorized to log out user " + user + "."));
        }
    }

    @SuppressWarnings("unused")
    private void processLogin (String input, Socket socket) {
        String user = myProtocol.getUser(input);
        String password = myProtocol.getPassword(input);

        if (authorized(user, socket)) {
            write(socket, myProtocol.createError("User already logged in."));
        }
        else if (myServer.login(user, password)) {
            write(socket, myProtocol.createLoggedIn(user, true));
            addUser(user, socket);
            synchronized (myUsersToSockets) {
                write(socket, myProtocol.createListUsers(Arrays.asList(myUsersToSockets.keySet()
                        .toArray(new String[0]))));
            }
        }
        else {
            write(socket, myProtocol.createLoggedIn(user, false));
        }
    }

    @SuppressWarnings("unused")
    private void processRegister (String input, Socket socket) {
        String user = myProtocol.getUser(input);
        String password = myProtocol.getPassword(input);

        synchronized (this) {
            if (myServer.hasUser(user)) {
                write(socket, myProtocol.createError("Username unavailable."));
            }
            else {
                myServer.addUser(user, password);
                myServer.login(user, password);
                write(socket, myProtocol.createLoggedIn(user, true));
                addUser(user, socket);
                synchronized (myUsersToSockets) {
                    write(socket, myProtocol.createListUsers(Arrays.asList(myUsersToSockets
                            .keySet().toArray(new String[0]))));
                }
            }
        }
    }

    @SuppressWarnings("unused")
    private void processMessage (String input, Socket socket) {
        String to = myProtocol.getTo(input);
        String from = myProtocol.getFrom(input);

        if (authorized(from, socket)) {
            synchronized (myUsersToSockets) {
                Socket dest = myUsersToSockets.get(to);
                write(dest, input);
            }
        }
        else {
            write(socket,
                  myProtocol.createError("User not authorized to send message from username " +
                                         from + "."));
        }
    }

    @SuppressWarnings("unused")
    private void processUnknown (String input, Socket socket) {
        // log the input
        System.out.println("unrecognized command from: " + socket.getInetAddress() + ": " + input);
    }

    private void addUser (String user, Socket socket) {
        synchronized (myUsersToSockets) {
            // notify all clients of new user
            for (Socket s : myUsersToSockets.values()) {
                write(s, myProtocol.createAddUser(user));
            }
            // add new user to list
            myUsersToSockets.put(user, socket);
        }
    }

    private void removeUser (String user, Socket socket) {
        // remove user from list
        myUsersToSockets.remove(user);
        // notify all clients to remove user
        for (Socket s : myUsersToSockets.values()) {
            write(s, myProtocol.createRemoveUser(user));
        }
    }

    private boolean authorized (String user, Socket socket) {
        synchronized (myUsersToSockets) {
            Socket s = myUsersToSockets.get(user);
            // if user is logged in, compare if sockets are equal
            if (s != null) { return s.equals(socket); }
        }
        // user is not logged in
        return false;
    }

    /**
     * Simplifies writing text to a socket.
     * 
     * @param socket Socket to write to.
     * @param text Text to write to the Socket's output stream.
     */
    private void write (Socket socket, String text) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(text);
            out.flush();
            System.out.println("Server sending to " + socket.getInetAddress() + ": " + text);
        }
        catch (IOException e) {
        }
    }

    /**
     * Get the requested port for the protocol.
     * 
     * @return Requested port for the protocol.
     */
    public int getPort () {
        return myProtocol.getPort();
    }
}
