package util.networking.chat.protocol;

import java.io.IOException;
import util.networking.chat.ChatClient;

/**
 *
 * @author Oren Bukspan
 */
public class ChatterTester {


    /**
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ChatClient myClient = new ChatClient("wl-10-190-252-208", new GordonBukspanProtocol());
        Chatter c = new Chatter(myClient);
 
    }
}
