package vooga.platformer.leveleditor;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;


/**
 * This class listens to mouse input. It allows the SpritePlacementManager to
 * select sprites through user mouse movement. This class may later be replaced
 * by user input classes written by the other group.
 * 
 * @author Paul Dannenberg
 * 
 */
public class LevelEditorMouseListener implements MouseListener, MouseMotionListener {

    private ISpritePlacementManager myManager;
    private Point2D myDragStart, myDragEnd;

    /**
     * Creates the mouse listener.
     * 
     * @param manager The ISpritePlacementManager that this listener will
     *        coordinate with.
     */
    public LevelEditorMouseListener (ISpritePlacementManager manager) {
        myManager = manager;
    }

    /**
     * If the mouse click is on a region that does not fails to select a 
     * sprite, the current selection is cleared. Otherwise a sprite is
     * selected by the SpritePlacementManager.
     */
    @Override
    public void mouseClicked (MouseEvent e) {
        if (!myManager.selectSprite(new Point(e.getX(), e.getY()))) {
            myManager.clearSelection();
        }

    }

    @Override
    public void mouseEntered (MouseEvent e) {

    }

    @Override
    public void mouseExited (MouseEvent e) {

    }

    @Override
    public void mousePressed (MouseEvent e) {

    }

    @Override
    public void mouseReleased (MouseEvent e) {
        myDragStart = null;
    }

    /**
     * Defines a selection rectangle based on a mouse drag event.
     * This method then asks the SpritePlacementManager to select
     * all sprites defined within this rectangle.
     */
    @Override
    public void mouseDragged (MouseEvent e) {
        if (myDragStart == null) {
            myDragStart = new Point(e.getX(), e.getY());
        }
        myDragEnd = new Point(e.getX(), e.getY());
        Rectangle selectionRectangle =
                new Rectangle((int) myDragStart.getX(), (int) myDragStart.getY(),
                              (int) (myDragEnd.getX() - myDragStart.getX()),
                              (int) (myDragEnd.getY() - myDragStart.getY()));
        myManager.selectRegion(selectionRectangle);

    }

    @Override
    public void mouseMoved (MouseEvent e) {

    }

}
