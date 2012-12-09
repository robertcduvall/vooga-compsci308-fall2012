package games.trexgame.interaction;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;
import vooga.turnbased.sprites.Sprite;

/**
 * options user can choose in a conversation
 * 
 * @author rex
 * 
 */
public class OptionConversation extends OptionObject {

	private static final String STATS_INDICATOR = "*";
	private List<String> myConversationMessages;
	private Map<String, Integer> myStatsBoosts;
	private List<OptionObject> myChildrenObjects;
	private List<BattleObject> myBattleObjects;

	public OptionConversation(Set<String> allowableModes, String condition,
			Image image, List<String> messages) {
		super(allowableModes, condition, messages.get(0));
		String optionName = messages.get(0);
		messages.remove(optionName);
		myConversationMessages = new ArrayList<String>();
		myStatsBoosts = new HashMap<String, Integer>();
		for (String message : messages) {
			if (message.indexOf(STATS_INDICATOR) < 0) {
				myConversationMessages.add(message);
			} else {
				String[] s = processStatsBoost(message);
				// two parts: the stats name (eg. atk), and the value
				myStatsBoosts.put(s[0], Integer.parseInt(s[1]));
			}
		}
		messages.add(optionName);
		myChildrenObjects = new ArrayList<OptionObject>();
	}

	private String[] processStatsBoost(String message) {
		final String SEPARATOR = " ";
		String statsMessage = message.substring(1);
		String[] stats = statsMessage.split(SEPARATOR);
		return stats;
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

		Sprite playerSprite = optionMode.getGameManager().getPlayerSprite();
		myBattleObjects = playerSprite.getBattleObjects();
		for (String stats : myStatsBoosts.keySet()) {
			for (BattleObject battleObject : myBattleObjects) {
				battleObject.increaseStat(stats, myStatsBoosts.get(stats));
			}
		}
	}
}
