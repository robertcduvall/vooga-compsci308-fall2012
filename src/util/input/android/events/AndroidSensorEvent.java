package util.input.android.events;

public class AndroidSensorEvent extends AndroidControllerEvent {
    
    private float[] myAccelerationValues;
    private int ACCEL_ID = 25;

    public AndroidSensorEvent (float[] values) {
        myAccelerationValues = values;
    }
    
    public float[] getAccelerationValues(){
        return myAccelerationValues;
    }
    public int getID(){
        return ACCEL_ID;
    }

}
