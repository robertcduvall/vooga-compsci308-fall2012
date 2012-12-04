package vooga.turnbased.gamecore.gamemodes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapstrategy.MapStrategy;
import vooga.turnbased.gameobject.optionobject.OptionObject;
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
    private InteractionPanel myPanel;
    private Image myPanelImage;
    private Point myOrigin;
    private Map<String, MapStrategy> myDisplayedStrategies;
    private Map<String, OptionObject> myOptions;
    private Rectangle myBounds;

    /**
     * Constructor.
     * 
     * @param gm GameManager to which the mode belongs.
     * @param modeName Name of mode.
     * @param involvedIDs List of IDs of sprites involved in the mode.
     */
    public OptionMode (GameManager gm, String modeName, List<Integer> involvedIDs) {
        super(gm, modeName, involvedIDs);
        myDisplayedStrategies = new HashMap<String, MapStrategy>();
        myNPC = findMapObjectByIndex(involvedIDs, NPC_INDEX);
        findMapObjectByIndex(involvedIDs, PLAYER_INDEX);
        List<MapStrategy> optionStrategies = myNPC.getDisplayableStrategies();
        for (MapStrategy strategy : optionStrategies) {
            myDisplayedStrategies.put(strategy.getDisplayMessage(), strategy);
        }
        List<OptionObject> options = new ArrayList<OptionObject>();
        myOptions = new HashMap<String, OptionObject>();
        for (GameObject option : getGameObjects()) {
            options.add((OptionObject) option);
            myOptions.put(((OptionObject) option).getMessage(), (OptionObject) option);
        }
        myPanel = new InteractionPanel(options);
        myOrigin = getDefaultPosition();
    }

    /**
     * find the mapobject using index
     * 
     * @param involvedIDs a list of involved IDs for an event
     * @param index the index in the list
     * @return the MapObject inside the Sprites specified by the ID
     */
    private MapObject findMapObjectByIndex (List<Integer> involvedIDs, int index) {
        return getGameManager().findSpriteWithID(involvedIDs.get(index)).getMapObject();
    }

    @Override
    public void pause () {
        setModeIsOver();
    }

    @Override
    public void resume () {
        initialize();
    }

    @Override
    public void initialize () {
        update();
    }

    /**
     * Returns a boolean of whether the current option mode's NPC is equal to
     * that of another
     * OptionMode.
     * 
     * @param conversation OptionMode to compare to.
     * @return
     */
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
        //option mode does not get updated now because it contains no animation
        //subclass could override this
    }

    private Point getPositionOnPanel (Point position) {
        int x = position.x - myOrigin.x;
        int y = position.y - myOrigin.y;
        return new Point(x, y);
    }

    @Override
    public void processMouseInput (int mousePressed, Point mousePosition, int mouseButton) {
        if (mousePressed == GamePane.MOUSE_PRESSED) {
            myBounds = new Rectangle(myOrigin, myPanel.getPanelSize());
            if (myBounds.contains(mousePosition)) {
                setFocus(!myPanel.highlightOption(getPositionOnPanel(mousePosition)));
            }
        }
        else if (mousePressed == GamePane.MOUSE_DRAGGED && hasFocus()) {
            Point pressedPosition = myPanel.getPreviousPosition();
            mousePosition.translate(-pressedPosition.x, -pressedPosition.y);
            myOrigin = mousePosition;
        }
        else if (mousePressed == GamePane.MOUSE_RELEASED) {
            myPanel.dehighlightOption();
        }
        else {
            OptionObject selectedOption = myPanel.triggerOption(getPositionOnPanel(mousePosition));
            if (selectedOption != null) {
                selectedOption.executeOption(this);
            }
        }
    }
}
