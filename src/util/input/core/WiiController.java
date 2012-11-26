package util.input.core;

import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import util.input.inputhelpers.UKeyCode;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
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


/**
 * This class allows users to enter input through the Wiimote.
 *
 * @author Amay
 *
 */
public class WiiController extends Controller<WiimoteListener> implements
        WiimoteListener {

    public static final int BUTTON_PRESSED = 51;
    public static final int BUTTON_HELD = 53;
    public static final int BUTTON_RELEASED = 55;
    public static final int WIIMOTE_BUTTON_A = 8;
    public static final int WIIMOTE_BUTTON_B = 4;
    public static final int WIIMOTE_BUTTON_ONE = 2;
    public static final int WIIMOTE_BUTTON_TWO = 1;
    public static final int WIIMOTE_BUTTON_PLUS = 4096;
    public static final int WIIMOTE_BUTTON_MINUS = 16;
    public static final int WIIMOTE_BUTTON_HOME = 128;
    public static final int WIIMOTE_BUTTON_UP = 2048;
    public static final int WIIMOTE_BUTTON_DOWN = 1024;
    public static final int WIIMOTE_BUTTON_LEFT = 256;
    public static final int WIIMOTE_BUTTON_RIGHT = 512;
    //public stat

    /**
     * Create a new Wii controller.
     *
     */
    public WiiController() {
        super();
        Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
        Wiimote wiimote = wiimotes[0];
        wiimote.addWiiMoteEventListeners(this);
    }

    @Override
    public void onButtonsEvent(WiimoteButtonsEvent arg0) {
        // Look up the table of acitons and if arg0 is there then invoke
        // specific action
        try {
            if (arg0.getButtonsJustPressed() > 0) {
                performReflections(
                        arg0,
                        "onButtonsEvent",
                        UKeyCode.codify(BUTTON_PRESSED,
                                arg0.getButtonsJustPressed()));
            }
            if (arg0.getButtonsJustReleased() > 0) {
                performReflections(
                        arg0,
                        "onButtonsEvent",
                        UKeyCode.codify(BUTTON_RELEASED,
                                arg0.getButtonsJustReleased()));
            }
            if (arg0.getButtonsHeld() > 0) {
                performReflections(arg0, "onButtonsEvent",
                        UKeyCode.codify(BUTTON_HELD, arg0.getButtonsHeld()));
            }
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void onMotionSensingEvent(MotionSensingEvent arg0) {
        // Based on the data invoke up motion, down motion, left motion or right
        // motion
        try {
            performReflections(arg0, "onMotionSensingEvent", -1);
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void onClassicControllerInsertedEvent(
            ClassicControllerInsertedEvent arg0) {
        //method will never be called as classic controller is not supported
    }

    @Override
    public void onClassicControllerRemovedEvent(
            ClassicControllerRemovedEvent arg0) {
        //method will never be called as classic controller is not supported
    }

    @Override
    public void onDisconnectionEvent(DisconnectionEvent arg0) {
        try {
            performReflections(arg0, "onDisconnectionEvent", -1);
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void onExpansionEvent(ExpansionEvent arg0) {
        //method will never be called as other controllers are not supported
    }

    @Override
    public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
        //method will never be called as guitar hero is not supported
    }

    @Override
    public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
      //method will never be called
    }

    @Override
    public void onIrEvent(IREvent arg0) {
        try {
            performReflections(arg0, "onIrEvent", -1);
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
        //method will never be called
    }

    @Override
    public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) { 
      //method will never be called
    }

    @Override
    public void onStatusEvent(StatusEvent arg0) {
        try {
            performReflections(arg0, "onStatusEvent", -1);
        }
        catch (IllegalAccessException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            //this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            //this will never be thrown because it was checked for previously
        }
    }
}
