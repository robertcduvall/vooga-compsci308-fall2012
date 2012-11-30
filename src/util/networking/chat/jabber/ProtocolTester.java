package util.networking.chat.jabber;


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

        new ChatServer(new ProtocolXMPP(), MAX_CONNECTIONS);

        ProtocolXMPP xmpp = new ProtocolXMPP();
        System.out.println(xmpp.openStream("server"));
        System.out.println(xmpp.sendMessage("Connor Gordon", "Oren Bukspan", "goduke."));
    }

}
