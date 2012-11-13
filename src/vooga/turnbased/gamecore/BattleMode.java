package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;
import vooga.turnbased.gameobject.BattleObject;

public class BattleMode extends GameMode {
    private List<BattleObject> myTeam1BattleObjects;
    private List<BattleObject> myTeam2BattleObjects;

    public BattleMode (GameManager gm, List<BattleObject> team1BattleObjects, List<BattleObject> team2BattleObjects) {
        super(gm);
        myTeam1BattleObjects = team1BattleObjects;
        myTeam2BattleObjects = team2BattleObjects;
        start();
    }

    @Override
    public void paint (Graphics g, int canvasWidth, int canvasHeight) {
        
    }
    
    public void start() {
        //initialize things, start game loop
    }
    
    public void updateLoop() {
        //update the loop after each turn
    }

    @Override
    public void handleKeyPressed (KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handleKeyReleased (KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}
