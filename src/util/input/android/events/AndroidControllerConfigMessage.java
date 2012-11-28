package util.input.android.events;

/**
 * A subclass of AndroidServerMessage that provides a template to control what
 * controllers a user can select to play with your game.
 * 
 * @author Ben Schwab
 * 
 */
public class AndroidControllerConfigMessage extends AndroidServerMessage {
    /**
     * 
     */
    private static final long serialVersionUID = -6161981633842264032L;
    private boolean[] myConfigSettings = new boolean[4];

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
    public AndroidControllerConfigMessage (boolean gameBoyEnabled, boolean playstationEnabled,
                                           boolean touchControllerEnabled,
                                           boolean accelerometerEnabled) {
        myConfigSettings[0] = gameBoyEnabled;
        myConfigSettings[1] = playstationEnabled;
        myConfigSettings[2] = touchControllerEnabled;
        myConfigSettings[3] = accelerometerEnabled;
    }

    /**
     * 
     * @return the configuration settings of this message
     */
    public boolean[] getConfigSettings () {
        return myConfigSettings;
    }

}
