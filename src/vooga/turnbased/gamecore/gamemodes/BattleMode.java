package vooga.turnbased.gamecore.gamemodes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import util.graphicprocessing.FontEffect;
import util.input.core.KeyboardController;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.InputAPI;
import vooga.turnbased.sprites.Sprite;


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
    private int myLooserSpriteID;
    private List<Integer> myInvolvedIDs;
    private List<String> myMessages;
    private OptionSelect mySelection;

    private final int MESSAGE_NUM = 4;
    private final int HEIGHT_SCALAR = 3;
    private final double TEXT_SCALAR = 40;

    private final double OPTION1_LOWER_BOUND = .0;
    private final double OPTION1_UPPER_BOUND = .5;
    private final double OPTION2_LOWER_BOUND = .5;
    private final double OPTION2_UPPER_BOUND = .7;
    private final double OPTION3_LOWER_BOUND = .7;
    private final double OPTION3_UPPER_BOUND = .9;
    private final double OPTION4_LOWER_BOUND = .9;
    private final double OPTION4_UPPER_BOUND = 1;

    private final double SHIFT_LEFT_SCALAR = .15;
    private final double SHIFT_RIGHT_SCALAR = .60;
    private final double SHIFT_TOP_SCALAR = .45;
    private final double SHIFT_BOTTOM_SCALAR = .75;
    private final double ADJUST_ARROW_SCALAR = .80;

    private final double INCREASE_ATTACK_VAL = 1;
    private final double INCREASE_DEFENSE_VAL = 1;
    private final double INCREASE_HEALTH_VAL = 3;

    private final String USED = " used ";
    private final String MENU_FONT = "Sans_Serif";
    private final String HEALTH_STAT = "health";
    private final String ATTACK_STAT = "attack";
    private final String DEFENSE_STAT = "defense";
    private final String MAX_HEALTH_STAT = "maxHealth";
    private final String OPTION1 = "ATTACK";
    private final String OPTION2 = "DEFEND";
    private final String OPTION3 = "CHARGE";
    private final String OPTION4 = "HEAL";

    /**
     * Constructor for a Battle.
     * 
     * @param gameManager The parent GameManager that is creating this battle.
     *        Will be
     *        alerted when battle ends.
     * @param modeObjectType The object type this mode uses, i.e.
     *        BattleObject.java
     * @param involvedIDs A list of IDs of the sprites involved in this battle.
     */

    // need to pass ids of battle participants upon battle creation
    public BattleMode (GameManager gameManager, Class<BattleObject> modeObjectType,
            List<Integer> involvedIDs) {
        super(gameManager, modeObjectType, involvedIDs);
        myInvolvedIDs = involvedIDs;
        myMessages = new ArrayList<String>();
        resume();
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
        configureInputHandling();
        // getGameManager().handleEvent(GameManager.GameEvent.BATTLE_OVER, new
        // ArrayList<Integer>());
    }

    /**
     * Allows the battle to receive input from the keyboard.
     * Controls include Attack, Defend, Heal, and Charge, set as constants.
     */
    public void configureInputHandling () {
        // use input api for key handling. notice how you can only invoke
        // methods w/t parameters...
        int attack = KeyEvent.VK_A;
        int left = KeyEvent.VK_LEFT;
        int right = KeyEvent.VK_RIGHT;
        int up = KeyEvent.VK_UP;
        int down = KeyEvent.VK_DOWN;
        int select = KeyEvent.VK_ENTER;
        try {
            GamePane.keyboardController.setControl(attack, KeyboardController.RELEASED, this,
                    "triggerOption1Event");
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
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeTeams () {
        // adding player
        List<BattleObject> myBattleObjects = new ArrayList<BattleObject>();
        Sprite s1 = getGameManager().findSpriteWithID(myInvolvedIDs.get(0));
        for (BattleObject bo : s1.getObject(BattleObject.class)) {
            myBattleObjects.add(bo);
        }
        myTeam = new Team(myBattleObjects);

        // adding enemy
        List<BattleObject> enemyBattleObjects = new ArrayList<BattleObject>();
        Sprite s2 = getGameManager().findSpriteWithID(myInvolvedIDs.get(1));
        for (BattleObject bo : s2.getObject(BattleObject.class)) {
            enemyBattleObjects.add(bo);
        }
        myEnemyTeam = new Team(enemyBattleObjects);
    }

    @Override
    public void update () {
        if (isBattleOver()) {
            endBattle();
        }
        if (!myPlayerObject.isAlive()) {
            myMessages.add(myPlayerObject.getDeathMessage());
            myTeam.switchPlayer(myTeam.nextPlayer());
            myPlayerObject = myTeam.getActivePlayer();
            myMessages.add(myPlayerObject.getStartFightingMessage(true));
        }
        else {
            myPlayerObject.update();
        }
        if (!myEnemyObject.isAlive()) {
            myMessages.add(myEnemyObject.getDeathMessage());
            myEnemyTeam.switchPlayer(myEnemyTeam.nextPlayer());
            myEnemyObject = myEnemyTeam.getActivePlayer();
            myMessages.add(myEnemyObject.getStartFightingMessage(false));
        }
    }

    // this needs to be generalized
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

    // someone please fix this...
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
        Font font = new Font(MENU_FONT, Font.PLAIN, fontSize);
        FontEffect myFontEffect = new FontEffect(g, font);
        for (int i = 0; counter + i < myMessages.size(); i++) {
            String currentMessage = myMessages.get(counter + i);
            double horizontalShift = (double) ((width / TEXT_SCALAR) * 3);
            double verticalShift = (double) (height / TEXT_SCALAR * 4.5);
            double spacingBetweenLines = (double) 1.2 * fontSize * i;
            Point paintPosition = new Point((int) horizontalShift, (int) ((2 * height
                    / HEIGHT_SCALAR + verticalShift + spacingBetweenLines)));
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
        Font font = new Font(MENU_FONT, Font.PLAIN, fontSize);
        FontEffect fontEffect = new FontEffect(g, font);
        String[] options = {OPTION1, OPTION2, OPTION3, OPTION4};
        // position determines where the strings are painted (need to get rid
        // of the magic numbers)
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
                // e.printStackTrace();
            }
        }
        File imageFile = new File("src/vooga/turnbased/resources/image/GUI/Arrow.png");
        Image arrow = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        if (mySelection == OptionSelect.OPTION1) {
            g.drawImage(arrow, x + leftShift - arrow.getWidth(null),
                    (int)(y + topShift - arrow.getHeight(null) * ADJUST_ARROW_SCALAR),
                    arrow.getWidth(null), arrow.getHeight(null), null);
        }
        else if (mySelection == OptionSelect.OPTION2) {
            g.drawImage(arrow, x + rightShift - arrow.getWidth(null),
                    (int)(y + topShift - arrow.getHeight(null) * ADJUST_ARROW_SCALAR), 
                    arrow.getWidth(null), arrow.getHeight(null), null);
        }
        else if (mySelection == OptionSelect.OPTION3) {
            g.drawImage(arrow, x + leftShift - arrow.getWidth(null),
                    (int)(y + bottomShift - arrow.getHeight(null) * ADJUST_ARROW_SCALAR), 
                    arrow.getWidth(null), arrow.getHeight(null), null);
        }
        else if (mySelection == OptionSelect.OPTION4) {
            g.drawImage(arrow, x + rightShift - arrow.getWidth(null),
                    (int)(y + bottomShift - arrow.getHeight(null) * ADJUST_ARROW_SCALAR), 
                    arrow.getWidth(null), arrow.getHeight(null), null);
        }
    }

    private int calculateFontSize (int width, int height) {
        // uses diagonal length of window; kind of faulty sometimes but usually works
        return (int) (Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2)) / TEXT_SCALAR);
    }

    /**
     * Initializes a battle with the current lists of BattleObjects
     */
    @Override
    public void initialize () {
        mySelection = OptionSelect.OPTION1;
        myPlayerObject = myTeam.getActivePlayer();
        myEnemyObject = myEnemyTeam.getActivePlayer();
    }

    /**
     * end a battle and report the event (whether player is dead) to GameManager
     */
    private void endBattle () {
        setModeIsOver();
        System.out.println("End battle!");
        List<Integer> battleLooserIDs = new ArrayList<Integer>();
        battleLooserIDs.add(myLooserSpriteID);
        if (!myPlayerObject.isAlive()) {
            flagCondition("gamelost", new ArrayList<Integer>());
        }
    }

    private boolean isBattleOver () {
        boolean teamDead = false;
        if (!myTeam.stillAlive()) {
            Sprite loserSprite = getGameManager().findSpriteWithID(myPlayerObject.getID());
            myLooserSpriteID = loserSprite.getID();
            loserSprite.clear();
            getGameManager().deleteSprite(loserSprite.getID());
            teamDead = true;
        }
        if (!myEnemyTeam.stillAlive()) {
            Sprite loserSprite = getGameManager().findSpriteWithID(myEnemyObject.getID());
            myLooserSpriteID = loserSprite.getID();
            loserSprite.clear();
            getGameManager().deleteSprite(loserSprite.getID());
            teamDead = true;
        }
        return teamDead;
    }

    /**
     * Triggers the event associated with the player's first option.
     */
    public void triggerOption1Event () {
        // for now, player attacks enemy player
        // by difference in defense
        myMessages.add(myPlayerObject.getName() + USED + OPTION1);
        myPlayerObject.attackEnemy(myEnemyObject);
        // check if enemy/opposing team is dead
        if (!isBattleOver()) {
            generateEnemyMove();
        }
    }

    /**
     * Triggers the event associated with the player's second option.
     */
    public void triggerOption2Event () {
        // for now, increases player defense by one; other team still attacks
        myMessages.add(myPlayerObject.getName() + USED + OPTION2);
        myPlayerObject.changeStat(DEFENSE_STAT, myPlayerObject.getStat(DEFENSE_STAT).intValue() +
                INCREASE_DEFENSE_VAL);
        if (!isBattleOver()) {
            generateEnemyMove();
        }
    }

    /**
     * Triggers the event associated with the player's third option.
     */
    public void triggerOption3Event () {
        // for now, increases player attack by one; other team still attacks
        myMessages.add(myPlayerObject.getName() + USED + OPTION3);
        myPlayerObject.changeStat(ATTACK_STAT, myPlayerObject.getStat(ATTACK_STAT).intValue() +
                INCREASE_ATTACK_VAL);
        if (!isBattleOver()) {
            generateEnemyMove();
        }
    }

    /**
     * Triggers the event associated with the player's fourth option.
     */
    public void triggerOption4Event () {
        // for now, increases player health by 3
        myMessages.add(myPlayerObject.getName() + USED + OPTION4);
        myPlayerObject.changeStat(HEALTH_STAT, myPlayerObject.getStat(HEALTH_STAT).intValue() +
                INCREASE_HEALTH_VAL);
        if (myPlayerObject.getStat("health").intValue() > myPlayerObject.getStat(MAX_HEALTH_STAT)
                .intValue()) {
            myPlayerObject.changeStat("health", myPlayerObject.getStat(MAX_HEALTH_STAT).intValue());
        }
        if (!isBattleOver()) {
            generateEnemyMove();
        }
    }

    /**
     * select the option that is on the left of the current option
     */
    public void triggerLeftEvent () {
        if (mySelection == OptionSelect.OPTION2) {
            mySelection = OptionSelect.OPTION1;
        }
        else if (mySelection == OptionSelect.OPTION4) {
            mySelection = OptionSelect.OPTION3;
        }
    }

    /**
     * select the option that is on the right of the current option
     */
    public void triggerRightEvent () {
        if (mySelection == OptionSelect.OPTION1) {
            mySelection = OptionSelect.OPTION2;
        }
        else if (mySelection == OptionSelect.OPTION3) {
            mySelection = OptionSelect.OPTION4;
        }
    }

    /**
     * select the option that is above the current option
     */
    public void triggerUpEvent () {
        if (mySelection == OptionSelect.OPTION3) {
            mySelection = OptionSelect.OPTION1;
        }
        else if (mySelection == OptionSelect.OPTION4) {
            mySelection = OptionSelect.OPTION2;
        }
    }

    /**
     * select the option that is below the current option
     */
    public void triggerDownEvent () {
        if (mySelection == OptionSelect.OPTION1) {
            mySelection = OptionSelect.OPTION3;
        }
        else if (mySelection == OptionSelect.OPTION2) {
            mySelection = OptionSelect.OPTION4;
        }
    }

    /**
     * trigger the selected event
     */
    public void triggerSelectEvent () {
        if (mySelection == OptionSelect.OPTION1) {
            triggerOption1Event();
        }
        else if (mySelection == OptionSelect.OPTION2) {
            triggerOption2Event();
        }
        else if (mySelection == OptionSelect.OPTION3) {
            triggerOption3Event();
        }
        else if (mySelection == OptionSelect.OPTION4) {
            triggerOption4Event();
        }
    }

    private void generateEnemyMove () {
        double random = Math.random();
        if (random >= OPTION1_LOWER_BOUND && random < OPTION1_UPPER_BOUND) {
            // attack
            myEnemyObject.attackEnemy(myPlayerObject);
            myMessages.add(myEnemyObject.getName() + USED + OPTION1);
        }
        if (random >= OPTION2_LOWER_BOUND && random < OPTION2_UPPER_BOUND) {
            // defend
            myEnemyObject.changeStat(DEFENSE_STAT, myEnemyObject.getStat(DEFENSE_STAT).intValue() +
                    INCREASE_DEFENSE_VAL);
            myMessages.add(myEnemyObject.getName() + USED + OPTION2);
        }
        if (random >= OPTION3_LOWER_BOUND && random < OPTION3_UPPER_BOUND) {
            // charge
            myEnemyObject.changeStat(ATTACK_STAT, myEnemyObject.getStat(ATTACK_STAT).intValue() +
                    INCREASE_ATTACK_VAL);
            myMessages.add(myEnemyObject.getName() + USED + OPTION3);
        }
        if (random >= OPTION4_LOWER_BOUND && random < OPTION4_UPPER_BOUND) {
            // increase health
            myEnemyObject.changeStat(HEALTH_STAT, myEnemyObject.getStat(HEALTH_STAT).intValue() + 
                    INCREASE_HEALTH_VAL);
            if (myEnemyObject.getStat(HEALTH_STAT).intValue() > myEnemyObject.getStat(
                    MAX_HEALTH_STAT).intValue()) {
                myEnemyObject.changeStat(HEALTH_STAT, myEnemyObject.getStat(MAX_HEALTH_STAT)
                        .intValue());
            }
            myMessages.add(myEnemyObject.getName() + USED + OPTION4);
        }
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

    /**
     * Options to select
     */
    private enum OptionSelect {
        OPTION1, OPTION2, OPTION3, OPTION4
    }

    @Override
    public void processMouseInput (int mousePressed, Point mousePosition, int mouseButton) {
    }

}