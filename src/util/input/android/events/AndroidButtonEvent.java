package util.input.android.events;

public class AndroidButtonEvent extends AndroidControllerEvent {
    
    private int myButtonID;
    private int myButtonAction;
    private int myEventCode;
    public static final int BUTTON_PRESSED = 401;
    public static final int BUTTON_RELEASED = 402;
    
    /**
     * 
     * A data wrapper for the components found on an Android playstaiton controller.
     *
     */
    public static final class Playstation{
        public static final int TRIANGLE =1;
        public static final int SQUARE =2;
        public static final int CIRCLE =3;
        public static final int X =4;
        public static final int UP =5;
        public static final int DOWN =6;
        public static final int LEFT = 7;
        public static final int RIGHT =8;
        public static final int START =9;
    }
    
    /**
     * 
     * A data wrapper for the components found on an Android Gameboy controller.
     *
     */
    public static final class GameBoy{
        public static final int TRIANGLE =100;
        public static final int SQUARE =101;
        public static final int CIRCLE =102;
        public static final int X =103;
        public static final int UP =104;
        public static final int DOWN =105;
        public static final int LEFT = 106;
        public static final int RIGHT =107;
    }
    
    
    public AndroidButtonEvent(int buttonID, int buttonAction){
        String eventCodeString = ""+buttonID+""+buttonAction;
        myEventCode = Integer.parseInt(eventCodeString);
        myButtonID = buttonID;
        myButtonAction = buttonAction;
    }
    
    public int getID(){
        return myButtonID;
    }
    
    public int getPressType(){
        return myButtonAction;
    }
    
    public int getEventCode(){
        return myEventCode;
    }
}
