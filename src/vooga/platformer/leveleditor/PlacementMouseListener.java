package vooga.platformer.leveleditor;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import vooga.platformer.gameobject.GameObject;


/**
 * A custom MouseAdapter used by the LevelEditor for placing GameObjects.
 * 
 * @author Sam Rang
 * 
 */
public class PlacementMouseListener extends MouseAdapter {
    private LevelBoard myLevel;
    private GameObject myCurrent;

    public PlacementMouseListener (LevelBoard board) {
        myCurrent = null;
        myLevel = board;
    }

    @Override
    public void mousePressed (MouseEvent e) {
        if (myCurrent != null) {
            myCurrent = null;
        }
        else {
            for (GameObject obj : myLevel.getGameObjects()) {
                if (obj.containsPoint(new Point(e.getPoint().x + myLevel.getOffset(),
                                                e.getPoint().y))) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        myCurrent = obj;
                    }
                    else if (e.getButton() == MouseEvent.BUTTON3) {
                        myLevel.objectPopupMenu(obj, e);
                    }
                    return;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3) {

            }

        }
    }

    @Override
    public void mouseMoved (MouseEvent e) {
        myLevel.setMouseLoc(e.getX(), e.getY());
    }

    @Override
    public void mouseDragged (MouseEvent e) {
        mouseMoved(e);
    }

    public GameObject getCurrent () {
        return myCurrent;
    }

    public void setCurrent (GameObject obj) {
        myCurrent = obj;
    }
}
