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
     * @throws IOException
     */
    public Messenger (StreamConnection stream) throws IOException {

        myOutput = stream.openOutputStream();

    }

    /**
     * Write a message to the active connection.
     * 
     * @param m the message to write
     * @throws IOException
     */
    public void write (AndroidServerMessage m) {
        new WriteThread(m);
    }

    private class WriteThread implements Runnable {
        private AndroidServerMessage myMessage;

        public WriteThread (AndroidServerMessage m) {
            myMessage = m;
            Thread mThread = new Thread(this);
            mThread.start();
        }

        public void run () {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            byte[] outBytes;
            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(myMessage);
                byte[] yourBytes = bos.toByteArray();
                outBytes = yourBytes;
                myOutput.write(outBytes);
            }
            catch (IOException e) {
                // should never happen
            }

            finally {
                try {
                    out.close();
                    bos.close();
                }

                catch (IOException e) {
                    // should never happen
                }
            }
        }
    }

    private void writeMessage (AndroidServerMessage m) {

    }

}
