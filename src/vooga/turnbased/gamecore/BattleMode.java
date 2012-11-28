package vooga.turnbased.gamecore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import util.input.core.KeyboardController;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gui.GamePane;
import vooga.turnbased.gui.InputAPI;


/**
 * This is gamemode that will run a battle given two lists of BattleObjects that
 * will fight each other.
 * 
 * @author David Howdyshell, Michael Elgart, Kevin Gao, Jenni Mercado
 * 
 */
public class BattleMode extends GameMode implements InputAPI {
    private List<Team> myTeams;
    private BattleObject myPlayerObject;
    private BattleObject myEnemy;
    private BattleState myState;
    private int myTurnCount;
    private int myTeamStartRandomizer;

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

    // need to pass ids of battle participants upon battle creation
    public BattleMode (GameManager gameManager, Class<BattleObject> modeObjectType,
                       List<Integer> myInvolvedIDs) {
       super(gameManager, modeObjectType);
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
            GamePane.keyboardController.setControl(KeyEvent.VK_A, KeyboardController.RELEASED, this, "hardcodeAttack");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // input api only supports invoking methods w/t parameters
    public void hardcodeAttack() {
        myPlayerObject.attackEnemy(myEnemy);
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.fillRect(0, 2 * height / 3, width, height / 3);
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
        //getGameManager().removeMode(this);
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

    /*@Override
    public void handleKeyPressed (KeyEvent e) {
        // use configureInputHandling() instead. see InputAPI.java interface for usage
    }
    
    @Override
    public void handleKeyReleased (KeyEvent e) {
        // use configureInputHandling() instead
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A:
                myPlayerObject.attackEnemy(myEnemy);
                // System.out.println("My health: " +
                // myPlayerObject.getHealth());
                // System.out.println("Enemy health: " + myEnemy.getHealth());
                break;
            default:
                break;
        }
    }*/

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
