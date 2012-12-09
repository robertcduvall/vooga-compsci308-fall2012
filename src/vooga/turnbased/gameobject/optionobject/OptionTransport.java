package vooga.turnbased.gameobject.optionobject;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;


/**
 * subclass of Option Object that does a specific job
 * 
 * @author rex
 * 
 */
public class OptionTransport extends OptionObject {

    public OptionTransport (Set<String> allowableModes, String condition,
            String message) {
        super(allowableModes, condition, message);
    }

    public OptionTransport (Set<String> allowableModes, String condition,
            Image image, List<String> message) {
        super(allowableModes, condition, message.get(0));
    }

    public void executeOption (OptionMode optionMode) {
        List<Integer> involvedIDs = new ArrayList<Integer>();
        involvedIDs.add(getID());
        // each teleporter along with the flagged condition needs to supply id
        // of
        // teleported sprite (to transfer it), and its id (containing info about
        // destination)
        // involvedIDs.add(someHowGetPlayersId);
        optionMode.flagCondition(getConditionFlag(), involvedIDs);
    }
}
