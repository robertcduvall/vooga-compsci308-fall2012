package util.input.android.events;

import java.io.Serializable;


/**
 * This is a data wrapper for a JoyStick event on an android controller.
 * 
 * @author Ben Schwab
 * 
 */
public class JoyStickEvent implements Serializable {

    /**
     * Constant representing that this is the right joystick
     */
    public static final int RIGHT = 1;
    /**
     * Constant representing that this is the left joystick, or if there is only
     * one
     * joystick.
     */
    public static final int LEFT = 2;
    /**
     * 
     */
    private static final long serialVersionUID = 5632816923987042393L;

    private double myAngle;
    private double myMagnitude;
    private int myID;

    /**
     * Create a new joystick event.
     * 
     * @param angle an angle in degrees from 0 to 360
     * @param magnitude The magnitude of the displacement of the joystick from
     *        the center of the pad. Ranges from 0 to 1.
     * @param joyStickId the id of the joystick the event originated from
     */
    public JoyStickEvent (double angle, double magnitude, int joyStickID) {
        myAngle = angle;
        myMagnitude = magnitude;
        myID = joyStickID;
    }

    /**
     * 
     * @return an int representing if this is the left or right joystick. If
     *         there is only one joystick
     *         left will always be returned.
     */
    public int getID () {
        return myID;
    }

    /**
     * Get the angle of the joystick.
     * 
     * @return the angle of the joystick. 0 degrees means right, 90 means up 180
     *         means left, and 270 is down
     */
    public double getMyAngle () {
        return myAngle;
    }

    /**
     * Get the magnitude of the joystick.
     * 
     * @returns a double that ranges from 1 -- fully pushed to 0 (not pushed at
     *          all).
     */
    public double getMyMagnitude () {
        return myMagnitude;
    }

}
