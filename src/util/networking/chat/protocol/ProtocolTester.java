package util.networking.chat.protocol;


import java.io.File;
import java.net.UnknownHostException;
import util.networking.chat.ChatServer;

/**
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */
public final class ProtocolTester {

    private static final int MAX_CONNECTIONS = 100;

    private ProtocolTester() {
    }

    /**
     * A main method to test the Server and Protocol.
     * 
     * @param args Expects NO command line arguments.
     * @throws UnknownHostException 
     **/
    public static void main(String[] args) throws UnknownHostException {

        File file = new File("src/util/networking/chat/users.xml");
        new ChatServer(new ProtocolXMPP(), file, MAX_CONNECTIONS);
        
        //ProtocolXMPP xmpp = new ProtocolXMPP();
        //System.out.println(xmpp.sendMessage("Connor Gordon", "Oren Bukspan", "goduke."));
    }

}
