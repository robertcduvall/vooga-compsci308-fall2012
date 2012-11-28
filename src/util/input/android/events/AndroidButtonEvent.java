package util.input.android.events;

import java.io.Serializable;


/**
 * This class wraps information from an Android controller button event.
 * 
 * @author Ben Schwab
 * 
 */
public class AndroidButtonEvent implements Serializable {
    /**
     * A constant representing that a button was just pushed.
     */
    public static final int BUTTON_PRESSED = 401;
    /**
     * A constant representing that a button was just released.
     */
    public static final int BUTTON_RELEASED = 402;
    /**
     * 
     */
    private static final long serialVersionUID = -3905344106141040518L;
    private int myButtonID;
    private int myButtonAction;
    private int myEventCode;

    /**
     * 
     * @param buttonID The ID of the button
     * @param buttonAction The action int for the button
     */
    public AndroidButtonEvent (int buttonID, int buttonAction) {
        String eventCodeString = "" + buttonID + "" + buttonAction;
        myEventCode = Integer.parseInt(eventCodeString);
        myButtonID = buttonID;
        myButtonAction = buttonAction;
    }

    /**
     * Get the ID of the button event.
     * 
     * @return The ID of the button event
     */
    public int getID () {
        return myButtonID;
    }

    /**
     * Get the type of button event (press/release)
     * 
     * @return an integer representing the type of button event (press/release).
     */
    public int getPressType () {
        return myButtonAction;
    }

    /**
     * Get the combined button type / press type code
     * 
     * @return the combined integer of the button press type and id
     */
    public int getEventCode () {
        return myEventCode;
    }

    /**
     * 
     * A data wrapper for the components found on an Android playstation
     * controller.
     * 
     */
    public static final class Playstation {
        /**
         * 
         */
        public static final int TRIANGLE = 1;
        /**
         * Triangle Button
         */
        public static final int SQUARE = 2;
        /**
         * Square Button
         */
        public static final int CIRCLE = 3;
        /**
         * Circle Button
         */
        public static final int X = 4;
        /**
         * Up arrow
         */
        public static final int UP = 5;
        /**
         * Down arrow
         */
        public static final int DOWN = 6;
        /**
         * Left arrow
         */
        public static final int LEFT = 7;
        /**
         * Right arrow
         */
        public static final int RIGHT = 8;
        /**
         * Start arrow
         */
        public static final int START = 9;
    }

    /**
     * 
     * A data wrapper for the components found on an Android Gameboy controller.
     * 
     */
    public static final class GameBoy {
        /**
         * A Button.
         */
        public static final int A = 100;
        /**
         * B Button
         */
        public static final int B = 101;
        /**
         * Up arrow
         */
        public static final int UP = 102;
        /**
         * Down arrow
         */
        public static final int DOWN = 103;
        /**
         * Left arrow
         */
        public static final int LEFT = 104;
        /**
         * Right arrow
         */
        public static final int RIGHT = 105;
        /**
         * Start button
         */
        public static final int START = 106;
        /**
         * Select button
         */
        public static final int SELECT = 107;

    }
    /**
     * 
     * A data wrapper class for the components in a TouchController.
     *
     */
    public static final class TouchController {

        /**
         * A Button.
         */
        public static final int A = 200;
        /**
         * B Button
         */
        public static final int B = 201;
        /**
         * X Button
         */
        public static final int X = 202;
        /**
         * Y Button
         */
        public static final int Y = 203;
        /**
         * Start button
         */
        public static final int START = 204;
        /**
         * Select button
         */
        public static final int SELECT = 204;

    }

}
