package vooga.shooter.gameObjects.spriteUtilities;

/**
 * This class simply provides an interface for
 * an action that the sprites will do. Each sprite
 * can determine their own action(s).
 *
 * @author Jesse Starr
 *
 */
public interface SpriteActionInterface {

    /**
     * This method will describe what actions
     * to do. It will be mapped (as a value) to
     * a string (the key).
     *
     * @param o a (possibly empty) list of parameters
     */
    public void doAction(Object...o);
}
