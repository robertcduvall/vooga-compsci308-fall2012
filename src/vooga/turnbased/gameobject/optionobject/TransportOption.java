package vooga.turnbased.gameobject.optionobject;

import java.awt.Image;
import java.util.Set;

public class TransportOption extends OptionObject {

    public TransportOption (Set<String> allowableModes, String condition, Image image, String message) {
        super(allowableModes, condition, message);
    }

}
