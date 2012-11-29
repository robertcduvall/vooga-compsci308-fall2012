package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapstrategy.MapStrategy;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;


/**
 * Choose different options through an interaction panel and talk with NPCs
 * 
 * @author rex
 * 
 */
public class ConversationMode extends GameMode {

    private static final int NPC_INDEX = 0;
    private static final int PLAYER_INDEX = 1;
    private MapObject myNPC;
    private MapObject myPlayer;
    private InteractionPanel myPanel;
    private Image myPanelImage;
    private Map<String, MapStrategy> myDisplayedStrategies;

    public ConversationMode (GameManager gm, Class modeObjectType, List<Integer> involvedIDs) {
        super(gm, modeObjectType);
        myDisplayedStrategies = new HashMap<String, MapStrategy>();
        myNPC = gm.findSpriteWithID(involvedIDs.get(NPC_INDEX)).getMapObject();
        myPlayer = gm.findSpriteWithID(involvedIDs.get(PLAYER_INDEX)).getMapObject();
        List<MapStrategy> optionStrategies = myNPC.getDisplayableStrategies();
        for (MapStrategy strategy: optionStrategies) {
            myDisplayedStrategies.put(strategy.getDisplayMessage(), strategy);
        }
        myPanel = new InteractionPanel(myDisplayedStrategies.keySet());
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
        initialize();
    }

    @Override
    public void initialize () {
        update();
    }

    public boolean equalsTo (ConversationMode conversation) {
        if (myNPC == conversation.myNPC)
            System.out.println("equals");
        return myNPC == conversation.myNPC;
    }

    @Override
    public void paint (Graphics g) {
        myPanelImage = myPanel.renderImage();
        Point position = getDefaultPosition();
        g.drawImage(myPanelImage, position.x, position.y, null);
    }

    private Point getDefaultPosition () {
        int windowWidth = getGameManager().getPaneDimension().width;
        int windowHeight = getGameManager().getPaneDimension().height;
        int x = (windowWidth - myPanel.getPanelSize().width) / 2;
        int y = (windowHeight - myPanel.getPanelSize().height) / 2;
        return new Point(x, y);
    }

    @Override
    public void update () {

    }

}
