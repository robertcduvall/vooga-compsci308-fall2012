package games.trexgame.interaction;

import java.util.ArrayList;
import java.util.List;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.optionobject.OptionObject;

public class StatsMode extends OptionMode{

	public StatsMode(GameManager gm, String modeName,
			List<Integer> involvedIDs) {
		super(gm, modeName, involvedIDs);
		List<OptionObject> options = new ArrayList<OptionObject>();
		setPanel(new StatsPanel());
	}
}
