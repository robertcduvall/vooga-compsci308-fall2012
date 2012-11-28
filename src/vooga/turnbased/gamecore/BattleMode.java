package vooga.turnbased.gamecore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import util.input.core.KeyboardController;
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

    private final int ATTACK_KEY = KeyEvent.VK_A;
    private final int DEFENSE_KEY = KeyEvent.VK_D;
    private final int INCREASE_HEALTH_KEY = KeyEvent.VK_I;
    private final int CHARGE_KEY = KeyEvent.VK_C;
    
    /**
     * Constructor for a Battle.
     * 
     * @param gm The parent GameManager that is creating this battle. Will be
     *        alerted
     *        when battle ends.
     * @param modeObjectType The object type this mode uses, i.e.
     *        BattleObject.java
     */

    // need to pass ids of battle participants upon battle creation
    public BattleMode (GameManager gameManager, Class<BattleObject> modeObjectType,
            List<Integer> involvedIDs) {
        super(gameManager, modeObjectType);
        myInvolvedIDs = involvedIDs;
    }

    @Override
    public void pause () {
        myTeams.clear();
    }

    @Override
    public void resume () {
        makeTeams();
        initialize();
        System.out.println("BattleStarting!");
        configureInputHandling();
        // getGameManager().handleEvent(GameManager.GameEvent.BATTLE_OVER, new
        // ArrayList<Integer>());
    }

    public void configureInputHandling () {
        // use input api for key handling. notice how you can only invoke methods w/t parameters...
        try {
            GamePane.keyboardController.setControl(ATTACK_KEY, KeyboardController.RELEASED, this,
                    "triggerAttackEvent");
            GamePane.keyboardController.setControl(DEFENSE_KEY, KeyboardController.RELEASED, this,
                    "triggerDefenseEvent");
            GamePane.keyboardController.setControl(INCREASE_HEALTH_KEY,
                    KeyboardController.RELEASED, this, "triggerIncreaseHealthEvent");
            GamePane.keyboardController.setControl(CHARGE_KEY, KeyboardController.RELEASED,
                    this, "triggerChargeEvent");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeTeams () {
        // BAD BAD TEST CODE
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
        // while(myState != BattleState.WAITING_FOR_MOVE){
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
                b.paintBattleObject(g, 0, (teamNumber) * height / 3, width, height / 3);
                // can this be done via b.paint(g)? i.e. let battleobject do
                // it's own paint/update work
                // trying to keep paint/update signature consistent across
                // project..
            }
            teamNumber += 1;
        }
        paintMenu(g);
    }

    public void paintMenu (Graphics g) {
        Dimension myWindow = getGameManager().getPaneDimension();
        int height = myWindow.height;
        int width = myWindow.width;
        //Graphics2D g2d = (Graphics2D) g;
        //g2d.setColor(Color.GREEN);
        //g2d.fillRect(0, 2 * height / 3, width, height / 3);
        File imageFile = new File("src/vooga/turnbased/resources/image/GUI/Message_Sign.png");
        Image box = new ImageIcon(imageFile.getAbsolutePath()).getImage();
        g.drawImage(box, 0, 0, width, height, null);        
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
        getGameManager().flagEvent("BATTLE_OVER", new ArrayList());
        setActive(false);
    }

    private boolean isBattleOver () {
        boolean teamDead = false;
        for (Team t : myTeams) {
            if (!t.stillAlive()) {
                teamDead = true;
                // hardcoded
                getGameManager().findSpriteWithID(t.getBattleObjects().get(0).getID()).clear();
                getGameManager().deleteSprite(t.getBattleObjects().get(0).getID());
            }
        }
        return teamDead;
    }

    // methods used in input api should be public
    public void triggerAttackEvent () {
        // for now, player attacks enemy player
        // by difference in defense
        System.out.println("You use ATTACK");
        myPlayerObject.attackEnemy(myEnemy);
        //displayBattleStats();
        // check if enemy/opposing team is dead
        if (isBattleOver()) { return; }
        // opposing team makes some predetermined action
        generateEnemyMove();
    }

    public void triggerIncreaseHealthEvent () {
        // for now, increases player health by 1
        // by difference in defense
        System.out.println("You use ATTACK");
        myPlayerObject.changeStat("health", myPlayerObject.getStat("health").intValue() + 3);
        if (myPlayerObject.getStat("health").intValue() > myPlayerObject.getStat("maxHealth").intValue()) {
            myPlayerObject.changeStat("health", myPlayerObject.getStat("maxHealth").intValue());
        }
        //displayBattleStats();
        // check if enemy/opposing team is dead
        if (isBattleOver()) { return; }
        // opposing team makes some predetermined action
        generateEnemyMove();
    }

    public void triggerDefenseEvent () {
        // for now, increases player defense by one; other team still attacks
        System.out.println("You use DEFEND");
        myPlayerObject.changeStat("defense", myPlayerObject.getStat("defense").intValue() + 1);
        //displayBattleStats();
        // check if enemy/opposing team is dead
        if (isBattleOver()) { return; }
        // opposing team makes some predetermined action
        generateEnemyMove();
    }
    
    public void triggerChargeEvent () {
     // for now, increases player attack by one; other team still attacks
        System.out.println("You use CHARGE");
        myPlayerObject.changeStat("attack", myPlayerObject.getStat("attack").intValue() + 1);
        //displayBattleStats();
        // check if enemy/opposing team is dead
        if (isBattleOver()) { return; }
        // opposing team makes some predetermined action
        generateEnemyMove();
    }

    private void generateEnemyMove () {
        double random = Math.random();
        if (random >= 0 && random < .5) {
            // attack
            myEnemy.attackEnemy(myPlayerObject);
            System.out.println("Your enemy uses ATTACK");
        }
        if (random >= .5 && random < .7) {
            // defend
            myEnemy.changeStat("defense", myEnemy.getStat("defense").intValue() + 1);
            System.out.println("Your enemy uses DEFEND");
        }
        if (random >= .7 && random < .9) {
            // charge
            myEnemy.changeStat("attack", myEnemy.getStat("attack").intValue() + 1);
            System.out.println("Your enemy uses CHARGE");
        }
        if (random >= .9 && random < 1) {
            // increase health
            myEnemy.changeStat("health", myEnemy.getStat("health").intValue() + 3);
            if (myPlayerObject.getStat("health").intValue() > myPlayerObject.getStat("maxHealth").intValue()) {
                myPlayerObject.changeStat("health", myPlayerObject.getStat("maxHealth").intValue());
            }
            System.out.println("Your enemy uses HEALTH INCREASE");
        }
        //displayBattleStats();
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
}
