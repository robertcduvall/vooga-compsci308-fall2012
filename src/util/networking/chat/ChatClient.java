package util.networking.chat;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

/**
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */

public class ChatClient {

    private Socket myConnection;
    private ChatProtocol myProtocol;
    private String myUser, myPass;

    public ChatClient(String host, int port, ChatProtocol c){
        try {
            myProtocol = c;
            myConnection = connect(host, port);

        }
        catch (IOException e) {
            System.out.println("Unable to connect to Server specified.");
        }
    }

    private Socket connect(String host, int port) throws IOException{
        return new Socket(host, port);
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
