package games.platformerdemo;

import vooga.platformer.gameobject.UpdateStrategy;


/**
 * @author Yaqi Zhang
 * 
 */
public class PlayerMoveStrategy extends SimpleMoveStrategy {
    private static final double VELOCITY = 10;
    private Player myPlayer;

    /**
     * @param player GameObject
     */
    public PlayerMoveStrategy (Player player) {
        super(player);
        myPlayer = player;
    }

    /**
     * 
     */
    public void jump () {
        myPlayer.setVelocity(myPlayer.getVelocity().getX(), -VELOCITY);
        // TODO: need to make sure this jump() will only be called once even if
        // the player keeps pressing jump button.
    }

    /**
     * 
     */
    public void goLeft () {
        myPlayer.setVelocity(-VELOCITY, myPlayer.getVelocity().getY());
    }

    /**
     * 
     */
    public void goRight () {
        myPlayer.setVelocity(VELOCITY, myPlayer.getVelocity().getY());
    }

    /**
     * This method needs to be called by input when there is no button pressed.
     */
    public void stop () {
        myPlayer.setVelocity(0, myPlayer.getVelocity().getY());
    }
}
