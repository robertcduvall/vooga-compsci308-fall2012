package games.trexgame.interaction;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import util.graphicprocessing.FontEffect;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;

/**
 * GUI panel for displaying stats
 * 
 * @author rex
 * 
 */
public class StatsPanel extends InteractionPanel {

	private static final int FONT_SIZE = 16;
	private List<String> myStats;
	private List<BattleObject> myBattleObjects;

	public StatsPanel(List<BattleObject> battleObjects) {
		super();
		myStats = new ArrayList<String>();
		myBattleObjects = battleObjects;
		writeStats();
		List<OptionObject> options = new ArrayList<OptionObject>();
		OptionObject attack = OptionObject.getDefaultOptionObject("ATTACK: ");
		setBackground(GameWindow.importString("StatsBackround"));
	}

	private void writeStats() {
		myStats.add("Number of creatures: " + myBattleObjects.size());
		for (BattleObject battleObject : myBattleObjects) {
			myStats.add(battleObject.getName() + " has an attack of "
					+ battleObject.getStat("atk"));
		}
	}

	@Override
	public OptionObject triggerOption(Point focusPosition) {
		return getOptionByIndex(0);
	}

	@Override
	public Image renderImage() {
		final int SPACE_BETWEEN_LINES = 20;
		Point currentPosition = getOptionPosition(0);
		Image panelImage = super.renderImage();
		Graphics g = panelImage.getGraphics();
		Font font = new Font("Helvetica", Font.BOLD, FONT_SIZE);
		FontMetrics fontMetrics = g.getFontMetrics(font);
		FontEffect fontEffect = new FontEffect(g, font);
		for (String message : myStats) {
			fontEffect.outlineEffect(message, Color.BLACK, Color.YELLOW,
					currentPosition);
			currentPosition = new Point(currentPosition);
			currentPosition.y += fontMetrics.getHeight() + SPACE_BETWEEN_LINES;
		}
		return panelImage;
	}
}
