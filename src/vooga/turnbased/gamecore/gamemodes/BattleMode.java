package vooga.turnbased.gamecore.gamemodes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import util.graphicprocessing.FontEffect;
import util.input.core.KeyboardController;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.InputAPI;


/**
 * This is a gamemode that will run a battle given two lists of BattleObjects
 * that
 * will fight each other.
 * Currently, 4 battle options are supported: attack, defend, charge, heal.
 * They can be modified and extended.
 * 
 * @author David Howdyshell, Michael Elgart, Kevin Gao, Jenni Mercado, Tony
 * 
 */
public class BattleMode extends GameMode implements InputAPI {
    private Team myTeam;
    private Team myEnemyTeam;
    private BattleObject myPlayerObject;
    private BattleObject myEnemyObject;
    private int myLoserSpriteID;
    private List<Integer> myInvolvedIDs;
    private List<String> myMessages;
    private int mySelection;

    private final int MESSAGE_NUM = 4;
    private final int HEIGHT_SCALAR = 3;
    private final double TEXT_SCALAR = 40;

    private final double SHIFT_LEFT_SCALAR = .15;
    private final double SHIFT_RIGHT_SCALAR = .60;
    private final double SHIFT_TOP_SCALAR = .45;
    private final double SHIFT_BOTTOM_SCALAR = .75;
    private final double ADJUST_ARROW_SCALAR = .80;
    
    private final int OPTION1 = 1;
    private final int OPTION2 = 2;
    private final int OPTION3 = 3;
    private final int OPTION4 = 4;
    

    private final String myMENU_FONT = "Sans_Serif";

    /**
     * Constructor for a Battle.
     * 
     * @param gameManager The parent GameManager that is creating this battle. Will be
     *        alerted when battle ends.
     * @param modeName The name of this BattleMode
     * @param involvedIDs A list of IDs of the sprites involved in this battle.
     */
    public BattleMode (GameManager gameManager, String modeName, List<Integer> involvedIDs) {
        super(gameManager, modeName, involvedIDs);
        myInvolvedIDs = involvedIDs;
        myMessages = new ArrayList<String>();
        resume();
    }

    /**
     * Initializes a battle with the current lists of BattleObjects
     */
    @Override
    public void initialize () {
        mySelection = OPTION1;
        myPlayerObject = myTeam.getActivePlayer();
        myEnemyObject = myEnemyTeam.getActivePlayer();
        
    }

    @Override
    public void pause () {
        // myTeams.clear();
    }

    @Override
    public void resume () {
        makeTeams();
        initialize();
        myMessages.add(myEnemyObject.getStartFightingMessage(false));
        myEnemyObject.initializeStats();
        myMessages.add(myPlayerObject.getStartFightingMessage(true));
        myPlayerObject.initializeStats();
        configureInputHandling();
        playModeEntranceSound("BattleStartSound");
    }

