package games.geo4games.movingbricks;

import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.UpdateStrategy;

/**
 * Simple update strategy that moves an object back and forth.
 * @author Grant Oakley
 *
 */
public class SideToSideScrollStrategy implements UpdateStrategy {

    private static final long serialVersionUID = 1L;
    private GameObject myGameObject;
    private int myLeftBound;
    private int myRightBound;
    private int myVelocity;
    private boolean myIsMovingRight = true;

    public SideToSideScrollStrategy (GameObject updatedObject, int leftBound, int rightBound,
                                int velocity) {
        myGameObject = updatedObject;
        myLeftBound = leftBound;
        myRightBound = rightBound;
        myVelocity = Math.abs(velocity);
        myGameObject.setX(leftBound);
    }

    @Override
    public void applyAction () {
        if (myIsMovingRight && (myGameObject.getX() + myGameObject.getWidth() < myRightBound)) {
            myGameObject.setX(myGameObject.getX() + myVelocity);
        }
        else if (myIsMovingRight) {
            myIsMovingRight = false;
            myGameObject.setX(myGameObject.getX() - myVelocity);
        }
        else if (!myIsMovingRight && (myGameObject.getX() + myGameObject.getWidth() > myLeftBound)) {
            myGameObject.setX(myGameObject.getX() - myVelocity);
        }
        else {
            myIsMovingRight = true;
            myGameObject.setX(myGameObject.getX() + myVelocity);
        }
    }

}
