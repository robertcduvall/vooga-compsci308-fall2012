package util.input.interfaces.listeners;

import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidMotionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/3/12
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AndroidListener {

    /**
     * Method will be called if button is pressed
     */
    public void onScreenPress(AndroidButtonEvent b);

    /**
     * Method will be called if device detects motion
     */
    public void onMotion(AndroidMotionEvent m);
}