    /**
     * Allows the battle to receive input from the keyboard.
     * Controls include Attack, Defend, Heal, and Charge, set as constants.
     */
    public void configureInputHandling () {
        // use input api for key handling. notice how you can only invoke
        // methods w/t parameters...
        int left = KeyEvent.VK_LEFT;
        int right = KeyEvent.VK_RIGHT;
        int up = KeyEvent.VK_UP;
        int down = KeyEvent.VK_DOWN;
        int select = KeyEvent.VK_ENTER;
        try {
            GamePane.keyboardController.setControl(left, KeyboardController.RELEASED, this,
                    "triggerLeftEvent");
            GamePane.keyboardController.setControl(right, KeyboardController.RELEASED, this,
                    "triggerRightEvent");
            GamePane.keyboardController.setControl(up, KeyboardController.RELEASED, this,
                    "triggerUpEvent");
            GamePane.keyboardController.setControl(down, KeyboardController.RELEASED, this,
                    "triggerDownEvent");
            GamePane.keyboardController.setControl(select, KeyboardController.RELEASED, this,
                    "triggerSelectEvent");
        }
        catch (NoSuchMethodException e) {
            System.out.println("A method was called that does not exist!");
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void makeTeams () {
        // adding player
        List<BattleObject> myBattleObjects = new ArrayList<BattleObject>();
        myBattleObjects.addAll((List<BattleObject>) getGameObjectsByID(myInvolvedIDs.get(0)));
        myTeam = new Team(myBattleObjects);

        // adding enemy
        List<BattleObject> enemyBattleObjects = new ArrayList<BattleObject>();
        enemyBattleObjects.addAll((List<BattleObject>) getGameObjectsByID(myInvolvedIDs.get(1)));
        myEnemyTeam = new Team(enemyBattleObjects);
    }

    @Override
    public void update () {
        if (isBattleOver()) {
            endBattle();
        }
        if (!myPlayerObject.isAlive()) {
            myMessages.add(myPlayerObject.getDeathMessage());
            List<Integer> list = new ArrayList<Integer>();
            list.add(new Integer(myPlayerObject.getID()));
            flagCondition(myPlayerObject.getConditionFlag(), list);
            myTeam.switchPlayer(myTeam.nextPlayer());
            myPlayerObject = myTeam.getActivePlayer();
            myMessages.add(myPlayerObject.getStartFightingMessage(true));
            myPlayerObject.initializeStats();
        }
        else {
            myPlayerObject.update();
        }
        if (!myEnemyObject.isAlive()) {
            myMessages.add(myEnemyObject.getDeathMessage());
            List<Integer> list = new ArrayList<Integer>();
            list.add(new Integer(myEnemyObject.getID()));
            flagCondition(myEnemyObject.getConditionFlag(), list);
            myEnemyTeam.switchPlayer(myEnemyTeam.nextPlayer());
            myEnemyObject = myEnemyTeam.getActivePlayer();
            myMessages.add(myEnemyObject.getStartFightingMessage(false));
            myEnemyObject.initializeStats();
        }
    }

    @Override
    public void paint (Graphics g) {
        Dimension myWindow = getGameManager().getPaneDimension();
        int height = myWindow.height;
        int width = myWindow.width;

        myEnemyObject.paintStats(g, 0, 0, width / 2, height / HEIGHT_SCALAR);
        myEnemyObject.paintBattleObject(g, width / 2, 0, width / 2, height / HEIGHT_SCALAR);
        myPlayerObject.paintBattleObject(g, 0, height / HEIGHT_SCALAR, width / 2, height /
                HEIGHT_SCALAR);
        myPlayerObject.paintStats(g, width / 2, height / HEIGHT_SCALAR, width / 2, height /
                HEIGHT_SCALAR);
        paintMenu(g);
    }

    /**
     * Draws the battle messages that display at the bottom of the screen, showing player and
     * enemy actions and changes.
     * 
     * @param g Graphics
     */
    public void paintMenu (Graphics g) {
        // paint the message box/battle option menu
        Dimension myWindow = getGameManager().getPaneDimension();
        int height = myWindow.height;
        int width = myWindow.width;

        // move this to XML
        File imageFile = new File("src/vooga/turnbased/resources/image/GUI/Message_Sign.png");
        Image box = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        g.drawImage(box, 0, 0, width, height, null);

        // draw the messages
        int counter = 0;
        if (myMessages.size() > MESSAGE_NUM) {
            counter = myMessages.size() - MESSAGE_NUM;
        }
        Graphics2D g2d = (Graphics2D) g;
        int fontSize = calculateFontSize(width, height);
        Font font = new Font(myMENU_FONT, Font.PLAIN, fontSize);
        FontEffect myFontEffect = new FontEffect(g, font);
        for (int i = 0; counter + i < myMessages.size(); i++) {
            String currentMessage = myMessages.get(counter + i);
            double horizontalShift = (double) ((width / TEXT_SCALAR) * 3);
            double verticalShift = (double) (height / TEXT_SCALAR * 4.5);
            double spacingBetweenLines = (double) 1.2 * fontSize * i;
            Point paintPosition = new Point((int) horizontalShift, (int) ((2 * height /
                    HEIGHT_SCALAR + verticalShift + spacingBetweenLines)));
            myFontEffect.shodowEffect(currentMessage, Color.blue, paintPosition);
        }
        g2d.drawImage(box, width / 2, 0, width / 2, height, null);
        drawOptions(g, width / 2, 2 * height / HEIGHT_SCALAR, width / 2, height / HEIGHT_SCALAR);
    }

    /**
     * Draw options in a box for the player to choose.
     * 
     * @param g the Graphics context
     * @param x the x coordinate of the origin of this box
     * @param y the y coordinate of the origin of this box
     * @param width the width of this box
     * @param height the height of this box
     */
    // I would say a point and a dimension, or a rectangle would be more intuitive and readable?
    public void drawOptions (Graphics g, int x, int y, int width, int height) {
        // format positions based on width and height of the box...maybe?
        int fontSize = calculateFontSize(width, height) * width / height;
        Font font = new Font(myMENU_FONT, Font.PLAIN, fontSize);
        FontEffect fontEffect = new FontEffect(g, font);
        String[] options = myPlayerObject.getOptions();
        // position determines where the strings are painted
        Point position = null;
        Color mainColor = Color.GRAY;
        Color outlineColor = Color.BLACK;
        int leftShift = (int) (width * SHIFT_LEFT_SCALAR);
        int rightShift = (int) (width * SHIFT_RIGHT_SCALAR);
        int topShift = (int) (height * SHIFT_TOP_SCALAR);
        int bottomShift = (int) (height * SHIFT_BOTTOM_SCALAR);

        for (int i = 0; i < options.length; i++) {
            String option = options[i];
            if (i == 0) {
                position = new Point(x + leftShift, y + topShift);
            }
            else if (i == 1) {
                position = new Point(x + rightShift, y + topShift);
            }
            else if (i == 2) {
                position = new Point(x + leftShift, y + bottomShift);
            }
            else if (i == 3) {
                position = new Point(x + rightShift, y + bottomShift);
            }
            try {
                fontEffect.outlineEffect(option, mainColor, outlineColor, position);
            }
            catch (NullPointerException e) {
                System.err.println("option not recognized");
            }
        }
        drawArrow(g, x, y, leftShift, rightShift, topShift, bottomShift);
    }

    private void drawArrow (Graphics g, int x, int y, int leftShift, int rightShift, int topShift,
            int bottomShift) {
        File imageFile = new File("src/vooga/turnbased/resources/image/GUI/Arrow.png");
        Image arrow = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        switch (mySelection) {
            case OPTION1:
                g.drawImage(arrow, x + leftShift - arrow.getWidth(null),
                        (int) (y + topShift - arrow.getHeight(null) * ADJUST_ARROW_SCALAR),
                        arrow.getWidth(null), arrow.getHeight(null), null);
                break;
            case OPTION2:
                g.drawImage(arrow, x + rightShift - arrow.getWidth(null),
                        (int) (y + topShift - arrow.getHeight(null) * ADJUST_ARROW_SCALAR),
                        arrow.getWidth(null), arrow.getHeight(null), null);
                break;
            case OPTION3:
                g.drawImage(arrow, x + leftShift - arrow.getWidth(null),
                        (int) (y + bottomShift - arrow.getHeight(null) * ADJUST_ARROW_SCALAR),
                        arrow.getWidth(null), arrow.getHeight(null), null);
                break;
            case OPTION4:
                g.drawImage(arrow, x + rightShift - arrow.getWidth(null),
                        (int) (y + bottomShift - arrow.getHeight(null) * ADJUST_ARROW_SCALAR),
                        arrow.getWidth(null), arrow.getHeight(null), null);
                break;
            default:
                break;
        }
    }

    private int calculateFontSize (int width, int height) {
        // uses diagonal length of window; kind of faulty sometimes but usually works
        return (int) (Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2)) / TEXT_SCALAR);
    }

