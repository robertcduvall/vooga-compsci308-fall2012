package vooga.turnbased.gameobject.optionobject;

import java.util.ArrayList;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gui.interactionpanel.ConversationPanel;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;

public class OptionBattle extends OptionObject {

    public OptionBattle (Set<String> allowableModes, String condition, String message) {
        super(allowableModes, condition, message);
    }
    
    public void executeOption (OptionMode optionMode) {
        InteractionPanel panel =
                new ConversationPanel(new ArrayList<OptionObject>(), myConversationMessages);
        optionMode.setPanel(panel);
    }
}
