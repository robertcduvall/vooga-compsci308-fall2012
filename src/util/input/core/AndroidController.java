package util.input.core;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/3/12
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */

import util.input.exceptions.InvalidControllerActionException;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidMotionEvent;
import util.input.interfaces.listeners.AndroidListener;

/**
 * Contains the implementation of pre-configured actions for the Android controller
 */
public class AndroidController extends Controller<AndroidListener> implements AndroidListener{

    @Override
    public void actionValidate (int action)
            throws InvalidControllerActionException {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setActionActive (int action, boolean isActive) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onScreenPress (AndroidButtonEvent b) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onMotion (AndroidMotionEvent m) {
        // TODO Auto-generated method stub
        
    }

}