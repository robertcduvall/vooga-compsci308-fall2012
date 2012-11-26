package util.input.android.events;

import java.io.Serializable;
import java.util.HashMap;

public class AndroidServerMessage implements Serializable {
      
    private int myVibration;
    
    public AndroidServerMessage(){
        
    }
    
    public void setVibration(int ms){
        myVibration = ms;
    }

}
