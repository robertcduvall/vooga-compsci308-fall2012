package util.networking.chat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import util.networking.GenericClient;

/**
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */

public class ChatClient extends GenericClient {

    private ChatProtocol myProtocol;
    private String myUser, myPass;

    public ChatClient(String host, int port, ChatProtocol c, OutputStream os) throws IOException{
        super(host,port,os);
        myProtocol = c;
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
