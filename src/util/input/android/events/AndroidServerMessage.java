package util.input.android.events;

import java.io.Serializable;
import java.util.HashMap;


/**
 * This class is the data bundle you can send to an android app. Currently, the
 * class supports vibrations and string messages.
 * 
 * @author Ben Schwab
 * 
 */
public class AndroidServerMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5089510314679069216L;
    private int myVibration;

    public AndroidServerMessage () {
        
    }

    public void setVibration (int ms) {
        myVibration = ms;
    }

}
