package vooga.turnbased.gamecore;

import java.awt.Graphics;
import vooga.turnbased.gameobject.battle.Enemy;

public class BattleMode extends GameMode {
    private Enemy myEnemy;

    public BattleMode (GameManager gm, Enemy e) {
        super(gm);
        setEnemy(e);
    }

    @Override
    public void paint (Graphics g, int canvasWidth, int canvasHeight) {
        // TODO Auto-generated method stub
        
    }

    public void setEnemy (Enemy e) {
        
    }
}
