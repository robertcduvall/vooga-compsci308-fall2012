package games.trexgame.interaction;

import java.awt.Point;

import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;

public class StatsPanel extends InteractionPanel{
    
	@Override
	public boolean highlightOption(Point focusPosition) {
		return false;
	}
	
	@Override
	public void dehighlightOption() {
	}
	
	@Override
	public OptionObject triggerOption (Point focusPosition) {
		return getOptionByIndex(0);
	}
}
