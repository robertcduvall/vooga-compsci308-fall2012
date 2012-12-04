package util.input.android.events;

import java.io.Serializable;


/**
 * Send tactile feedback to your android controller with a AndroidVibration
 * event. All times are in ms.
 * 
 * @author Ben Schwab
 * 
 */
public class AndroidVibration implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2790366678753962306L;
    private int myVibrationTime;
    private int myOnTime;
    private int myOffTime;

    /**
     * Create a complex vibration event. Will cycle on and off times until total
     * vibrationDuration is met. All times are in ms.
     * 
     * @param vibrationDuration The total duration of the vibration
     * @param onTime the time the vibration is on cycle.
     * @param offTime the time the vibration is off each cycle.
     */
    public AndroidVibration (int vibrationDuration, int onTime, int offTime) {
        myVibrationTime = vibrationDuration;
        myOnTime = onTime;
        myOffTime = offTime;
    }

    /**
     * Create a one time vibration event.
     * 
     * @param time the time of the vibration in ms.
     */
    public AndroidVibration (int time) {
        myVibrationTime = time;
        myOnTime = time;
    }

    /**
     * 
     * @return the total time a vibration lasts
     */
    public int getVibrationTime () {
        return myVibrationTime;
    }

    /**
     * 
     * @return the time in ms that the vibration is on per cycle
     */
    public int getOnTime () {
        return myOnTime;
    }

    /**
     * 
     * @return the time in ms that the vibration is off per cycle
     */
    public int getOffTime () {
        return myOffTime;
    }

}
