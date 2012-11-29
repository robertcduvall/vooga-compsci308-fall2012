package vooga.turnbased.gamecore;

import java.awt.Graphics;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;

/**
 * Choose different options through an interaction panel and talk with NPCs
 * 
 * @author rex
 * 
 */
public class ConversationMode extends GameMode {

    private MapObject myNPC;
    private InteractionPanel myPanel;
    
    public ConversationMode (GameManager gm, Class modeObjectType, MapObject NPC) {
        super(gm, modeObjectType);
        myPanel = new InteractionPanel();
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
        
    }

    @Override
    public void paint (Graphics g) {
        myPanel.paint(g);
    }

    @Override
    public void update () {
        
    }

}
