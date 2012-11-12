package vooga.platformer.leveleditor;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;


/*
 * I decided not to make this an inner class. Why, you ask? Well, I COULD
 * have. Either this class COULD have been an inner class for the
 * LevelBoard or the SpritePlacementManager. The reason I chose not to make
 * it an inner class for the SpritePlacementManager is that the
 * SpritePlacementManager
 * does not extend Java's Component class. This means that, although this class
 * calls SpritePlacementManager's methods, it would have to act as a listener
 * for LevelBoard, which does extend Component. This means that the listener
 * for the LevelBoard would be set in the SpritePlacementManager.
 * 
 * But it isn't the SpriteSplacementManager's business to be doing this,
 * so this class shouldn't be an inner class for SpritePlacementManager.
 * 
 * So could it be an inner class for LevelBoard? The reason I don't like this is
 * that I'm quite attached to an idea. I envisage a level editor that can be
 * used in multiple modes. For example, while this class is used to drag and
 * drop
 * sprites, I would love to have a mode where users can draw levels or set
 * sprite
 * movement paths. In these modes, using this class doesn't necessarily make
 * sense. To switch between these modes, we should use composition. We can
 * either
 * switch out the different managers (e.g. SpritePlacementManager) or we can
 * switch out the mouse listeners, such as the one represented by this class.
 * Now,
 * if we decided to switch out the managers, each manager would need to
 * implement
 * the same interface or extend the same class. This superinterface would have
 * to
 * be very vague - probably too vague for our needs. For example, we would
 * probably
 * have to have method names such as 'click()'. This would almost certainly mean
 * that the mouse listeners would have to be added to the manager classes and I
 * have
 * already stated why this would be bad. Hence we should trade out the
 * mouse listener classes such as this one. This means that the mouse listener
 * classes shouldn't be an inner class of the LevelBoard. Therefore, I've made
 * it
 * a separate class.
 * 
 * If the above justification made no sense (it probably didn't) feel free to
 * talk
 * to me. Or you can just ignore my design justification, since how this is
 * implemented
 * shouldn't matter to most classes. If you have a better design definitely
 * talk to me!
 * 
 * This class will probably be replaced by the user input team's classes anyway.
 */

/**
 * This class listens to mouse input. It allows the SpritePlacementManager to
 * select sprites through user mouse movement. This class may later be replaced
 * by user input classes written by the other group.
 * 
 * @author Paul Dannenberg
 * 
 */
public class SelectionMouseListener implements MouseListener,
        MouseMotionListener {

    private ISpritePlacementManager myManager;
    private Point2D myDragStart, myDragEnd;

    /**
     * Creates the mouse listener.
     * 
     * @param manager The ISpritePlacementManager that this listener will
     *        coordinate with.
     */
    public SelectionMouseListener(ISpritePlacementManager manager) {
        myManager = manager;
    }

    /**
     * If the mouse click is on a region that does not fails to select a
     * sprite, the current selection is cleared. Otherwise a sprite is
     * selected by the SpritePlacementManager.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!myManager.selectSprite(new Point(e.getX(), e.getY()))) {
            myManager.clearSelection();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        myDragStart = null;
    }

    /**
     * Defines a selection rectangle based on a mouse drag event.
     * This method then asks the SpritePlacementManager to select
     * all sprites defined within this rectangle.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (myDragStart == null) {
            myDragStart = new Point(e.getX(), e.getY());
        }
        myDragEnd = new Point(e.getX(), e.getY());
        Rectangle selectionRectangle = new Rectangle((int) myDragStart.getX(),
                (int) myDragStart.getY(),
                (int) (myDragEnd.getX() - myDragStart.getX()),
                (int) (myDragEnd.getY() - myDragStart.getY()));
        myManager.selectRegion(selectionRectangle);

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
