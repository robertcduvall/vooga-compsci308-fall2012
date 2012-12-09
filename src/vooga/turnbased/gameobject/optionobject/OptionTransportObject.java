package vooga.turnbased.gameobject.optionobject;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;

/**
 * subclass of Option Object that does a specific job
 * @author rex
 *
 */
public class OptionTransportObject extends OptionObject {

    public OptionTransportObject (Set<String> allowableModes, String condition, String message) {
        super(allowableModes, condition, message);
    }
    
    public OptionTransportObject (Set<String> allowableModes, String condition, Image image, List<String> message) {
    	super(allowableModes, condition, message.get(0));
    }
    
    public void executeOption(OptionMode optionMode) {
        super.executeOption(optionMode);
    }
}
