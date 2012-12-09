package games.game.core;

/**
 * Specifies all objects that can be updated. The
 * update method will be called once per frame.
 * 
 * @author Paul Dannenberg
 *
 */
public interface IUpdatable {

    /**
     * Updates the state of the object.
     */
    void update();
    
}
