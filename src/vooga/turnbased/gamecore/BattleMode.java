package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import vooga.turnbased.gameobject.BattleObject;


public class BattleMode extends GameMode {
    private final List<Team> myTeams;
    private BattleState myState;
    private int myTurnCount;
    private int myTeamStartRandomizer;

    public BattleMode (GameManager gm, List<BattleObject> team1BattleObjects,
                       List<BattleObject> team2BattleObjects) {
        super(gm);
        myTeams = new ArrayList<Team>();
        myTeams.add(new Team(team1BattleObjects));
        myTeams.add(new Team(team2BattleObjects));
        initialize();
    }

    @Override
    public void paint (Graphics g, int canvasWidth, int canvasHeight) {

    }

    public void initialize () {
        myState = BattleState.WAITING_FOR_MOVE;
        myTurnCount = 0;
        // Initialize myTeamStartRandomizer to 0 to number of teams (exclusive)
        // the seed value is going to determine which team starts where 0 =
        // "team 1"
        java.util.Random generator = new java.util.Random();
        myTeamStartRandomizer = generator.nextInt(myTeams.size());
    }

    public void updateLoop () {
        // TODO: figure out how this should work. Right now we just give it the previous team
        // TODO: Take into account animating, requesting user input for player team, etc.
        nextTeam().makeMove(myTeams.get(myTurnCount-1 % myTeams.size()));
    }

    @Override
    public void handleKeyEvent (KeyEvent e) {
    }

    /**
     * Returns the team that should make the next move and increments
     * myTurnCount by 1.
     *
     * @return Team that should make next move.
     */
    private Team nextTeam () {
        // Get team index and increment turncount
        int numTeams = myTeams.size();
        int teamIndex = (myTurnCount++ + myTeamStartRandomizer) % numTeams;
        return myTeams.get(teamIndex);
    }

    private class Team {
        private final List<BattleObject> myBattleObjects;

        public Team (List<BattleObject> battleObjs) {
            myBattleObjects = battleObjs;
        }

        public void makeMove(Team enemy) {
            // TODO: fill in behavior here
        }

        // TODO: Add more methods here to aid team behavior
    }

    private enum BattleState {
        WAITING_FOR_MOVE, MESSAGE, ANIMATING
    }
}
