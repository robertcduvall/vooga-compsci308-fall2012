package vooga.turnbased.gamecore.gamemodes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapstrategy.MapStrategy;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.interactionpanel.InteractionPanel;


/**
 * Choose different options through an interaction panel and talk with NPCs
 * 
 * @author rex
 * 
 */
public class OptionMode extends GameMode {

    private static final int NPC_INDEX = 0;
    private static final int PLAYER_INDEX = 1;
    private MapObject myNPC;
    private MapObject myPlayer;
    private InteractionPanel myPanel;
    private Image myPanelImage;
    private Point myOrigin;
    private Map<String, MapStrategy> myDisplayedStrategies;
    private Rectangle myBounds;

    public OptionMode (int ID, GameManager gm, Class modeObjectType, List<Integer> involvedIDs) {
        super(ID, gm, modeObjectType);
        myDisplayedStrategies = new HashMap<String, MapStrategy>();
        myNPC = findMapObjectByIndex(involvedIDs, NPC_INDEX);
        myPlayer = findMapObjectByIndex(involvedIDs, PLAYER_INDEX);
        List<MapStrategy> optionStrategies = myNPC.getDisplayableStrategies();
        for (MapStrategy strategy: optionStrategies) {
            myDisplayedStrategies.put(strategy.getDisplayMessage(), strategy);
        }
        myPanel = new InteractionPanel(myDisplayedStrategies.keySet());
        myOrigin = getDefaultPosition();
    }

    private MapObject findMapObjectByIndex(List<Integer> involvedIDs, int index) {
        return getGameManager().findSpriteWithID(involvedIDs.get(index)).getMapObject();
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

    public boolean equalsTo (OptionMode conversation) {
        return myNPC == conversation.myNPC;
    }

    @Override
    public void paint (Graphics g) {
        myPanelImage = myPanel.renderImage();
        g.drawImage(myPanelImage, myOrigin.x, myOrigin.y, null);
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
    
    /**
     * change the position of the conversation box
     */
    @Override
    public void changeDisplayPosition (Point currentPosition) {
        if (hasFocus()) {
            Point pressedPosition = myPanel.getPreviousPosition();
            currentPosition.translate(-pressedPosition.x, -pressedPosition.y);
            myOrigin = currentPosition;
        }
    }
    
    private Point getPositionOnPanel(Point position) {
        int x = position.x - myOrigin.x;
        int y = position.y - myOrigin.y;
        return new Point(x, y);
    }

    @Override
    public void processMouseInput (int mousePressed, Point myMousePosition, int myMouseButton) {
        if(mousePressed == GamePane.MOUSE_PRESSED){
            myBounds = new Rectangle(myOrigin, myPanel.getPanelSize());
            if (myBounds.contains(myMousePosition)) {
                //myPanel.highlightOption(getPositionOnPanel(myMousePosition));
                //setFocus(true);
                setFocus(!myPanel.highlightOption(getPositionOnPanel(myMousePosition)));
                //myDisplayedStrategies.get("Next Level!").performStrategy(myPlayer); error?
            }
        } else if (mousePressed == GamePane.MOUSE_RELEASED){
            myPanel.dehighlightOption();
        } else {
            getGameManager().flagEvent("SWITCH_LEVEL", new ArrayList<Integer>());
        }
    }
}
