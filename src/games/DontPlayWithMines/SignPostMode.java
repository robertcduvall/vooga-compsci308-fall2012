package games.DontPlayWithMines;

import games.trexgame.interaction.ConversationPanel;

import java.util.ArrayList;
import java.util.List;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.optionobject.OptionObject;

/**
 * Adapted from Rex's ConversationMode
 * 
 * @author David
 * 
 */
public class SignPostMode extends OptionMode {

	private List<String> myMessages;

	public SignPostMode(GameManager gm, String modeName,
			List<Integer> involvedIDs) {
		super(gm, modeName, involvedIDs);
		List<OptionObject> options = new ArrayList<OptionObject>();
		for (GameObject option : getGameObjects()) {
			if (option.getAllowableModes().contains(getName())) {
				options.add((OptionObject) option);
			}
		}
		myMessages = new ArrayList<String>();
		myMessages.add("The sign reads: ");
		setPanel(new ConversationPanel(options, myMessages));
	}
}
