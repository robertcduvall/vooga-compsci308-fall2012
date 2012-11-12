package vooga.platformer.leveleditor;

import java.awt.Graphics2D;


/**
 * An interface specifying all paintable objects.
 * 
 * @author Paul
 * 
 */
public interface IPaintable {

    /**
     * This method should be called every time an
     * object should be painted on the screen.
     * 
     * @param pen The Graphics2D that will be used to
     *        draw the object that implements this interface.
     */
    void paint(Graphics2D pen);

}
