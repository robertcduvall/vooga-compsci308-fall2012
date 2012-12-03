package vooga.platformer.gameobject;

import java.io.Serializable;

/**
 * Strategy used to add control to a Player
 * @author Zach Michaelov
 */

public interface ControlStrategy extends Serializable {
    public void fire();
}
