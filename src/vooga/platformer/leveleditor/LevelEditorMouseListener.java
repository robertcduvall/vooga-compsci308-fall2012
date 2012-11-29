package vooga.platformer.leveleditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * This class listens to mouse input. It allows the SpritePlacementManager to
 * select sprites through user mouse movement. This class will later be replaced
 * by user input classes written by the other group.
 * 
 * @author Paul Dannenberg
 * 
 */
public class LevelEditorMouseListener implements MouseListener, MouseMotionListener {

    IEditorMode myMode;

    public LevelEditorMouseListener (IEditorMode currentMode) {
        myMode = currentMode;
    }

    @Override
    public void mouseDragged (MouseEvent e) {

    }

    @Override
    public void mouseMoved (MouseEvent e) {
        myMode.sendCursorPosition(e.getX(), e.getY());
    }

    @Override
    public void mouseClicked (MouseEvent e) {

    }

    @Override
    public void mouseEntered (MouseEvent e) {

    }

    @Override
    public void mouseExited (MouseEvent e) {

    }

    @Override
    public void mousePressed (MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            myMode.primaryButtonPress(e.getX(), e.getY());
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            myMode.secondaryButtonPress(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased (MouseEvent e) {

    }

}
