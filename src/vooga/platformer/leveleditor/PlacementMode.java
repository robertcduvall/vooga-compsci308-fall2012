package vooga.platformer.leveleditor;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import vooga.platformer.leveleditor.leveldrawer.IEditorObject;


/**
 * Responsible for placing sprites on the LevelBoard. This class
 * is a fairly simple implementation. It allows sprites to be
 * placed on the LevelBoard as long as they are not on top of
 * other sprites.
 * 
 * @author Paul Dannenberg
 * 
 */
public class PlacementMode implements IEditorMode {

    private IEditorObject mySelected;
    private Collection<IEditorObject> mySelectables;

    /**
     * Creates a new object. This object will oversee
     * the placement of editor objects.
     * 
     */
    public PlacementMode() {
        mySelectables = new ArrayList<IEditorObject>();
    }

    @Override
    public void sendCursorPosition(int x, int y) {
        if (mySelected != null) {
            mySelected.setCenter(x, y);
        }
    }

    @Override
    public void primaryButtonPress(int x, int y) {
        if (mySelected == null) {
            for (IEditorObject toSelect : mySelectables) {
                if (toSelect.contains(x, y)) {
                    mySelected = toSelect;
                }
            }
        } else {
            placeSelected(x, y);
        }
    }

    protected void placeSelected(int x, int y) {
        mySelected.setCenter(x, y);
        mySelected = null;
    }

    protected IEditorObject getSelected() {
        return mySelected;
    }

    protected void setSelected(IEditorObject selected) {
        mySelected = selected;
    }
    
    protected Collection<IEditorObject> getSelectables() {
        return mySelectables;
    }

    @Override
    public void secondaryButtonPress(int x, int y) {

    }

    @Override
    public void paint(Graphics2D pen) {
        for (IEditorObject toPaint : mySelectables) {
            toPaint.paint(pen);
        }
    }

}
