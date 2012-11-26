package util.input.android.events;

public class AndroidControllerConfigMessage extends AndroidServerMessage {
    
    private boolean[] myConfigSettings = new boolean[4];

    public AndroidControllerConfigMessage(boolean gameBoyEnabled, boolean playstationEnabled, boolean touchControllerEnabled, boolean accelerometerEnabled){
        myConfigSettings[0] = gameBoyEnabled;
        myConfigSettings[1] = playstationEnabled;
        myConfigSettings[2] = touchControllerEnabled;
        myConfigSettings[3] = accelerometerEnabled;
    }
    
    public boolean[] getConfigSettings(){
        return myConfigSettings;
    }

    
}
