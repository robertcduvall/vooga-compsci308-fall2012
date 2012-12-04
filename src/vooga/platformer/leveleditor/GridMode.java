package vooga.platformer.leveleditor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import vooga.platformer.leveleditor.leveldrawer.IEditorObject;


/**
 * Creates an object that allows users to select objects and position
 * them on the screen. When an object is positioned, it will immediately
 * snap to the center of the closest gridtile.
 * 
 * @author Paul Dannenberg
 * 
 */
public class GridMode extends PlacementMode {

    private Grid myGrid;

    /*
     * I have put this constant in this class since the size of each
     * grid tile really should be related to the size of the actual
     * screen that the grid is being displayed on.
     */
    private static final Dimension TILE_SIZE = new Dimension(50, 50);
    private static final Dimension DEFAULT_GRID_SIZE = new Dimension(600, 400);

    /**
     * Creates a new object capable of managing selections of objects.
     * The grid size is set to a default size of 600 x 400 pixels.
     */
    public GridMode () {
        super();
        myGrid = new Grid(new Point(), DEFAULT_GRID_SIZE, TILE_SIZE);
    }

    /**
     * Creates an object capable of managing selections of objects.
     * 
     * @param size The size of the total grid.
     */
    public GridMode (Dimension size) {
        super();
        myGrid = new Grid(new Point(), size, TILE_SIZE);
    }

    @Override
    public void sendCursorPosition (int x, int y) {
        super.sendCursorPosition(x, y);
        myGrid.highlightTile(x, y);
    }

    @Override
    public void primaryButtonPress (int x, int y) {
        if (getSelected() == null) {
            for (IEditorObject toSelect : getSelectables()) {
                if (toSelect.contains(x, y) && !myGrid.containsTile(toSelect)) {
                    setSelected(toSelect);
                }
            }
        }
        else {
            myGrid.position(getSelected());
            setSelected(null);
        }
    }

    @Override
    public void paint (Graphics2D pen) {
        myGrid.paint(pen);
        super.paint(pen);
    }
}
