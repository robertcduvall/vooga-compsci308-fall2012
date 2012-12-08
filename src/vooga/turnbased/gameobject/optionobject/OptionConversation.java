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

    /*
    protected void paintMessage (FontEffect fontEffect) {
        final Color TOP_COLOR = new Color(56, 255, 69);
        final Color SIDE_COLOR = new Color(20, 150, 20);
        final int LAYER = 4;
        fontEffect.threeDimensionEffect(getMessage(), getColor(), TOP_COLOR, SIDE_COLOR, LAYER,
                                        getPosition());
    }*/
}
