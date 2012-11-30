package util.networking.chat;

public interface ChatProtocol {

    String sendMessage (String to, String dest, String body);

    String getUsersOnline();

    String login(String userName, String password);
    
    String register(String userName, String password);
    
    int getPort();
}
