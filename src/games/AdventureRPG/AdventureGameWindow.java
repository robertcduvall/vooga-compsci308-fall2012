package games.AdventureRPG;

import vooga.turnbased.gui.GameWindow;

public class AdventureGameWindow extends GameWindow {
    private final String myFilePath = "src/games/AdventureRPG/AdventureRPGMap.xml";

    public AdventureGameWindow (String title, String settingsResource, int width,
            int height) {
        super(title, settingsResource, width, height);
        setXmlPath(myFilePath);
        changeActivePane(GAME);
    }
}