    /**
     * End a battle and report the event (whether player is dead) to GameManager.
     */
    private void endBattle () {
        setModeIsOver();
        System.out.println("End battle!");
        if (!myPlayerObject.isAlive()) {
            flagCondition(myPlayerObject.getConditionFlag(), new ArrayList<Integer>());
        }
        getGameManager().clearSprite(myLoserSpriteID);
    }

    private boolean isBattleOver () {
        boolean teamDead = false;
        if (!myTeam.stillAlive()) {
            myLoserSpriteID = myPlayerObject.getID();
            teamDead = true;
        }
        if (!myEnemyTeam.stillAlive()) {
            myLoserSpriteID = myEnemyObject.getID();
            teamDead = true;
        }
        return teamDead;
    }

    private void continueBattle () {
        if (!isBattleOver()) {
            generateEnemyMove();
        }
    }

    private void generateEnemyMove () {
        String message = myEnemyObject.doRandomOption(myPlayerObject);
        myMessages.add(message);
    }

    /**
     * select the option that is on the left of the current option
     */
    public void triggerLeftEvent () {
        if (mySelection == OPTION2) {
            mySelection = OPTION1;
        }
        else if (mySelection == OPTION4) {
            mySelection = OPTION3;
        }
    }

    /**
     * select the option that is on the right of the current option
     */
    public void triggerRightEvent () {
        if (mySelection == OPTION1) {
            mySelection = OPTION2;
        }
        else if (mySelection == OPTION3) {
            mySelection = OPTION4;
        }
    }

