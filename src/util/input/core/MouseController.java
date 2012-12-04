package util.input.core;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.event.MouseInputListener;
import util.input.inputhelpers.UKeyCode;


/**
 * This class allows users to enter input through the mouse.
 * 
 * @author Amay
 */

public class MouseController extends Controller<MouseInputListener> implements MouseInputListener {

    public final static int PRESSED = MouseEvent.MOUSE_PRESSED;
    public final static int RELEASED = MouseEvent.MOUSE_RELEASED;
    public final static int CLICKED = MouseEvent.MOUSE_CLICKED;
    public final static int NO_BUTTON = 0;

    /**
     * Create a new mouse controller.
     * 
     * @param comp - The component to which we add the
     *        MouseListener and MouseMotionListener
     */
    public MouseController (Component comp) {
        super();
        comp.addMouseListener(this);
        comp.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked (MouseEvent e) {
        try {
            performReflections(e, "mouseClicked",
                               UKeyCode.codify(MouseEvent.MOUSE_CLICKED, e.getButton()));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void mousePressed (MouseEvent e) {
        try {
            performReflections(e, "mousePressed",
                               UKeyCode.codify(MouseEvent.MOUSE_PRESSED, e.getButton()));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void mouseReleased (MouseEvent e) {
        try {
            performReflections(e, "mouseReleased",
                               UKeyCode.codify(MouseEvent.MOUSE_RELEASED, e.getButton()));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void mouseEntered (MouseEvent e) {
        try {
            performReflections(e, "mouseEntered",
                               UKeyCode.codify(MouseEvent.MOUSE_ENTERED, NO_BUTTON));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void mouseExited (MouseEvent e) {
        try {
            performReflections(e, "mouseExited",
                               UKeyCode.codify(MouseEvent.MOUSE_EXITED, NO_BUTTON));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void mouseDragged (MouseEvent e) {
        try {
            performReflections(e, "mouseDragged",
                               UKeyCode.codify(MouseEvent.MOUSE_DRAGGED, e.getButton()));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

    @Override
    public void mouseMoved (MouseEvent e) {
        try {
            performReflections(e, "mouseMoved", UKeyCode.codify(MouseEvent.MOUSE_MOVED, NO_BUTTON));
        }
        catch (IllegalAccessException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (InvocationTargetException e1) {
            // this will never be thrown because it was checked for previously
        }
        catch (NoSuchMethodException e1) {
            // this will never be thrown because it was checked for previously
        }
    }

}
