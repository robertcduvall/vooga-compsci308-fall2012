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
    private BattleState myState;
    private int myTurnCount;
    private int myTeamStartRandomizer;
    private List<Integer> myInvolvedIDs;
    private List<String> myMessages;
    private OptionSelect mySelection;

    private final int MESSAGE_NUM = 4;
    private final double TEXT_SCALAR = 40;
    private int myLooserSpriteID;
    private final String OPTION1 = "ATTACK";
    private final String OPTION2 = "DEFEND";
    private final String OPTION3 = "CHARGE";
    private final String OPTION4 = "HEAL";

    /**
     * Constructor for a Battle.
     * 
     * @param id The ID of the mode
     * @param gameManager The parent GameManager that is creating this battle.
     *        Will be
     *        alerted when battle ends.
     * @param modeObjectType The object type this mode uses, i.e.
     *        BattleObject.java
     * @param involvedIDs A list of IDs of the sprites involved in this battle.
     */

    // need to pass ids of battle participants upon battle creation
    public BattleMode (int id, GameManager gameManager, Class<BattleObject> modeObjectType,
                       List<Integer> involvedIDs) {
        super(id, gameManager, modeObjectType);
        myInvolvedIDs = involvedIDs;
        myMessages = new ArrayList<String>();
    }

    @Override
    public void pause () {
        // myTeams.clear();
    }

    @Override
    public void resume () {
        makeTeams();
        initialize();
        myMessages.add(myEnemyObject.getName() + " encountered!");
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
            myMessages.add(myPlayerObject.getName() + " fainted");
            myTeam.switchPlayer(myTeam.nextPlayer());
            myPlayerObject = myTeam.getActivePlayer();
            myMessages.add(myPlayerObject.getName() + " sent out");
        }
        else {
            myPlayerObject.update();
        }
        if (!myEnemyObject.isAlive()) {
            myMessages.add(myEnemyObject.getName() + " fainted");
            myEnemyTeam.switchPlayer(myEnemyTeam.nextPlayer());
            myEnemyObject = myEnemyTeam.getActivePlayer();
            myMessages.add(myEnemyObject.getName() + " appeared");
        }

        // TODO: figure out how this should work. Right now we just give it the
        // previous team
        // TODO: Take into account animating, requesting user input for player
        // team, etc.
        // nextTeam().makeMove(myTeams.get(myTurnCount - 1 % myTeams.size()));
        // }
    }

    // this needs to be generalized
    @Override
    public void paint (Graphics g) {
        Dimension myWindow = getGameManager().getPaneDimension();
        int height = myWindow.height;
        int width = myWindow.width;

        myEnemyObject.paintStats(g, 0, 0, width / 2, height / 3);
        myEnemyObject.paintBattleObject(g, width / 2, 0, width / 2, height / 3);
        myPlayerObject.paintBattleObject(g, 0, height / 3, width / 2, height / 3);
        myPlayerObject.paintStats(g, width / 2, height / 3, width / 2, height / 3);
        paintMenu(g);
    }

    // someone please fix this...
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
        Font font = new Font("Sans_Serif", Font.PLAIN, fontSize);
        FontEffect myFontEffect = new FontEffect(g, font);
        for (int i = 0; counter + i < myMessages.size(); i++) {
            String currentMessage = myMessages.get(counter + i);
            double horizontalShift = (double) ((width / TEXT_SCALAR) * 3);
            double verticalShift = (double) (height / TEXT_SCALAR * 4.5);
            double spacingBetweenLines = (double) (1.2) * (fontSize) * i;
            Point paintPosition =
                    new Point((int) horizontalShift,
                              (int) ((2 * height / 3 + verticalShift + spacingBetweenLines)));
            myFontEffect.shodowEffect(currentMessage, Color.blue, paintPosition);
        }
        g.drawImage(box, width / 2, 0, width / 2, height, null);
        drawOptions(g, width / 2, 2 * height / 3, width / 2, height / 3);
    }

    /**
     * draw options in a box for the player to choose
     * @param g the Graphics context
     * @param x the x coordinate of the origin of this box
     * @param y the y coordinate of the origin of this box
     * @param width the width of this box
     * @param height the height of this box
     */
    // I would say a point and a dimension, or a rectangle would be more intuitive and readable?
    public void drawOptions (Graphics g, int x, int y, int width, int height) {
        // format positions based on width and height of the box...maybe?
        Font font = new Font("Sans_Serif", Font.PLAIN, 25);
        FontEffect fontEffect = new FontEffect(g, font);
        String[] options = { OPTION1, OPTION2, OPTION3, OPTION4 };
        // position determines where the strings are painted (need to get rid
        // of the magic numbers)
        Point position = null;
        Color mainColor = Color.GRAY;
        Color outlineColor = Color.BLACK;
        for (int i = 0; i < options.length; i++) {
            String option = options[i];
            if (i == 0) {
                position = new Point(x + 60, y + 80);
            }
            else if (i == 1) {
                position = new Point(x + 220, y + 80);
            }
            else if (i == 2) {
                position = new Point(x + 60, y + 140);
            }
            else if (i == 3) {
                position = new Point(x + 220, y + 140);
            }
            try {
                fontEffect.outlineEffect(option, mainColor, outlineColor, position);
            }
            catch (NullPointerException e) {
                System.err.println("option not recognized");
                e.printStackTrace();
            }
        }
        File imageFile = new File("src/vooga/turnbased/resources/image/GUI/Arrow.png");
        Image arrow = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        if (mySelection == OptionSelect.OPTION1) {
            g.drawImage(arrow, x + 40, y + 60, 20, 20, null);
        }
        else if (mySelection == OptionSelect.OPTION2) {
            g.drawImage(arrow, x + 200, y + 60, 20, 20, null);
        }
        else if (mySelection == OptionSelect.OPTION3) {
            g.drawImage(arrow, x + 40, y + 120, 20, 20, null);
        }
        else if (mySelection == OptionSelect.OPTION4) {
            g.drawImage(arrow, x + 200, y + 120, 20, 20, null);
        }
    }

    private int calculateFontSize (int width, int height) {
        // current hypotenuse of regular window size is ~965, with font size 25
        // 965/25 = 37.4
        return (int) (Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2)) / TEXT_SCALAR);
    }

    /**
     * Initializes a battle with the current lists of BattleObjects
     */
    @Override
    public void initialize () {
        myState = BattleState.WAITING_FOR_MOVE;
        mySelection = OptionSelect.OPTION1;
        myTurnCount = 0;
        // Initialize myTeamStartRandomizer to 0 to number of teams (exclusive)
        // the seed value is going to determine which team starts where 0 =
        // "team 1"
        Random generator = new Random();
        myTeamStartRandomizer = generator.nextInt(1);
        myPlayerObject = myTeam.getActivePlayer();
        myEnemyObject = myEnemyTeam.getActivePlayer();
    }

    /**
     * end a battle and report the event (whether player is dead) to GameManager
     */
    private void endBattle () {
        System.out.println("End battle!");
        List<Integer> battleLooserIDs = new ArrayList<Integer>();
        battleLooserIDs.add(myLooserSpriteID);
        if (!myPlayerObject.isAlive()) {
            getGameManager().flagEvent("GAME_LOST", new ArrayList<Integer>());
        }
        else {
            getGameManager().flagEvent("BATTLE_OVER", battleLooserIDs);
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

    public void triggerOption1Event () {
        // for now, player attacks enemy player
        // by difference in defense
        myMessages.add(myPlayerObject.getName() + " used " + OPTION1);
        myPlayerObject.attackEnemy(myEnemyObject);
        // check if enemy/opposing team is dead
        if (!isBattleOver()) {
            generateEnemyMove();
        }
    }

    public void triggerOption4Event () {
        // for now, increases player health by 3
        myMessages.add(myPlayerObject.getName() + " used HEAL");
        myPlayerObject.changeStat("health", myPlayerObject.getStat("health").intValue() + 3);
        if (myPlayerObject.getStat("health").intValue() > myPlayerObject.getStat("maxHealth")
                .intValue()) {
            myPlayerObject.changeStat("health", myPlayerObject.getStat("maxHealth").intValue());
        }
        if (!isBattleOver()) {
            generateEnemyMove();
        }
    }

    public void triggerOption2Event () {
        // for now, increases player defense by one; other team still attacks
        myMessages.add(myPlayerObject.getName() + " used DEFEND");
        myPlayerObject.changeStat("defense", myPlayerObject.getStat("defense").intValue() + 1);
        if (!isBattleOver()) {
            generateEnemyMove();
        }
    }

    public void triggerOption3Event () {
        // for now, increases player attack by one; other team still attacks
        // System.out.println("You use CHARGE");
        myMessages.add(myPlayerObject.getName() + " used CHARGE");
        myPlayerObject.changeStat("attack", myPlayerObject.getStat("attack").intValue() + 1);
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
        if (random >= 0 && random < .5) {
            // attack
            myEnemyObject.attackEnemy(myPlayerObject);
            myMessages.add(myEnemyObject.getName() + " used ATTACK");
            // System.out.println("Your enemy uses ATTACK");
        }
        if (random >= .5 && random < .7) {
            // defend
            myEnemyObject.changeStat("defense", myEnemyObject.getStat("defense").intValue() + 1);
            myMessages.add(myEnemyObject.getName() + " used DEFEND");
            // System.out.println("Your enemy uses DEFEND");
        }
        if (random >= .7 && random < .9) {
            // charge
            myEnemyObject.changeStat("attack", myEnemyObject.getStat("attack").intValue() + 1);
            myMessages.add(myEnemyObject.getName() + " used CHARGE");
            // System.out.println("Your enemy uses CHARGE");
        }
        if (random >= .9 && random < 1) {
            // increase health
            myEnemyObject.changeStat("health", myEnemyObject.getStat("health").intValue() + 3);
            if (myEnemyObject.getStat("health").intValue() > myEnemyObject.getStat("maxHealth")
                    .intValue()) {
                myEnemyObject.changeStat("health", myEnemyObject.getStat("maxHealth").intValue());
            }
            myMessages.add(myEnemyObject.getName() + " used HEAL");
            // System.out.println("Your enemy uses HEALTH INCREASE");
        }
        // displayBattleStats();
    }

    // for debugging etc
    private void displayBattleStats () {
        System.out.println("My health: " + myPlayerObject.getStat("health"));
        System.out.println("My defense: " + myPlayerObject.getStat("defense"));
        System.out.println("Enemy health: " + myEnemyObject.getStat("health"));
        System.out.println("Enemy defense: " + myEnemyObject.getStat("defense"));
    }

    private class Team {
        private final List<BattleObject> myBattleObjects;
        private BattleObject myActivePlayer;

        public Team (List<BattleObject> battleObjs) {
            myBattleObjects = battleObjs;
            if (myBattleObjects.size() > 0) myActivePlayer = myBattleObjects.get(0);
        }

        public boolean stillAlive () {
            for (BattleObject teamMember : myBattleObjects) {
                if (teamMember.isAlive()) { return true; }
            }
            return false;
        }

        public List<BattleObject> getBattleObjects () {
            return myBattleObjects;
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
     * battle states in a battle
     * waiting for player to decide, display message, and when animation takes
     * place
     */
    private enum BattleState {
        WAITING_FOR_MOVE, MESSAGE, ANIMATING
    }

    /**
     * Options to select
     */
    private enum OptionSelect {
        OPTION1, OPTION2, OPTION3, OPTION4
    }

    public void processMouseInput (int mousePressed, Point mousePosition, int mouseButton) {
    }
}
