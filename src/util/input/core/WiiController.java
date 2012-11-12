package util.input.core;

import util.input.input_utils.UKeyCode;


/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/3/12
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
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

    public WiiController(Wiimote wii) {
        wii.addWiiMoteEventListeners(this);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMotionSensingEvent(MotionSensingEvent arg0) {
        // Based on the data invoke up motion, down motion, left motion or right
        // motion
    }

    @Override
    public void onClassicControllerInsertedEvent(
            ClassicControllerInsertedEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClassicControllerRemovedEvent(
            ClassicControllerRemovedEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDisconnectionEvent(DisconnectionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onExpansionEvent(ExpansionEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onIrEvent(IREvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusEvent(StatusEvent arg0) {
        // TODO Auto-generated method stub

    }
}
