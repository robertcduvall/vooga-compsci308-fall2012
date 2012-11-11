package vooga.platformer.leveleditor;

import java.awt.Rectangle;


/**
 * Classes responsible for sprite placement
 * on a LevelBoard should consider implementing this interface.
 * 
 * @author Paul Dannenberg
 * 
 */
public interface ISpritePlacementManager {

    /**
     * Calls the sprite's getLocation() method
     * to determine if that sprite, at its
     * current location can be placed on the
     * LevelBoard.
     * 
     * @param sprite The sprite to place on the
     *        LevelBoard.
     * @return
     */
    boolean isValidPosition (Sprite sprite);

    /**
     * Places a sprite on a LevelBoard, if
     * that position is valid. Validity is
     * determined by the individual subclass
     * of this interface.
     * 
     * @param toPosition The sprite to be
     *        placed on the LevelBoard.
     */
    void positionSprite (Sprite toPosition);

    /**
     * Selects a sprite which can now be
     * moved by the user.
     * 
     * @param toSelect The sprite that the
     *        user has selected.
     */
    void selectSprite (Sprite toSelect);

    /**
     * Removes a sprite from the LevelBoard.
     * @param toClear
     */
    void clearSprite (Sprite toClear);

    /**
     * Selects any sprites whose outlines
     * overlap with the region determined
     * by the parameter, region.
     * @param region If any Sprite's outline
     * intersects with this Rectangle, the
     * sprite will be selected and can be 
     * controlled by user input. 
     */
    void selectRegion (Rectangle region);

}
