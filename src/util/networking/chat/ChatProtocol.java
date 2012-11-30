package util.networking.chat;

public interface ChatProtocol {

    String sendMessage (String to, String dest, String body);

    String queryRoomInformation (String roomName);

    String openStream (String dest);

    int getPort ();
}
