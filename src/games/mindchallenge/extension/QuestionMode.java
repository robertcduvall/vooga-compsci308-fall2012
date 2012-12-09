package games.mindchallenge.extension;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import util.input.core.KeyboardController;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.GameMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gui.GameWindow;


/**
 * Custom user-written game mode in which user answers 
 * questions. By using multiple QuestionModes as nodes in the
 * graph, and QuestionObjects as edges, conversation trees 
 * of any complexity can be built.
 * 
 * @author zavidovych
 *
 */
public class QuestionMode extends GameMode {
    private static double RELATIVE_DIALOGUE_BOX_SIZE = 0.9;
    private static double DIALOGUE_BOX_DRAWABLE_AREA = 0.8;
    private Rectangle myDialogueBox;
    private int myObjectCount;

    /**
     * Constructor for question mode
     * 
     * @param gm GameManager to interact with game
     * @param modeName Name of game mode
     * @param involvedIDs List of involved sprites' ids
     */
    public QuestionMode (GameManager gm, String modeName, List<Integer> involvedIDs) {
        super(gm, modeName, involvedIDs);
    }

    @Override
    public void initialize () {
        configureInputHandling();
        myDialogueBox = new Rectangle();
        myObjectCount = getGameObjects().size();
    }

    @Override
    public void configureInputHandling () {
        super.configureInputHandling();
        try {
            getGameManager().getKeyboardController().setControl(KeyEvent.VK_Q,
                                                                KeyboardController.RELEASED, this,
                                                                "setModeIsOver");
        }
        catch (NoSuchMethodException e) {
            System.out.println("A method was called that does not exist!");
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void paint (Graphics g) {
        paintMode(g);
        paintObjects(g);
    }

    private void paintMode (Graphics g) {
        Image background = GameWindow.importImage("DialogueBoxImage");
        Dimension paneDim = getGameManager().getPaneDimension();
        g.drawImage(background, myDialogueBox.x, myDialogueBox.y, myDialogueBox.width,
                    myDialogueBox.height, null);
    }

    private void paintObjects (Graphics g) {
        for (GameObject go : getGameObjects()) {
            QuestionObject qo = (QuestionObject) go;
            qo.paint(g);
        }
    }

    @Override
    public void update () {
        recalculateDialogueBoxSize();
        updateObjects();
    }

    private void recalculateDialogueBoxSize () {
        Dimension pane = getGameManager().getPaneDimension();
        int originCornerX = (int) (pane.width * 0.5 * (1 - RELATIVE_DIALOGUE_BOX_SIZE));
        int originCornerY = (int) (pane.height * 0.5 * (1 - RELATIVE_DIALOGUE_BOX_SIZE));
        myDialogueBox.x = originCornerX;
        myDialogueBox.y = originCornerY;
        myDialogueBox.width = (int) (pane.width * RELATIVE_DIALOGUE_BOX_SIZE);
        myDialogueBox.height = (int) (pane.height * RELATIVE_DIALOGUE_BOX_SIZE);
    }

    private void updateObjects () {
        int objectCount = 0;
        for (GameObject go : getGameObjects()) {
            QuestionObject qo = (QuestionObject) go;
            qo.setMouseTargetBox(calculateItemRectangle(objectCount));
            objectCount++;
        }
    }

    private Rectangle calculateItemRectangle (int objectCount) {
        Rectangle targetBox = new Rectangle();
        Rectangle drawingArea = shrinkRect(myDialogueBox, DIALOGUE_BOX_DRAWABLE_AREA);
        int itemHeight = drawingArea.height / myObjectCount;
        targetBox.x = drawingArea.x;
        targetBox.y = drawingArea.y + itemHeight * objectCount;
        targetBox.height = itemHeight;
        targetBox.width = drawingArea.width;
        return targetBox;
    }

    private Rectangle shrinkRect (Rectangle box, double shrink) {
        Rectangle result = new Rectangle();
        result.x = (int) (box.x + box.width * (1 - shrink) / 2);
        result.y = (int) (box.y + box.height * (1 - shrink) / 2);
        result.width = (int) (box.width * shrink);
        result.height = (int) (box.height * shrink);
        return result;
    }

    @Override
    public void processMouseInput (int mousePressed, Point mousePosition, int mouseButton) {
        if (!myDialogueBox.contains(mousePosition)) {
            setModeIsOver();
        }
        else {
            for (GameObject go : getGameObjects()) {
                QuestionObject qo = (QuestionObject) go;
                qo.processMouseInput(mousePressed, mousePosition, this);
            }
        }
    }

    /**
     * Get id of whoever triggered interaction
     * 
     * @return id of sprite
     */
    public Integer getPlayerID () {
        for (Integer spriteID : getInvolvedIDs()) {
            if (spriteID == getGameManager().getPlayerSpriteID()) { return spriteID; }
        }
        return -1;
    }
}
