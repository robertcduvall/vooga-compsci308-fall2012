package util.input.android.events;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/4/12
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class AndroidButtonEvent {
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static final int A = 5;
    public static final int B = 6;
    public static final int START = 7;
    public static final int SELECT = 8;

    /**
     * @return the id of this button event
     */
    public int getID() {
        return 0;
    }

    /**
     * @return returns an integer describing the type of press
     */
    public int getPressType() {
        return 0;
    }
}
