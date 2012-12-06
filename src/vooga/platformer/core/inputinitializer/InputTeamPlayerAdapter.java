package vooga.platformer.core.inputinitializer;

import vooga.platformer.gameobject.Player;

/**
 * An adapter class necessary for allowing our Player design to work with the input team's
 * zero-parameter method.
 * @author Niel Lebeck
 *
 */
public class InputTeamPlayerAdapter {

    Player myPlayer;
    
    public InputTeamPlayerAdapter(Player pl) {
        myPlayer = pl;
    }
    
    public void goLeft() {
        myPlayer.fireControlStrategy("GoLeft");
    }
    
    public void goRight() {
        myPlayer.fireControlStrategy("GoRight");
    }
    
    public void goUp() {
        myPlayer.fireControlStrategy("Jump");
    }

    
    public void shoot() {
        myPlayer.fireControlStrategy("Shoot");
    }
    
    public void stop() {
        myPlayer.fireControlStrategy("Stop");
    }
}
