package vooga.platformer.leveleditor;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;


public class LevelEditorMouseListener implements MouseListener, MouseMotionListener {

    private ISpritePlacementManager myManager;
    private Point2D myDragStart, myDragEnd;

    public LevelEditorMouseListener (ISpritePlacementManager manager) {
        myManager = manager;
    }

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
