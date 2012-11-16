package util.networking;

public abstract class GameClient extends GenericClient {
    
    //MultiplayerGame game; 
    
    private static final String SEND_TAG = "[SEND]";
    private static final String SYNC_TAG = "[SYNC]";
    private static final String RECEIVE_TAG = "[RECEIVE]";
    
    public abstract void setUp();
    public abstract void send();
    public abstract void sync();
    public abstract void receive();
}
