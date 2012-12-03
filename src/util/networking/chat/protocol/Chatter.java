package util.networking.chat.protocol;

import util.networking.chat.ChatClient;

/**
 *
 * @author Oren Bukspan
 */
public class Chatter {

    private static final int TIMEOUT = 10000;
    
    private String myUser;
    private ChatClient myClient;
    
    public Chatter(ChatClient client) {
        myClient = client;
    }

    public boolean register(String user, String password) {
        myClient.register(user, password);
        try {
            this.wait(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myClient.getLoggedInStatus();
    }
    
    
    public boolean login(String user, String password) {
        myClient.login(user, password);
        try {
            this.wait(10000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myClient.getLoggedInStatus();
    }




}
