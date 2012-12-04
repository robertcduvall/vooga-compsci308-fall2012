package vooga.platformer.gameobject;

import java.io.Serializable;

public interface UpdateStrategy extends Serializable{
    public void applyAction();
}
