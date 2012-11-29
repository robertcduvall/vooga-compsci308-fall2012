package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
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
    private Image myPanelImage;
    
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
        update();
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
