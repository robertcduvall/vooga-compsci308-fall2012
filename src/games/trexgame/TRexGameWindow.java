package games.trexgame;

import vooga.turnbased.gui.GameWindow;

public class TRexGameWindow extends GameWindow {
    private final String myFilePath = "src/games/trexgame/TTTReXXX.xml";

    public TRexGameWindow (String title, String settingsResource, int width,
            int height) {
        super(title, settingsResource, width, height);
        setXmlPath(myFilePath);
        changeActivePane(GAME);
    }
}
