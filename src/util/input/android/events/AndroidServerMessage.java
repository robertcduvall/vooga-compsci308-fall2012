package util.input.android.events;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * This class is the data bundle you can send to an android app. Currently, the
 * class supports vibrations and string messages and controller setting
 * messages.
 * A message can contain more than one field at a time.
 * 
 * @author Ben Schwab
 * 
 */
public class AndroidServerMessage implements Serializable {

    /**
     * 
     */
    /**
     * Use this constant to represent that the string message should set the
     * description of the gameboy controller.
     */
    public static final String GAMEBOY_DESCRIPTION = "gameboydescription";
    /**
     * Use this constant to represent that the string message should set the
     * description of the touch screen controller.
     */
    public static final String TOUCHSCREEN_DESCRIPTION = "touchscreendescription";
    /**
     * Use this constant to represent that the string message should set the
     * description of the playstation controller.
     */
    public static final String PLAYSTATION_DESCRIPTION = "playstationdescription";
    private static final long serialVersionUID = -5089510314679069216L;
    private AndroidVibration myVibration;
    private boolean[] myConfigSettings;
    /**
     * A constant that indicates a message about gameboy controls.
     */

    private Map<String, String> myMessageBank;

    /**
     * Create a new message to send to an android controller.
     */
    public AndroidServerMessage () {
        myMessageBank = new HashMap<String, String>();
    }

    /**
     * Store a string message to a string key.
     * 
     * @param messageType the type of message - use constants in class.
     * @param messageContent the content of the message
     */
    public void putStringMessage (String messageType, String messageContent) {
        myMessageBank.put(messageType, messageContent);
    }

    /**
     * 
     * @param gameBoyEnabled boolean representing if your game supports gameboy
     *        controllers
     * @param playstationEnabled boolean representing if your game supports
     *        playstation controllers
     * @param touchControllerEnabled boolean representing if your game supports
     *        touch controllers
     * @param accelerometerEnabled boolean representing if any of your
     *        controllers use the accelerometer data.
     */
    public void putControllerConfigMessage (boolean gameBoyEnabled, boolean playstationEnabled,
                                            boolean touchControllerEnabled,
                                            boolean accelerometerEnabled) {
        myConfigSettings = new boolean[4];
        myConfigSettings[0] = gameBoyEnabled;
        myConfigSettings[1] = playstationEnabled;
        myConfigSettings[2] = touchControllerEnabled;
        myConfigSettings[3] = accelerometerEnabled;
    }

    /**
     * put a vibration event to immediately be deployed on android phone.
     * 
     * @param vibration the vibration event to deploy.
     */
    public void setVibration (AndroidVibration vibration) {
        myVibration = vibration;
    }

    /***
     * 
     * @return a boolean array representing which controls are active, and if
     *         the accelerometer is active, or null if the message does not
     *         contain
     *         controllerConfigSettings
     */
    public boolean[] getControllerConfigSettings () {
        return myConfigSettings;
    }

    /**
     * 
     * @return the message bank map, or an empty map if there are no string
     *         messages
     */
    public Map<String, String> getMessageBank () {
        return myMessageBank;
    }

    /**
     * 
     * @return the vibration action, or null if there are no vibration actions
     *         in the message.
     */
    public AndroidVibration getAndroidVibration () {
        return myVibration;
    }

}
