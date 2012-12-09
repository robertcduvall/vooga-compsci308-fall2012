package games.trexgame.interaction;

import java.util.ArrayList;
import java.util.List;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.sprites.Sprite;

public class StatsMode extends OptionMode{

	private List<BattleObject> myBattleObjects;
	
	public StatsMode(GameManager gm, String modeName,
			List<Integer> involvedIDs) {
		super(gm, modeName, involvedIDs);
		Sprite playerSprite = getGameManager().getPlayerSprite();
		myBattleObjects = playerSprite.getBattleObjects();
		setPanel(new StatsPanel(myBattleObjects));
	}
}
