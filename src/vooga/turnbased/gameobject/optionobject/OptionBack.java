package vooga.turnbased.gameobject.optionobject;

import java.util.HashSet;
import java.util.Set;

import vooga.turnbased.gamecore.gamemodes.OptionMode;

/**
 * go back to the previous interaction panel in the option mode
 * 
 * @author rex
 * 
 */
public class OptionBack extends OptionObject{

    public OptionBack(Set<String> allowableModes, String condition,
			String message) {
		super(allowableModes, condition, message);
	}
    
    /**
     * default option: back to the previous menu
     */
    public static OptionObject getDefaultOptionObject (String quitMessage) {
        Set<String> allowableModes = new HashSet<String>();
        allowableModes.add("option");
        OptionObject defaultOption = new OptionBack(allowableModes,
                "NO_ACTION", quitMessage);
        return defaultOption;
    }

	public void executeOption (OptionMode myOptionMode) {
        myOptionMode.goBack();
    }
    
}
