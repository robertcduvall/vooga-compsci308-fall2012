package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vooga.turnbased.gameobject.BattleObject;
import vooga.turnbased.gui.GameWindow;

public class BattleMode extends GameMode {
	private List<Team> myTeams;
	private BattleState myState;
	private int myTurnCount;
	private int myTeamStartRandomizer;

	public BattleMode(GameManager gm, Class modeObjectType) {
		super(gm, modeObjectType);
		makeTeams();
		initialize();
		System.out.println("BattleStarting!");
		try {
			System.in.read(); // stall the game
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void makeTeams() {
		// BAD BAD TEST CODE
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
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {
		 Image background = GameWindow.importImage("EditorBackgroundImage");
	        g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), null);
	}

	public void initialize() {
		myState = BattleState.WAITING_FOR_MOVE;
		myTurnCount = 0;
		// Initialize myTeamStartRandomizer to 0 to number of teams (exclusive)
		// the seed value is going to determine which team starts where 0 =
		// "team 1"
		Random generator = new Random();
		myTeamStartRandomizer = generator.nextInt(myTeams.size());
	}

	public void updateLoop() {
		if (isBattleOver()) {
			endBattle();
		}
		// TODO: figure out how this should work. Right now we just give it the
		// previous team
		// TODO: Take into account animating, requesting user input for player
		// team, etc.
		nextTeam().makeMove(myTeams.get(myTurnCount - 1 % myTeams.size()));
	}

	private void endBattle() {
		// TODO: let myGameManager know the battle has ended
		// need to save game state (sprite health, status, etc)
		// then transition back to mapmode
	}

	private boolean isBattleOver() {
		boolean allDead = false;
		for (Team t : myTeams) {
			if (!t.stillAlive()) {
				allDead = true;
			}
		}
		return allDead;
	}

	@Override
	public void handleKeyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void handleKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Returns the team that should make the next move and increments
	 * myTurnCount by 1.
	 * 
	 * @return Team that should make next move.
	 */
	private Team nextTeam() {
		// Get team index and increment turncount
		int numTeams = myTeams.size();
		int teamIndex = (myTurnCount++ + myTeamStartRandomizer) % numTeams;
		return myTeams.get(teamIndex);
	}

	private class Team {
		private final List<BattleObject> myBattleObjects;

		public Team(List<BattleObject> battleObjs) {
			myBattleObjects = battleObjs;
		}

		public boolean stillAlive() {
			for (BattleObject teamMember : myBattleObjects) {
				if (teamMember.isAlive()) {
					return true;
				}
			}
			return false;
		}

		public void makeMove(Team enemy) {
			// TODO: fill in behavior here
			// get user input to choose active enemy sprite
			// currentEnemyBattleObject.attackEnemy(currentPlayerBattleObject);
		}

		// TODO: Add more methods here to aid team behavior
	}

	private enum BattleState {
		WAITING_FOR_MOVE, MESSAGE, ANIMATING
	}

	@Override
	public void processGameEvents() {
		// TODO Auto-generated method stub
		
	}
}
