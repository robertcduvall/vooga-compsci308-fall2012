package vooga.turnbased.gameobject.optionobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gui.interactionpanel.ConversationPanel;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;


public class OptionConversation extends OptionObject {

    private List<String> myConversationMessages;

    public OptionConversation (Set<String> allowableModes, String condition, String message) {
        super(allowableModes, condition, message);
        myConversationMessages = new ArrayList<String>();
        myConversationMessages.add("Professor Oak:");
        myConversationMessages.add("    life is like a game");
    }

    public void executeOption (OptionMode optionMode) {
        InteractionPanel panel =
                new ConversationPanel(new ArrayList<OptionObject>(), myConversationMessages);
        optionMode.setPanel(panel);
    }
}
