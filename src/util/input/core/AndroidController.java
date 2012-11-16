package util.input.core;



import util.input.android.bluetoothserver.AndroidBluetoothServer;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.JoyStickEvent;
import util.input.inputhelpers.UKeyCode;
import util.input.interfaces.listeners.AndroidListener;

/**
 * This class allows users to enter input through an Android app.
 *
 * @author Ben Schwab Lance
 *
 */
public class AndroidController extends Controller<AndroidListener> implements
        AndroidListener {
    /**
     * Create a new android controller.
     */
    public AndroidController() {
        super();
        AndroidBluetoothServer server = new AndroidBluetoothServer(0);
        server.subscribe(this);
        server.startServer();
        System.out.println("not hanging");
    }

    @Override
    public void onScreenPress(AndroidButtonEvent b) {
        try {
            performReflections(b, "onScreenPress",
                    UKeyCode.codify(b.getPressType(), b.getID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {
        try {
            performReflections(j, "onJoyStickMove", j.getID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
