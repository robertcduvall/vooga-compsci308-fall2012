package vooga.platformer.core.inputinitializer;

import java.util.List;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.gameobject.GameObject;

/**
 * Represents classes responsible for setting up the user input.
 * @author Niel Lebeck
 *
 */
public interface InputInitializer {

    /**
     * Set up the input listener or controller, using the given list of objects
     * @param objects list of objects needed for input setup
     * @param platformControl reference to PlatformerController
     */
    public void setUpInput(List<GameObject> objects, PlatformerController platformControl);
}
