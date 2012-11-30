package util.networking.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import util.networking.Service;

/**
 *
 * @author Oren Bukspan
 */
public class ChatService implements Service {

    private ChatProtocol myProtocol;

    public ChatService(ChatProtocol cp) {
        myProtocol = cp;
    }

    @Override
    public void serve (InputStream in, OutputStream out) throws IOException {

        while (true) {
            BufferedReader input = new BufferedReader(new InputStreamReader(in));
            input.readLine();
        }

    }

    public int getPort () {
        return myProtocol.getPort();
    }
}
