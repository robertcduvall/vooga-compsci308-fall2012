package games.spacebugs;

import java.awt.event.KeyEvent;
import vooga.shooter.gameObjects.Player;

/**
 * Interfaces between the input team's KeyboardController and the Player class.
 * @author Stephen Hunt
 * @author Jesse Starr
 */

public class SpaceBugsActionAdapter {
    private Player myPlayer1;
    private Player myPlayer2;
    
    public SpaceBugsActionAdapter(Player pl, Player p2) {
        myPlayer1 = pl;
        myPlayer2 = p2;
    }
    
    public void goLeft1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_LEFT), null);
    }
    
    public void goRight1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_RIGHT), null);
    }
    
    public void goUp1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_UP), null);
    }
    
    public void goDown1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_DOWN), null);
    }
    
    public void fireShot1() {
        myPlayer1.doEvent(Integer.toString(KeyEvent.VK_SPACE), null);
    }
    
    public void stop1() {
        myPlayer1.doEvent("stop", null);
    }
    
    public void goLeft2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_LEFT), null);
    }
    
    public void goRight2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_RIGHT), null);
    }
    
    public void goUp2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_UP), null);
    }
    
    public void goDown2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_DOWN), null);
    }
    
    public void fireShot2() {
        myPlayer2.doEvent(Integer.toString(KeyEvent.VK_SPACE), null);
    }
    
    public void stop2() {
        myPlayer2.doEvent("stop", null);
    }
}
