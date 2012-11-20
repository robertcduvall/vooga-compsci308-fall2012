package vooga.platformer.leveleditor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import javax.swing.JLayeredPane;
import vooga.platformer.leveleditor.Sprite;


/**
 * This class represents an actual grid. This grid will be used to
 * help people using the level editor position objects on the map.
 * 
 * @author Paul Dannenberg
 * 
 */
public class Grid extends JLayeredPane {

    // LayeredPane correct?
    // TODO MAKE SURE NO INDEXOUTOFBOUNDS EXCEPTION! DO THIS FIRST!

    private static final long serialVersionUID = 2449587405419439040L;
    private GridTile[][] myGrid;
    private Dimension mySize;
    private GridTile myHighLightedTile;

    /**
     * Creates a new Grid object.
     * 
     * @param gridLocation The location of the grid's top left hand corner.
     * @param gridSize The dimension of the whole grid.
     * @param tileSize The dimension of each individual grid tile.
     */
    public Grid (Point2D gridLocation, Dimension gridSize, Dimension tileSize) {
        mySize = gridSize;
        myGrid = fillGrid(createGrid(gridSize, tileSize), tileSize);
    }

    /**
     * Fills up the grid which evenly spaced grid tiles.
     * 
     * @param toFill The array to be filled with grid tiles.
     * @param tileSize The dimension of the tiles to fill
     *        the array with.
     * @return An array filed with grid tiles.
     */
    private GridTile[][] fillGrid (GridTile[][] toFill, Dimension tileSize) {
        for (int i = 0; i < toFill.length; i++) {
            for (int j = 0; j < toFill[i].length; j++) {
                GridTile tile = new GridTile(tileSize.width, tileSize.height);
                tile.setX(i * tileSize.width);
                tile.setY(j * tileSize.height);
                toFill[i][j] = tile;
            }
        }
        return toFill;
    }

    /**
     * Creates an array to hold grid tiles
     * whose size is determined by the total grid size and tile size.
     * 
     * @param gridSize The size of the complete grid.
     * @param tileSize The size of each individual tile.
     * @return A newly created, empty array of the required size.
     */
    private GridTile[][] createGrid (Dimension gridSize, Dimension tileSize) {
        int numberRows = gridSize.height / tileSize.width;
        int numberColumns = gridSize.width / tileSize.width;
        return new GridTile[numberRows][numberColumns]; // +1 needed?
    }

    /**
     * Given a sprite, snaps the sprite to the center of the closest
     * tile in the grid.
     * 
     * @param sprite The sprite to position.
     */
    public void positionSprite (Sprite sprite) {
        GridTile tileToPositionSprite = findTile(sprite.getX(), sprite.getY());
        tileToPositionSprite.placeSprite(sprite);
    }

    /**
     * Finds the tile at a particular x location and y location.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The grid tile found at the specified location.
     */
    private GridTile findTile (int x, int y) {
        return myGrid[mySize.height / x][mySize.width / y];
    }

    /**
     * Highlights the tile located at the coordinates specified 
     * by the method parameters.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void highlightTile (int x, int y) {
        GridTile tileToHighLight = findTile(x, y);
        if (!tileToHighLight.equals(myHighLightedTile) && myHighLightedTile != null) {
            myHighLightedTile.unHighLight();
            tileToHighLight.highlight();
            myHighLightedTile = tileToHighLight;
        }
    }

    /**
     * Paints the entire grid.
     */
    @Override
    public void paint (Graphics pen) {
        super.paint(pen);
        for (GridTile[] row : myGrid) {
            for (GridTile tile : row) {
                tile.paint((Graphics2D) pen, this);
            }
        }
    }

}
