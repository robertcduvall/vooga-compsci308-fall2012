package games.trexgame.interaction;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;

public class OptionConversation extends OptionObject {

	private List<String> myConversationMessages;
	private List<OptionObject> myChildrenObjects;

	public OptionConversation(Set<String> allowableModes, String condition,
			Image image, List<String> messages) {
		super(allowableModes, condition, messages.get(0));
		myConversationMessages = new ArrayList<String>();
		for (int i = 1; i < messages.size(); i++) {
			myConversationMessages.add(messages.get(i));
		}
		myChildrenObjects = new ArrayList<OptionObject>();
	}

	public OptionConversation(Set<String> allowableModes, String condition,
			Image image, List<String> messages, List<GameObject> childrenObjects) {
		this(allowableModes, condition, image, messages);
		for (GameObject gameObject : childrenObjects) {
			myChildrenObjects.add((OptionObject) gameObject);
		}
	}

	public void executeOption(OptionMode optionMode) {
		InteractionPanel panel = new ConversationPanel(myChildrenObjects,
				myConversationMessages);
		optionMode.setPanel(panel);
	}
}
