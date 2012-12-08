package games.spacebugs;

import java.awt.event.KeyEvent;
import vooga.shooter.gameObjects.Player;

public class SpaceBugsActionAdapter {
    private Player1 myPlayer1;
    private Player2 myPlayer2;
    
    public SpaceBugsActionAdapter(Player1 pl, Player2 p2) {
        myPlayer1 = pl;
        myPlayer2 = p2;
    }
    
    public void goLeft1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_A), null);
    }
    
    public void goRight1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_D), null);
    }
    
    public void goUp1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_W), null);
    }
    
    public void goDown1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_S), null);
    }
    
    public void fireShot1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_F), null);
    }
    
    public void stop1() {
        myPlayer1.doEvent("stop", null);
    }
    
    public void goLeft2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_J), null);
    }
    
    public void goRight2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_L), null);
    }
    
    public void goUp2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_I), null);
    }
    
    public void goDown2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_K), null);
    }
    
    public void fireShot2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_H), null);
    }
    
    public void stop2() {
        myPlayer2.doEvent("stop", null);
    }
}
