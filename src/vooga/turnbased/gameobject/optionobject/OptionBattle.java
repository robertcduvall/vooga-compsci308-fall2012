package vooga.turnbased.gameobject.optionobject;

import java.awt.Color;
import java.util.Set;
import util.graphicprocessing.FontEffect;
import vooga.turnbased.gamecore.gamemodes.OptionMode;

public class OptionBattle extends OptionObject {

    public OptionBattle (Set<String> allowableModes, String condition, String message) {
        super(allowableModes, condition, message);
    }
    
    public void executeOption (OptionMode optionMode) {
        optionMode.flagCondition(getConditionFlag(), optionMode.getInvolvedIDs());
    }
    
    @Override
    protected void paintMessage (FontEffect fontEffect) {
        final Color TOP_COLOR = new Color(56, 255, 69);
        final Color SIDE_COLOR = new Color(20, 150, 20);
        final int LAYER = 4;
        fontEffect.threeDimensionEffect(getMessage(), getColor(), TOP_COLOR, SIDE_COLOR, LAYER,
                                        getPosition());
    }
}
