package util.input.core;

import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;
import util.input.core.Controller;
import util.input.exceptions.InvalidControllerActionException;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/3/12
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class WiiController extends Controller<WiimoteListener> implements WiimoteListener {

    
    @Override
    public void onButtonsEvent (WiimoteButtonsEvent arg0) {
        // Look up the table of acitons and if arg0 is there then invoke specific action
        
    }
    
    @Override
    public void onMotionSensingEvent (MotionSensingEvent arg0) {
        // Based on the data invoke up motion, down motion, left motion or right motion
    }
     

    @Override
    public void onClassicControllerInsertedEvent (
            ClassicControllerInsertedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onClassicControllerRemovedEvent (
            ClassicControllerRemovedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDisconnectionEvent (DisconnectionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onExpansionEvent (ExpansionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onGuitarHeroInsertedEvent (GuitarHeroInsertedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onGuitarHeroRemovedEvent (GuitarHeroRemovedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onIrEvent (IREvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onNunchukInsertedEvent (NunchukInsertedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onNunchukRemovedEvent (NunchukRemovedEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStatusEvent (StatusEvent arg0) {
        // TODO Auto-generated method stub
        
    }
}
