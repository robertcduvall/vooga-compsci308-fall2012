package util.networking;

import java.net.Socket;

/**
 * 
 * @author Connor Gordon
 */

public class ChatClient {
    private Socket myConnection;
    // private Language myLanguage;
    private String myHost;
    private String myUser, myPass;
    
    public ChatClient(String host, int port){}
    
    private Socket connect(String host, int port){
        return null;
    }
    
    private void openStream(String user){}
    
    public void login(String user, String password){}
    public void logout(){}
    
    public void switchUser(String user, String password){
        logout();
        login(user, password);
    }
    
    public void sendMessage(String userDest, String body){}
    private void sendMessage(/*Message m*/){}
}
