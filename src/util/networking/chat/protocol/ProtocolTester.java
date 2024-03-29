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

    private static final int MAX_CONNECTIONS = 10;

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
        GordonBukspanProtocol gbp = new GordonBukspanProtocol();
        new ChatServer(gbp, file, MAX_CONNECTIONS);
    }

}
