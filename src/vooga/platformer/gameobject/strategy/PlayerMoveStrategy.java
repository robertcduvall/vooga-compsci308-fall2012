package vooga.platformer.gameobject.strategy;


import vooga.platformer.gameobject.Player;

/**
 * Any Player Move Strategy Should extends this Strategy.
 * 
 * @author Yaqi Zhang
 * 
 */
public class PlayerMoveStrategy {
    private static final double HORIZONTAL_VELOCITY = 5;
    private static final double VERTICAL_VELOCITY = -3;
    private Player myPlayer;


    /**
     * @param player GameObject
     */
    public PlayerMoveStrategy(Player player) {
//        super(player);
        myPlayer = player;
    }

    /**
     * 
     */
    public void jump () {

    }

    /**
     * 
     */
    public void goLeft () {
//        myPlayer.setVelocity(-HORIZONTAL_VELOCITY, myPlayer.getVelocity()
//                .getY());
    }

    /**
     * 
     */
    public void goRight () {
//        myPlayer.setVelocity(HORIZONTAL_VELOCITY, myPlayer.getVelocity().getY());
    }

    /**
     * This method needs to be called by input when there is no button pressed.
     */
    public void stop () {
//        myPlayer.setVelocity(0, myPlayer.getVelocity().getY());
    }
}
