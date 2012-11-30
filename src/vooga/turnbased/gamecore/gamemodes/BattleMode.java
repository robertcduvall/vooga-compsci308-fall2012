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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import util.input.core.KeyboardController;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.InputAPI;
import vooga.turnbased.sprites.Sprite;


/**
 * This is gamemode that will run a battle given two lists of BattleObjects that
 * will fight each other.
 * 
 * @author David Howdyshell, Michael Elgart, Kevin Gao, Jenni Mercado, Tony
 * 
 */
public class BattleMode extends GameMode implements InputAPI {
    private List<Team> myTeams;
    private BattleObject myPlayerObject;
    private BattleObject myEnemy;
    private BattleState myState;
    private int myTurnCount;
    private int myTeamStartRandomizer;
    private List<Integer> myInvolvedIDs;
    private List<String> myMessages;
    private int mySelection = 0;

    private final int MESSAGE_NUM = 4;
    private final int ATTACK_KEY = KeyEvent.VK_A;
    private final int DEFEND_KEY = KeyEvent.VK_D;
    private final int HEAL_KEY = KeyEvent.VK_H;
    private final int CHARGE_KEY = KeyEvent.VK_C;
    private final int SELECT_KEY = KeyEvent.VK_ENTER;
    private final int LEFT_KEY = KeyEvent.VK_LEFT;
    private final int RIGHT_KEY = KeyEvent.VK_RIGHT;
    private final int UP_KEY = KeyEvent.VK_UP;
    private final int DOWN_KEY = KeyEvent.VK_DOWN;

    private final double TEXT_SCALAR = 40;
    private int myLooserSpriteID;

    /**
     * Constructor for a Battle.
     * 
     * @param gameManager The parent GameManager that is creating this battle. Will be
     *        alerted when battle ends.
     * @param modeObjectType The object type this mode uses, i.e. BattleObject.java
     * @param involvedIDs A list of IDs of the sprites involved in this battle.
     */

    // need to pass ids of battle participants upon battle creation
    public BattleMode (int ID, GameManager gameManager, Class<BattleObject> modeObjectType,
            List<Integer> involvedIDs) {
        super(ID, gameManager, modeObjectType);
        myInvolvedIDs = involvedIDs;
        myMessages = new ArrayList<String>();
    }

    @Override
    public void pause () {
        myTeams.clear();
    }

    @Override
    public void resume () {
        makeTeams();
        initialize();
        // System.out.println("BattleStarting!");
        myMessages.add(myEnemy.getName() + " encountered!");
        configureInputHandling();
        // getGameManager().handleEvent(GameManager.GameEvent.BATTLE_OVER, new
        // ArrayList<Integer>());
    }