    /**
     * select the option that is above the current option
     */
    public void triggerUpEvent () {
        if (mySelection == OPTION3) {
            mySelection = OPTION1;
        }
        else if (mySelection == OPTION4) {
            mySelection = OPTION2;
        }
    }

    /**
     * select the option that is below the current option
     */
    public void triggerDownEvent () {
        if (mySelection == OPTION1) {
            mySelection = OPTION3;
        }
        else if (mySelection == OPTION2) {
            mySelection = OPTION4;
        }
    }

    /**
     * trigger the selected event
     * @throws Exception When mySelection is greater than 4, selecting an option that
     * is not supported
     */
    public void triggerSelectEvent () throws Exception {
        if (mySelection > 4) {
            myMessages.add("Option does not exist");
            System.out.println("Error: MySelection on option that does not exist");
            throw new Exception();
        }
        switch (mySelection) {
            case OPTION1:
                triggerOption(1);
                break;
            case OPTION2:
                triggerOption(2);
                break;
            case OPTION3:
                triggerOption(3);
                break;
            case OPTION4:
                triggerOption(4);
                break;
            default:
                break;
        }
    }

    private void triggerOption (int MenuOptionSelected) {
        String message = myPlayerObject.doOption(MenuOptionSelected, myEnemyObject);
        
        myMessages.add(message);
        continueBattle();
        
    }

    private class Team {
        private final List<BattleObject> myBattleObjects;
        private BattleObject myActivePlayer;

        public Team (List<BattleObject> battleObjs) {
            myBattleObjects = battleObjs;
            if (myBattleObjects.size() > 0) {
                myActivePlayer = myBattleObjects.get(0);
            }
        }

        public boolean stillAlive () {
            for (BattleObject teamMember : myBattleObjects) {
                if (teamMember.isAlive()) { return true; }
            }
            return false;
        }

        public BattleObject getActivePlayer () {
            return myActivePlayer;
        }

        public void switchPlayer (BattleObject nextPlayer) {
            myActivePlayer = nextPlayer;
        }

        public BattleObject nextPlayer () {
            myBattleObjects.add(myBattleObjects.remove(0));
            return myBattleObjects.get(0);
        }
    }


    @Override
    public void processMouseInput (int mousePressed, Point mousePosition, int mouseButton) {
    }

}
