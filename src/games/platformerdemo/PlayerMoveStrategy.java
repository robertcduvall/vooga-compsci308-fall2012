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
<<<<<<< HEAD
    public PlayerMoveStrategy(GameObject player) {
        myPlayer = player;
    }

    @Override
    public void applyAction() {
        myPlayer.setX(myVelocityX + myPlayer.getX());
        myPlayer.setY(myVelocityY + myPlayer.getY());
    }

    /**
     * 
     */
    public void jump() {
        myVelocityY = -VELOCITY;
=======
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
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    /**
     * 
     */
<<<<<<< HEAD
    public void goLeft() {
        myVelocityX = -VELOCITY;
=======
    public void goLeft () {
        myPlayer.setVelocity(-VELOCITY, myPlayer.getVelocity().getY());
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    /**
     * 
     */
<<<<<<< HEAD
    public void goRight() {
        myVelocityX = VELOCITY;
=======
    public void goRight () {
        myPlayer.setVelocity(VELOCITY, myPlayer.getVelocity().getY());
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }

    /**
     * This method needs to be called by input when there is no button pressed.
     */
<<<<<<< HEAD
    public void stop() {
        myVelocityX = 0;
=======
    public void stop () {
        myPlayer.setVelocity(0, myPlayer.getVelocity().getY());
>>>>>>> f5741ba1b396d7e73c1993f7f217c5c9362159a8
    }
}
