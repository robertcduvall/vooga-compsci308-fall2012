package games.trexgame.interaction;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import util.graphicprocessing.FontEffect;
import vooga.turnbased.gameobject.optionobject.OptionBack;
import vooga.turnbased.gameobject.optionobject.OptionObject;
import vooga.turnbased.gui.GameWindow;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;


public class ConversationPanel extends InteractionPanel {

    private static final int FONT_SIZE = 18;
    private List<String> myConversationMessages;

    public ConversationPanel (List<OptionObject> options, List<String> messages) {
        super();
        final int POSITION_INDEX = 4;
        myConversationMessages = messages;
        options.add(makeQuitOption());
        for (int i = 0; i < options.size(); i++) {
            insertOption(options.get(i), i + POSITION_INDEX);
        }
    }

    public Image renderImage () {
        final int SPACE_BETWEEN_LINES = 20;
        Point currentPosition = getOptionPosition(0);
        Image panelImage = super.renderImage();
        Graphics g = panelImage.getGraphics();
        Font font = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        FontEffect fontEffect = new FontEffect(g, font);
        for (String message: myConversationMessages) {
            fontEffect.outlineEffect(message, Color.WHITE, Color.BLUE,
                                 currentPosition);
            currentPosition = new Point(currentPosition);
            currentPosition.y += fontMetrics.getHeight() + SPACE_BETWEEN_LINES;
        }
        return panelImage;
    }
    
	@Override
	protected void initializePanelImage() {
		try {
			setPanelImage(ImageIO.read(new File(GameWindow.importString("DialogueBox"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.initializePanelImage();
	}

    private OptionObject makeQuitOption () {
        OptionObject quitOption = OptionBack.getDefaultOptionObject("I don't care~!");
        return quitOption;
    }
}