    /**
     * Allows the battle to receive input from the keyboard.
     * Controls include Attack, Defend, Heal, and Charge, set as constants.
     */
    public void configureInputHandling () {
        // use input api for key handling. notice how you can only invoke methods w/t parameters...
        try {
            GamePane.keyboardController.setControl(ATTACK_KEY, KeyboardController.RELEASED, this,
                    "triggerAttackEvent");
            GamePane.keyboardController.setControl(DEFEND_KEY, KeyboardController.RELEASED, this,
                    "triggerDefendEvent");
            GamePane.keyboardController.setControl(HEAL_KEY,
                    KeyboardController.RELEASED, this, "triggerHealEvent");
            GamePane.keyboardController.setControl(CHARGE_KEY, KeyboardController.RELEASED,
                    this, "triggerChargeEvent");
            GamePane.keyboardController.setControl(LEFT_KEY, KeyboardController.RELEASED, 
                    this, "triggerLeftEvent");
            GamePane.keyboardController.setControl(RIGHT_KEY, KeyboardController.RELEASED, 
                    this, "triggerRightEvent");
            GamePane.keyboardController.setControl(UP_KEY, KeyboardController.RELEASED, 
                    this, "triggerUpEvent");
            GamePane.keyboardController.setControl(DOWN_KEY, KeyboardController.RELEASED, 
                    this, "triggerDownEvent");
            GamePane.keyboardController.setControl(SELECT_KEY, KeyboardController.RELEASED, 
                    this, "triggerSelectEvent");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeTeams () {
        // BAD BAD TEST CODE
        // I don't think this is super bad test code now,
        // We should eventually add support for a single sprite with multiple battleobjects
        // (i.e. A single Pokemon trainer with multiple pokemon)
        myTeams = new ArrayList<Team>();

        // adding player
        List<BattleObject> team1BattleObjects = new ArrayList<BattleObject>();
        Sprite s1 = getGameManager().findSpriteWithID(myInvolvedIDs.get(0));
        BattleObject bo1 = s1.getObject(BattleObject.class).get(0);
        team1BattleObjects.add(bo1);

        // adding enemy
        List<BattleObject> team2BattleObjects = new ArrayList<BattleObject>();
        Sprite s2 = getGameManager().findSpriteWithID(myInvolvedIDs.get(1));
        BattleObject bo2 = s2.getObject(BattleObject.class).get(0);
        team2BattleObjects.add(bo2);

        myTeams.add(new Team(team2BattleObjects));
        myTeams.add(new Team(team1BattleObjects));
    }

    @Override
    public void update () {
        
        if (isBattleOver()) {
            endBattle();
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
        int teamNumber = 0;
        for (Team t : myTeams) {
            for (BattleObject b : t.getBattleObjects()) {
                b.paintBattleObject(g, 0, teamNumber * height / 3, width, height / 3);
            }
            teamNumber += 1;
        }
        paintMenu(g);
    }

    //someone please fix this...
    public void paintMenu (Graphics g) {
        // paint the message box/battle option menu
        Dimension myWindow = getGameManager().getPaneDimension();
        int height = myWindow.height;
        int width = myWindow.width;

        //move this to XML 
        File imageFile = new File("src/vooga/turnbased/resources/image/GUI/Message_Sign.png");
        Image box = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        g.drawImage(box, 0, 0, width, height, null);

        // draw the messages
        int counter = 0;
        if (myMessages.size() > MESSAGE_NUM) {
            counter = myMessages.size() - MESSAGE_NUM;
        }
        Graphics2D g2d = (Graphics2D) g;
        int fontSize = calcFontSize(width, height);
        // System.out.println(fontSize);
        Font font = new Font("Sans_Serif", Font.PLAIN, fontSize);
        FontRenderContext frc = g2d.getFontRenderContext();
        g2d.setColor(Color.BLACK);
        for (int i = 0; counter + i < myMessages.size(); i++) {
            String currentMessage = myMessages.get(counter + i);
            GlyphVector gv = font.createGlyphVector(frc, currentMessage);
            float horizontalShift = (float) ((width/TEXT_SCALAR)*3);
            float verticalShift = (float) (height/TEXT_SCALAR*4.5);
            float spacingBetweenLines = (float) (1.2)*(fontSize) * i;
            g2d.drawGlyphVector(gv, horizontalShift, (2 * height / 3 + verticalShift + spacingBetweenLines));
        }
        
        g.drawImage(box, width/2, 0, width/2, height, null);
        drawOptions(g, width/2, 2*height/3, width/2, height/3);
    }


    public void drawOptions (Graphics g, int x, int y, int width, int height) {
        //format positions based on width and height of the box...maybe?
        
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("Sans_Serif", Font.PLAIN, 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        g2d.setColor(Color.BLACK);
        String[] options = {"ATTACK", "DEFEND", "CHARGE", "HEAL"};
        for (int i = 0; i < 4; i ++) {
            String s = options[i];
            GlyphVector gv = font.createGlyphVector(frc, s);
            if (i == 0) {
                g2d.drawGlyphVector(gv, x+60, y+80);
            }
            else if (i == 1) {
                g2d.drawGlyphVector(gv, x+220, y+80);
            }
            else if (i == 2) {
                g2d.drawGlyphVector(gv, x+60, y+140);
            }
            else if (i == 3) {
                g2d.drawGlyphVector(gv, x+220, y+140);
            }
        }
        File imageFile = new File("src/vooga/turnbased/resources/image/GUI/Arrow.png");
        Image arrow = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        if (mySelection == 0) {
            g.drawImage(arrow, x+40, y+60, 20, 20, null);
        }
        else if (mySelection == 1) {
            g.drawImage(arrow, x+200, y+60, 20, 20, null);
        }
        else if (mySelection == 2) {
            g.drawImage(arrow, x+40, y+120, 20, 20, null);
        }
        else if (mySelection == 3) {
            g.drawImage(arrow, x+200, y+120, 20, 20, null);
        }
    }
    
    private int calcFontSize(int width, int height) {
        // current hypotenuse of regular window size is ~965, with font size 25
        // 965/25 = 37.4
        return (int) (Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2))/TEXT_SCALAR);
    }

    /**
     * Initializes a battle with the current lists of BattleObjects
     */
    @Override
    public void initialize () {
        myState = BattleState.WAITING_FOR_MOVE;
        myTurnCount = 0;
        // Initialize myTeamStartRandomizer to 0 to number of teams (exclusive)
        // the seed value is going to determine which team starts where 0 =
        // "team 1"
        Random generator = new Random();
        myTeamStartRandomizer = generator.nextInt(myTeams.size());
        myPlayerObject = nextTeam().nextPlayer();
        myEnemy = nextTeam().nextPlayer();
    }

    private void endBattle () {
        System.out.println("End battle!");
        List<Integer> battleLooserIDs = new ArrayList<Integer>();
        battleLooserIDs.add(myLooserSpriteID);
        if(!myPlayerObject.isAlive()){
            getGameManager().flagEvent("GAME_LOST", new ArrayList<Integer>());
        } else {
            getGameManager().flagEvent("BATTLE_OVER", battleLooserIDs);
        }
    }

    private boolean isBattleOver () {
        boolean teamDead = false;
        for (Team t : myTeams) {
            if (!t.stillAlive()) {
                teamDead = true;
                // hardcoded
                Sprite looserSprite = getGameManager().findSpriteWithID(t.getBattleObjects().get(0).getID());
                // not to be confused with tighterSprite...
                myLooserSpriteID = looserSprite.getID();
                looserSprite.clear();
                getGameManager().deleteSprite(looserSprite.getID());
            }
        }
        return teamDead;
    }

    public void triggerAttackEvent () {
        // for now, player attacks enemy player
        // by difference in defense
        myMessages.add(myPlayerObject.getName() + " used ATTACK");
        myPlayerObject.attackEnemy(myEnemy);
        // check if enemy/opposing team is dead
        if (isBattleOver()) { return; }
        generateEnemyMove();
    }

    public void triggerHealEvent () {
        // for now, increases player health by 1
        // by difference in defense
        myMessages.add(myPlayerObject.getName() + " used HEAL");
        myPlayerObject.changeStat("health", myPlayerObject.getStat("health").intValue() + 3);
        if (myPlayerObject.getStat("health").intValue() > myPlayerObject.getStat("maxHealth")
                .intValue()) {
            myPlayerObject.changeStat("health", myPlayerObject.getStat("maxHealth").intValue());
        }
        // check if enemy/opposing team is dead
        if (isBattleOver()) { return; }
        // opposing team makes some predetermined action
        generateEnemyMove();
    }

    public void triggerDefendEvent () {
        // for now, increases player defense by one; other team still attacks
        // System.out.println("You use DEFEND");
        myMessages.add(myPlayerObject.getName() + " used DEFEND");
        myPlayerObject.changeStat("defense", myPlayerObject.getStat("defense").intValue() + 1);
        // displayBattleStats();
        // check if enemy/opposing team is dead
        if (isBattleOver()) { return; }
        // opposing team makes some predetermined action
        generateEnemyMove();
    }

    public void triggerChargeEvent () {
        // for now, increases player attack by one; other team still attacks
        // System.out.println("You use CHARGE");
        myMessages.add(myPlayerObject.getName() + " used CHARGE");
        myPlayerObject.changeStat("attack", myPlayerObject.getStat("attack").intValue() + 1);
        // displayBattleStats();
        // check if enemy/opposing team is dead
        if (isBattleOver()) { return; }
        // opposing team makes some predetermined action
        generateEnemyMove();
    }
    
    //don't even start...i know D:
    public void triggerLeftEvent () {
        if (mySelection == 1) {
            mySelection = 0;
        }
        else if (mySelection == 3) {
            mySelection = 2;
        }
    }
    public void triggerRightEvent () {
        if (mySelection == 0) {
            mySelection = 1;
        }
        else if (mySelection == 2) {
            mySelection = 3;
        }
    }
    public void triggerUpEvent () {
        if (mySelection == 2) {
            mySelection = 0;
        }
        else if (mySelection == 3) {
            mySelection = 1;
        }
    }
    public void triggerDownEvent () {
        if (mySelection == 0) {
            mySelection = 2;
        }
        else if (mySelection == 1) {
            mySelection = 3;
        }
    }
    public void triggerSelectEvent () {
        if (mySelection == 0) {
            triggerAttackEvent();
        }
        else if (mySelection == 1) {
            triggerDefendEvent();
        }
        else if (mySelection == 2) {
            triggerChargeEvent();
        }
        else if (mySelection == 3) {
            triggerHealEvent();
        }
    }

    private void generateEnemyMove () {
        double random = Math.random();
        if (random >= 0 && random < .5) {
            // attack
            myEnemy.attackEnemy(myPlayerObject);
            myMessages.add(myEnemy.getName() + " used ATTACK");
            // System.out.println("Your enemy uses ATTACK");
        }
        if (random >= .5 && random < .7) {
            // defend
            myEnemy.changeStat("defense", myEnemy.getStat("defense").intValue() + 1);
            myMessages.add(myEnemy.getName() + " used DEFEND");
            // System.out.println("Your enemy uses DEFEND");
        }
        if (random >= .7 && random < .9) {
            // charge
            myEnemy.changeStat("attack", myEnemy.getStat("attack").intValue() + 1);
            myMessages.add(myEnemy.getName() + " used CHARGE");
            // System.out.println("Your enemy uses CHARGE");
        }
        if (random >= .9 && random < 1) {
            // increase health
            myEnemy.changeStat("health", myEnemy.getStat("health").intValue() + 3);
            if (myEnemy.getStat("health").intValue() > myEnemy.getStat("maxHealth").intValue()) {
                myEnemy.changeStat("health", myEnemy.getStat("maxHealth").intValue());
            }
            myMessages.add(myEnemy.getName() + " used HEAL");
            // System.out.println("Your enemy uses HEALTH INCREASE");
        }
        // displayBattleStats();
    }

    // for debugging etc
    private void displayBattleStats () {
        System.out.println("My health: " + myPlayerObject.getStat("health"));
        System.out.println("My defense: " + myPlayerObject.getStat("defense"));
        System.out.println("Enemy health: " + myEnemy.getStat("health"));
        System.out.println("Enemy defense: " + myEnemy.getStat("defense"));
    }

    /**
     * Returns the team that should make the next move and increments
     * myTurnCount by 1.
     * 
     * @return Team that should make next move.
     */
    private Team nextTeam () {
        // Get team index and increment turncount
        myTeams.add(myTeams.remove(0));
        return myTeams.get(0);
    }

    private class Team {
        private final List<BattleObject> myBattleObjects;

        public Team (List<BattleObject> battleObjs) {
            myBattleObjects = battleObjs;
        }

        public boolean stillAlive () {
            for (BattleObject teamMember : myBattleObjects) {
                if (teamMember.isAlive()) { return true; }
            }
            return false;
        }

        public void makeMove (Team enemy) {
            // TODO: fill in behavior here
            // get user input to choose active enemy sprite
            // currentEnemyBattleObject.attackEnemy(currentPlayerBattleObject);
        }

        public List<BattleObject> getBattleObjects () {
            return myBattleObjects;
        }

        public BattleObject nextPlayer () {
            myBattleObjects.add(myBattleObjects.remove(0));
            return myBattleObjects.get(0);
        }
        // TODO: Add more methods here to aid team behavior
    }

    private enum BattleState {
        WAITING_FOR_MOVE, MESSAGE, ANIMATING
    }

    @Override
    public void processMouseInput (Point myMousePosition, int myMouseButton) {
        // TODO Auto-generated method stub
        
    }
}
