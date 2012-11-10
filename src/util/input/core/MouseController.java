package util.input.core;

import util.input.input_utils.UKeyCode;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import util.input.exceptions.InvalidControllerActionException;

/**
 * Created with IntelliJ IDEA.
 * User: lance
 * Date: 11/4/12
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */

public class MouseController extends Controller<MouseListener> implements MouseListener {
    
    public MouseController (Component comp) {
        super();
        comp.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked (MouseEvent e) {
        try {
            performReflections(e, "mouseClicked", UKeyCode.codify(MouseEvent.MOUSE_CLICKED, e.getButton()));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mousePressed (MouseEvent e) {
        try {
            performReflections(e, "mousePressed", UKeyCode.codify(MouseEvent.MOUSE_PRESSED, e.getButton()));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void mouseReleased (MouseEvent e) {
        try {
            performReflections(e, "mouseReleased", UKeyCode.codify(MouseEvent.MOUSE_RELEASED, e.getButton()));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
        
    }

    @Override
    public void mouseEntered (MouseEvent e) {
        try {
            //Clarify on the 0
            performReflections(e, "mouseEntered", UKeyCode.codify(MouseEvent.MOUSE_ENTERED, 0));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }       
    }

    @Override
    public void mouseExited (MouseEvent e) {
        try {
          //Clarify on the 0
            performReflections(e, "mouseExited", UKeyCode.codify(MouseEvent.MOUSE_EXITED, 0));
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
