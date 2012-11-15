package util.input.android.events;

import java.io.Serializable;

public class JoyStickEvent implements Serializable {
    
    private static final long serialVersionUID = 8938037512105434858L;
    public double myAngle;
    public double myMagnitude;
    
    public JoyStickEvent(double angle, double magnitude){
        myAngle = angle;
        myMagnitude = magnitude;
    }
    
    public double getMyAngle(){
        return myAngle;
    }
    
    public double getMyMagnitude(){
        return myMagnitude;
    }

}
