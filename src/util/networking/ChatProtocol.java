package util.networking;

public interface ChatProtocol{
        String sendMessage(String to, String dest, String body);
        String queryRoomInformation(String roomName);
        String openStream(String dest);    
}
