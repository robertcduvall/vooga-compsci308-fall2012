package util.input.android.bluetoothserver;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.microedition.io.StreamConnection;
import util.input.android.events.AndroidControllerEvent;
import util.input.android.events.AndroidServerMessage;

public class Messenger {

    private OutputStream myOutput;


    public Messenger(StreamConnection stream){
        try {
            myOutput = stream.openOutputStream();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void write(AndroidServerMessage m){
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
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
