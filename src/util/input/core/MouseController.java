package util.input.core;

import java.awt.Component;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import util.input.input_utils.UKeyCode;

/**
 * This class allows users to enter input through the mouse
 * 
 * @author Amay
 */

public class MouseController extends Controller<MouseInputListener> implements MouseInputListener {
    
    public final static int PRESSED = MouseEvent.MOUSE_PRESSED;
    public final static int RELEASED = MouseEvent.MOUSE_RELEASED;
    public final static int CLICKED = MouseEvent.MOUSE_CLICKED;
    public final static int NO_BUTTON = 0;
    
    
    /**
     * Create a new mouse controller
     * 
     * @param comp - The component to which we add the MouseListener and MouseMotionListener 
     */
    public MouseController (Component comp) {
        super();
        comp.addMouseListener(this);
        comp.addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            performReflections(e, "mouseClicked",
                    UKeyCode.codify(MouseEvent.MOUSE_CLICKED, e.getButton()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            performReflections(e, "mousePressed", UKeyCode.codify(MouseEvent.MOUSE_PRESSED, e.getButton()));
            System.out.println(e.getButton());
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    
    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            performReflections(e, "mouseReleased",
                    UKeyCode.codify(MouseEvent.MOUSE_RELEASED, e.getButton()));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        try {
            performReflections(e, "mouseEntered", UKeyCode.codify(MouseEvent.MOUSE_ENTERED, NO_BUTTON));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        try {
            performReflections(e, "mouseExited", UKeyCode.codify(MouseEvent.MOUSE_EXITED, NO_BUTTON));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mouseDragged (MouseEvent e) {
        try {
            performReflections(e, "mouseDragged", UKeyCode.codify(MouseEvent.MOUSE_DRAGGED, e.getButton()));
          }
          catch (Exception e1) {
              e1.printStackTrace();
          }
    }

    @Override
    public void mouseMoved (MouseEvent e) {
        try {
            performReflections(e, "mouseMoved", UKeyCode.codify(MouseEvent.MOUSE_MOVED, NO_BUTTON));
          }
          catch (Exception e1) {
              e1.printStackTrace();
          }
    }

}
