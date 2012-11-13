package vooga.turnbased.gamecore;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.List;
import vooga.turnbased.gameobject.BattleObject;

public class BattleMode extends GameMode {
    private List<BattleObject> myBattleObjects;

    public BattleMode (GameManager gm) {
        super(gm);
    }

    @Override
    public void paint (Graphics g, int canvasWidth, int canvasHeight) {
        // TODO Auto-generated method stub
        
    }

    public void start(List<BattleObject> battleObjects) {
        myBattleObjects = battleObjects; 
    }
    
    public void updateLoop() {
    
    }

    @Override
    public void handleKeyEvent (KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}
