package games.trexgame.interaction;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;


public class OptionConversation extends OptionObject {

    private List<String> myConversationMessages;

    public OptionConversation (Set<String> allowableModes, String condition, Image image, List<String> messages) {
        super(allowableModes, condition, messages.get(0));
        myConversationMessages = new ArrayList<String>();
        myConversationMessages.add("Some misterious guy:");
        myConversationMessages.add("    life is like a game");
        myConversationMessages.add("	but here is the reward");
    }

    public void executeOption (OptionMode optionMode) {
        InteractionPanel panel =
                new ConversationPanel(new ArrayList<OptionObject>(), myConversationMessages);
        optionMode.setPanel(panel);
    }
}
