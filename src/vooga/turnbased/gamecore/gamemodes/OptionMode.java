package vooga.turnbased.gamecore.gamemodes;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.mapobject.MapObject;
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
	private MapObject myPlayer;
	private List<Integer> myInvolvedIDs;
	private InteractionPanel myPanel;
	private Image myPanelImage;
	private Point myOrigin;
	private Map<String, OptionObject> myOptions;
	private Rectangle myBounds;
	private Stack<InteractionPanel> myPanelStack;
	private boolean myIsDefaultPanelUsed;

	/**
	 * Constructor.
	 * 
	 * @param gm
	 *            GameManager to which the mode belongs.
	 * @param modeName
	 *            Name of mode.
	 * @param involvedIDs
	 *            List of IDs of sprites involved in the mode.
	 */
	public OptionMode(GameManager gm, String modeName, List<Integer> involvedIDs) {
		super(gm, modeName, involvedIDs);
		myPanelStack = new Stack<InteractionPanel>();
		myNPC = findMapObjectByIndex(involvedIDs, NPC_INDEX);
		myPlayer = findMapObjectByIndex(involvedIDs, PLAYER_INDEX);
		myInvolvedIDs = involvedIDs;
		List<OptionObject> options = new ArrayList<OptionObject>();
		myOptions = new HashMap<String, OptionObject>();
		for (GameObject option : getGameObjects()) {
			options.add((OptionObject) option);
			myOptions.put(((OptionObject) option).getMessage(),
					(OptionObject) option);
		}
		// this is the default interaction panel
		// subclasses will call setPanel to change the default ones
		myPanel = new InteractionPanel(options);
		// if myIsDefaultPanelUsed is true, the panel is discarded instead of
		// saving into the panel stack
		myIsDefaultPanelUsed = true;
		myOrigin = getDefaultPosition();
	}

	/**
	 * find the mapobject using index
	 * 
	 * @param involvedIDs
	 *            a list of involved IDs for an event
	 * @param index
	 *            the index in the list
	 * @return the MapObject inside the Sprites specified by the ID
	 */
	private MapObject findMapObjectByIndex(List<Integer> involvedIDs, int index) {
		return getGameManager().findSpriteWithID(involvedIDs.get(index))
				.getMapObject();
	}

	@Override
	public void pause() {
		setModeIsOver();
	}

	@Override
	public void resume() {
	}

	@Override
	public void initialize() {
		update();
	}

	/**
	 * Returns a boolean of whether the current option mode's NPC is equal to
	 * that of another OptionMode.
	 * 
	 * @param conversation
	 *            OptionMode to compare to.
	 * @return
	 */
	public boolean equalsTo(OptionMode conversation) {
		return myNPC == conversation.myNPC;
	}

	@Override
	public void paint(Graphics g) {
		myPanelImage = myPanel.renderImage();
		g.drawImage(myPanelImage, myOrigin.x, myOrigin.y, null);
	}

	private Point getDefaultPosition() {
		int windowWidth = getGameManager().getPaneDimension().width;
		int windowHeight = getGameManager().getPaneDimension().height;
		int x = (windowWidth - myPanel.getPanelSize().width) / 2;
		int y = (windowHeight - myPanel.getPanelSize().height) / 2;
		return new Point(x, y);
	}

	@Override
	public void update() {
		// option mode does not get updated now because it contains no animation
		// subclass could override this
	}

	private Point getPositionOnPanel(Point position) {
		int x = position.x - myOrigin.x;
		int y = position.y - myOrigin.y;
		return new Point(x, y);
	}

	@Override
	public void processMouseInput(int mousePressed, Point mousePosition,
			int mouseButton) {
		if (mousePressed == GamePane.MOUSE_PRESSED) {
			myBounds = new Rectangle(myOrigin, myPanel.getPanelSize());
			if (myBounds.contains(mousePosition)) {
				setFocus(!myPanel
						.highlightOption(getPositionOnPanel(mousePosition)));
			}
		} else if (mousePressed == GamePane.MOUSE_DRAGGED && hasFocus()) {
			Point pressedPosition = myPanel.getPreviousPosition();
			mousePosition.translate(-pressedPosition.x, -pressedPosition.y);
			myOrigin = mousePosition;
		} else if (mousePressed == GamePane.MOUSE_RELEASED) {
			myPanel.dehighlightOption();
		} else {
			OptionObject selectedOption = myPanel
					.triggerOption(getPositionOnPanel(mousePosition));
			if (selectedOption != null) {
				selectedOption.executeOption(this);
			}
		}
	}

	/**
	 * set the panel after pushing the current panel into the panel stack
	 * 
	 * @param panel
	 */
	public void setPanel(InteractionPanel panel) {
		if (!myIsDefaultPanelUsed) {
			myPanelStack.add(myPanel);
		}
		else {
			myIsDefaultPanelUsed = false;
		}
		myPanel = panel;
	}

	/**
	 * go back to the previous panel
	 */
	public void goBack() {
		try {
			myPanel = myPanelStack.pop();
		} catch (EmptyStackException e) {
			setModeIsOver();
		}
	}

	public List<Integer> getInvolvedIDs() {
		return myInvolvedIDs;
	}
}
