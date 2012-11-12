package games.platformerdemo;

import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.UpdateStrategy;


/**
 * @author Yaqi Zhang
 * 
 */
public class PlayerMoveStrategy implements UpdateStrategy {
    private static final double VELOCITY = 3;
    private GameObject myPlayer;
    private double myVelocityX;
    private double myVelocityY;

    /**
     * @param player GameObject
     */
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
    }

    /**
     * 
     */
    public void goLeft() {
        myVelocityX = -VELOCITY;
    }

    /**
     * 
     */
    public void goRight() {
        myVelocityX = VELOCITY;
    }

    /**
     * This method needs to be called by input when there is no button pressed.
     */
    public void stop() {
        myVelocityX = 0;
    }
}
