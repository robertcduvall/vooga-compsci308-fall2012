package util.input.android.events;

import java.io.Serializable;


/**
 * A data wrapper that contains information from an android accelerometer
 * 
 * @author Ben Schwab
 * 
 */
public class AndroidSensorEvent implements Serializable {
    private static final int ACCEL_ID = 25;
    /**
     * 
     */
    private static final long serialVersionUID = -2479570115943153542L;
    private float[] myAccelerationValues;
  
    /**
     * Create a new acceleration event
     * @param values the values of the acceleration event
     */
    public AndroidSensorEvent (float[] values) {
        myAccelerationValues = values;
    }
    /**
     * 
     * @return the acceleration values of the event
     */
    public float[] getAccelerationValues () {
        return myAccelerationValues;
    }
    /**
     * 
     * @return the id of the acceleration event
     */
    public int getID () {
        return ACCEL_ID;
    }

}
