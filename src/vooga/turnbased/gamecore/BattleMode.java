package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import vooga.turnbased.gameobject.BattleObject;


/**
 * This is gamemode that will run a battle given two lists of BattleObjects that
 * will fight each other.
 * 
 * @author David Howdyshell, Michael Elgart, Kevin Gao, Jenni Mercado
 * 
 */
public class BattleMode extends GameMode {
    private List<Team> myTeams;
    private BattleObject myPlayerObject;
    private BattleObject myEnemy;
    private BattleState myState;
    private int myTurnCount;
    private int myTeamStartRandomizer;
    
    private final int ATTACK_KEY = KeyEvent.VK_A;
    private final int DEFENSE_KEY = KeyEvent.VK_D;

    /**
     * Constructor for a Battle.
     * 
     * @param gm The parent GameManager that is creating this battle. Will be
     *        alerted
     *        when battle ends.
     * @param modeObjectType The object type this mode uses, i.e.
     *        BattleObject.java
     */
    public BattleMode (GameManager gm, Class modeObjectType) {
        super(gm, modeObjectType);
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
        // getGameManager().handleEvent(GameManager.GameEvent.BATTLE_OVER, new
        // ArrayList<Integer>());
    }

    private void makeTeams () {
        // BAD BAD TEST CODE
        setObjects();
        myTeams = new ArrayList<Team>();
        List<BattleObject> team1BattleObjects = new ArrayList<BattleObject>();
        team1BattleObjects.add((BattleObject) getObjects().get(0));
        List<BattleObject> team2BattleObjects = new ArrayList<BattleObject>();
        team2BattleObjects.add((BattleObject) getObjects().get(1));
        // BAD BAD BAD
        myTeams.add(new Team(team1BattleObjects));
        myTeams.add(new Team(team2BattleObjects));
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
        int i = -300; // fix painting this stuff...
        for (Team t : myTeams) {
            i += 300;
            for (BattleObject b : t.getBattleObjects()) {
                b.paint(g, 0, 0 + i, 800, 300);
            }
        }
    }

    /**
     * Initializes a battle with the current lists of BattleObjects
     */
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
    }

    private boolean isBattleOver () {
        boolean allDead = false;
        for (Team t : myTeams) {
            if (!t.stillAlive()) {
                allDead = true;
                // hardcoded
                getGameManager().deleteSprite(t.getBattleObjects().get(0).getID());
            }
        }
        return allDead;
    }

    @Override
    public void handleKeyPressed (KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleKeyReleased (KeyEvent e) {
        // should some of this be handled in update?
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case ATTACK_KEY: 
                triggerAttackEvent();
                break;
            case DEFENSE_KEY:
                triggerDefenseEvent();
                break;
            default:
                break;
        }
    }

    private void triggerAttackEvent() {
        // for now, player attacks enemy player
        // by difference in defense
        myPlayerObject.attackEnemy(myEnemy);
        System.out.println("You use ATTACK");
        displayBattleStats();
        // check if enemy/opposing team is dead
        if (isBattleOver()) {
            return;
        }
        // pause while enemy "thinks"
        // opposing team makes some predetermined action
        myEnemy.attackEnemy(myPlayerObject);
        System.out.println("Your enemy uses ATTACK");
        displayBattleStats();
    }

    private void triggerDefenseEvent() {
        // for now, increases player defense by one; other team still attacks
        myPlayerObject.attackEnemy(myEnemy);
        System.out.println("You use DEFENSE");
        myPlayerObject.setDefense(myPlayerObject.getDefense() + 1);
        displayBattleStats();
        // check if enemy/opposing team is dead
        if (isBattleOver()) {
            return;
        }
        // pause while enemy "thinks"
        // opposing team makes some predetermined action
        myEnemy.attackEnemy(myPlayerObject);
        System.out.println("Your enemy uses ATTACK");
        displayBattleStats();
    }

    // for debugging etc
    private void displayBattleStats () {
        System.out.println("My health: " + myPlayerObject.getHealth());
        // System.out.println("My defense: " + myPlayerObject.getDefense());
        System.out.println("Enemy health: " + myEnemy.getHealth());
        // System.out.println("Enemy defense: " + myEnemy.getDefense());
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
    public void configureInputHandling () {
        // handle inputs
    }

    @Override
    public void init () {
        // TODO Auto-generated method stub

    }
}
