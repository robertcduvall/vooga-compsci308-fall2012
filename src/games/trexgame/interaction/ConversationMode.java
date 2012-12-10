package games.trexgame.interaction;

import java.util.ArrayList;
import java.util.List;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;

/**
 * Conversation mode
 * @author rex
 *
 */
public class ConversationMode extends OptionMode {
	
	private List<String> myMessages;

	public ConversationMode(GameManager gm, String modeName,
			List<Integer> involvedIDs) {
		super(gm, modeName, involvedIDs);
		List<OptionObject> options = new ArrayList<OptionObject>();
		for (GameObject option : getGameObjects()) {
	        options.add((OptionObject) option);
	    }
		myMessages = new ArrayList<String>();
		myMessages.add("You found a Box!!");
		myMessages.add("in which is a random guy =.=");
	    setPanel(new ConversationPanel(options, myMessages));
	}
}
