package vooga.turnbased.gameobject.optionobject;

import java.util.ArrayList;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;

/**
 * subclass of Option Object that does a specific job
 * @author rex
 *
 */
public class TransportOption extends OptionObject {

    public TransportOption (Set<String> allowableModes, String condition, String message) {
        super(allowableModes, condition, message);
    }
    
    public void executeOption(OptionMode optionMode) {
        optionMode.flagCondition("entermap2", new ArrayList<Integer>());
    }
}