package games.game.core;

import java.awt.Graphics2D;


/**
 * Specifies an interface for all game
 * objects that can be painted or can paint
 * something on the screen.
 * 
 * @author Paul Dannenberg
 * 
 */
public interface IPaintable {

    /**
     * Draws an object on the screen.
     * @param pen To be used to draw.
     */
    void paint(Graphics2D pen);

}
