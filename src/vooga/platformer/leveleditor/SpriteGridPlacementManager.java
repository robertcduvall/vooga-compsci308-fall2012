package vooga.platformer.leveleditor;

import java.awt.Dimension;
import java.awt.Point;


/**
 * Creates a SpritePlacementManager that makes use of a grid that
 * aids construction of levels in the level editor.
 * 
 * @author Paul Dannenberg
 * 
 */
public class SpriteGridPlacementManager extends SpritePlacementManager {

    private Grid myGrid;
    private static final Dimension TILE_SIZE = new Dimension(50, 50);

    /**
     * Creates a new placement manager for a level board.
     * 
     * @param board The level board whose sprite placement
     *        this object should manage.
     */
    public SpriteGridPlacementManager (LevelBoard board) {
        super(board);
        myGrid = new Grid(new Point(), board.getSize(), TILE_SIZE);
        board.add(myGrid);
    }

    /**
     * Positions all selected sprites, adding them to the
     * levelboard. When added, the sprite will automatically snap to the
     * center of the closest tile in the grid.
     */
    @Override
    public void positionSprites () {
        for (Sprite sprite : getSelectedSprites()) {
            myGrid.positionSprite(sprite);
        }
        super.positionSprites();
    }

    /**
     * Causes all selected sprites to move based on the coordinates given
     * by x and y. Also highlights the tile located at x and y. 
     */
    @Override
    public void follow (int x, int y) {
        super.follow(x, y);
        myGrid.highlightTile(x, y);
    }

}
