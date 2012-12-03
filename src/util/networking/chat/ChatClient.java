package util.networking.chat;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;
import util.networking.Client;

/**
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */

public class ChatClient extends Client {

    private ChatProtocol myProtocol;
    private String myUser;
    private boolean myLoggedIn;
    private List<String> myListUsers;
    private List<ChatListener> myChatListeners;

    public ChatClient(String host, int port, ChatProtocol c) throws IOException{
        super(host,port);
        myProtocol = c;
    }

    private Socket connect(String host, int port) throws IOException{
        return new Socket(host, port);
    }

    public void login(String user, String password) {
        send(myProtocol.createLogin(user, password));
    }
    
    public void logout() {
        send(myProtocol.createLogout(myUser));
    }

    public void switchUser(String user, String password) {
        logout();
        login(user, password);
    }

    public void sendMessage(String userDest, String body){
        send(myProtocol.createMessage(myUser, userDest, body));
    }
    
    public List<String> getListUsers() {
        return myListUsers;
    }
    
    @Override
    public void processInputFromServer(String input) {
        System.out.println("server received: " + input);
        ChatCommand type = myProtocol.getType(input);
        Method m;
        try {
            m = this.getClass().getMethod(type.getMethodName(), String.class);
            m.invoke(input);
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
    private void processMessage(String input) {
        String from = myProtocol.getFrom(input);
        String to = myProtocol.getTo(input);
        String body = myProtocol.getBody(input);
        fireMessageReceivedEvent(to, from, body);
    }

    @SuppressWarnings("unused")
    private void processError(String input) {
        fireErrorEvent(myProtocol.getErrorMessage(input));
    }
    
    @SuppressWarnings("unused")
    private void processLoggedIn(String input) {
        myLoggedIn = myProtocol.getStatus(input);
    }
    
    @SuppressWarnings("unused")
    private void processListUsers(String input) {
       myListUsers = myProtocol.getListUsers(input);
    }
    
    @SuppressWarnings("unused")
    private void processAddUser(String input) {
        myListUsers.add(myProtocol.getUser(input));
    }
    
    @SuppressWarnings("unused")
    private void processRemoveUser(String input) {
        myListUsers.remove(myProtocol.getUser(input));
    }
    
    
    private synchronized void fireMessageReceivedEvent(String to, String from, String body){
        MessageReceivedEvent e = new MessageReceivedEvent(this, to, from, body);
        for (ChatListener cl : myChatListeners) {
            cl.handleMessageReceivedEvent(e);
        }
    }

    private synchronized void fireErrorEvent(String message) {
        ErrorEvent e = new ErrorEvent(this, message);
        for (ChatListener cl : myChatListeners) {
            cl.handleErrorEvent(e);
        }
    }
    
    public synchronized void addListener(ChatListener cl){
        myChatListeners.add(cl);
    }

    public synchronized void removeListener(ChatListener cl){
        myChatListeners.remove(cl);
    }
}
