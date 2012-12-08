package vooga.shooter.gameplay.inputInitialize;

import java.awt.event.KeyEvent;
import vooga.shooter.gameObjects.Player;

public class InputTeamSpriteActionAdapter {
    private Player myPlayer;
    
    public InputTeamSpriteActionAdapter(Player pl) {
        myPlayer = pl;
    }
    
    public void goLeft() {
        myPlayer.doEvent(Integer.toString(KeyEvent.VK_LEFT), null);
    }
    
    public void goRight() {
        myPlayer.doEvent(Integer.toString(KeyEvent.VK_RIGHT), null);
    }
    
    public void goUp() {
        myPlayer.doEvent(Integer.toString(KeyEvent.VK_UP), null);
    }
    
    public void goDown() {
        myPlayer.doEvent(Integer.toString(KeyEvent.VK_DOWN), null);
    }
    
    public void fireShot() {
        myPlayer.doEvent(Integer.toString(KeyEvent.VK_SPACE), null);
    }
    
    public void stop() {
        myPlayer.doEvent("stop", null);
    }
}
