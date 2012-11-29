package util.input.android.bluetoothserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.microedition.io.StreamConnection;
import util.input.android.events.AndroidServerMessage;


/**
 * A messenger can write serializable objects to an output stream.
 * 
 * @author Ben Schwab
 * 
 */
public class Messenger {

    private OutputStream myOutput;

    /**
     * Create a messenger with the active stream connection.
     * 
     * @param stream the active stream connection.
     */
    public Messenger (StreamConnection stream) {
        try {
            myOutput = stream.openOutputStream();
        }
        // should never occur
        catch (IOException e) {

        }
    }

    /**
     * Write a message to the active connection.
     * 
     * @param m the message to write
     */
    public void write (AndroidServerMessage m) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        System.out.println("writing android server message");
        byte[] outBytes;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(m);
            byte[] yourBytes = bos.toByteArray();
            outBytes = yourBytes;
            myOutput.write(outBytes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                out.close();
                bos.close();
            }
            // should never occur
            catch (IOException e) {

            }
        }
    }

}
