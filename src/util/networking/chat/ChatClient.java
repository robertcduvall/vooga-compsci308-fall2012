package util.networking.chat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import util.networking.Client;


/**
 * A ChatClient allows users to communicate with each other from multiple
 * computers over a designated ChatServer using a defined protocol.
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */

public class ChatClient extends Client {

    private static final int TIMEOUT = 10000;

    private ChatProtocol myProtocol;
    private String myUser;
    private boolean myLoggedIn;
    private List<String> myListUsers;
    private List<ChatListener> myChatListeners;

    /**
     * Creates a ChatClient connected to a designated host, which will
     * pass/receive data using the following protocol
     * 
     * @param host the Server address
     * @param c the protocol which will encrypt client commands and decrypt
     *        server responses
     * @throws IOException if Socket cannot be opened properly
     */
    public ChatClient (String host, ChatProtocol c) throws IOException {
        super(host, c.getPort());
        myProtocol = c;
        myChatListeners = new ArrayList<ChatListener>();
        myListUsers = new ArrayList<String>();
        myLoggedIn = false;
    }

    /**
     * Notifies server that the user wants to log in
     */
    public void login (String user, String password) {
        send(myProtocol.createLogin(user, password));
    }

    /**
     * Notifies server that the user is logging out
     */
    public void logout () {
        send(myProtocol.createLogout(myUser));
    }

    /**
     * Sends a server-side request to register a new user
     * 
     * @param user name of new user
     * @param password new user's password
     */
    public void register (String user, String password) {
        send(myProtocol.createRegister(user, password));
    }

    /**
     * Makes registration request to server and waits a designated period of
     * time before checking log-in status
     * 
     * @param user name of user running client
     * @param password password of user running client
     * @param timeout period time between making request to server and checking
     *        status
     * @return whether or not the user is now logged in
     */
    public boolean registerWithTimeout (String user, String password, int timeout) {
        register(user, password);
        try {
            Thread.sleep(timeout);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (myLoggedIn) {
            myUser = user;
        }
        return myLoggedIn;
    }

    /**
     * Makes login request to server and waits a designated period of time
     * before checking log-in status
     * 
     * @param user name of user running client
     * @param password password of user running client
     * @param timeout period time between making request to server and checking
     *        status
     * @return whether or not the user is now logged in
     */
    public boolean loginWithTimeout (String user, String password, int timeout) {
        login(user, password);
        try {
            Thread.sleep(timeout);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (myLoggedIn) {
            myUser = user;
        }
        return myLoggedIn;
    }

    /**
     * Allows user to switch users within the same client
     * 
     * @param user new username
     * @param password new password
     */
    public void switchUser (String user, String password) {
        logout();
        login(user, password);
    }

    public void sendMessage (String userDest, String body) {
        send(myProtocol.createMessage(myUser, userDest, body));
    }

    /**
     * Returns a list containing the names of users who are currently online
     * 
     * @return list of online users
     */
    public List<String> getListUsers () {
        return myListUsers;
    }

    /**
     * Returns login status of user who is running this client
     * 
     * @return login status
     */
    public boolean getLoggedInStatus () {
        return myLoggedIn;
    }

    /**
     * Returns name of user who is running this client
     * 
     * @return username
     */
    public String getUserName () {
        return myUser;
    }

    @Override
    public void processInputFromServer (String input) {
        if (input == null || "".equals(input.trim())) return;
        System.out.println("client received: " + input);
        ChatCommand type = myProtocol.getType(input);
        Method m;
        try {
            m = this.getClass().getDeclaredMethod(type.getMethodName(), String.class);
            m.setAccessible(true);
            m.invoke(this, input);
        }
        catch (SecurityException e) {
        }
        catch (NoSuchMethodException e) {
        }
        catch (IllegalArgumentException e) {
        }
        catch (IllegalAccessException e) {
        }
        catch (InvocationTargetException e) {
        }
    }

    @SuppressWarnings("unused")
    private void processMessage (String input) {
        String from = myProtocol.getFrom(input);
        String to = myProtocol.getTo(input);
        String body = myProtocol.getBody(input);
        fireMessageReceivedEvent(to, from, body);
    }

    @SuppressWarnings("unused")
    private void processError (String input) {
        fireErrorEvent(myProtocol.getErrorMessage(input));
    }

    @SuppressWarnings("unused")
    private void processLoggedIn (String input) {
        myLoggedIn = myProtocol.getStatus(input);
    }

    @SuppressWarnings("unused")
    private void processListUsers (String input) {
        myListUsers = myProtocol.getListUsers(input);
    }

    @SuppressWarnings("unused")
    private void processAddUser (String input) {
        myListUsers.add(myProtocol.getUser(input));
        fireUsersUpdateEvent();
    }

    @SuppressWarnings("unused")
    private void processRemoveUser (String input) {
        myListUsers.remove(myProtocol.getUser(input));
        fireUsersUpdateEvent();
    }

    private synchronized void fireMessageReceivedEvent (String to, String from, String body) {
        MessageReceivedEvent e = new MessageReceivedEvent(this, to, from, body);
        for (ChatListener cl : myChatListeners) {
            cl.handleMessageReceivedEvent(e);
        }
    }

    private synchronized void fireErrorEvent (String message) {
        ErrorEvent e = new ErrorEvent(this, message);
        for (ChatListener cl : myChatListeners) {
            cl.handleErrorEvent(e);
        }
    }

    private void fireUsersUpdateEvent () {
        UsersUpdateEvent e = new UsersUpdateEvent(this);
        for (ChatListener cl : myChatListeners) {
            cl.handleUsersUpdateEvent(e);
        }
    }

    /**
     * Adds ChatListener to list of ChatListners that are iterated through when
     * an event is triggered
     * 
     * @param cl new ChatListener which will handle triggered events
     */
    public synchronized void addListener (ChatListener cl) {
        myChatListeners.add(cl);
    }

    /**
     * Removes ChatListener from list of ChatListeners iterated through when
     * event is triggered
     * 
     * @param cl reference to ChatListener being removed
     */
    public synchronized void removeListener (ChatListener cl) {
        myChatListeners.remove(cl);
    }
}
