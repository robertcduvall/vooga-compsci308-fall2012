package util.input.core;

/**
 * Created with IntelliJ IDEA.

 * User: lance
 * Date: 11/3/12
 * Time: 8:18 PM
 * To change this template use File | Settings | File Templates.
 */

import util.input.android.events.AndroidButtonEvent;
import util.input.input_utils.UKeyCode;
import util.input.interfaces.listeners.AndroidListener;

/**
 * Contains the implementation of pre-configured actions for the Android controller
 */
public class AndroidController extends Controller<AndroidListener> implements AndroidListener{
    
    public AndroidController(){
        super();        
    }


    @Override
    public void onScreenPress (AndroidButtonEvent b) {
        try {
            performReflections(b, "onScreenPress", UKeyCode.codify(b.getPressType(), b.getID()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

   
}