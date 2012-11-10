/**
 * Abstract class that represent a mode in a game,
 * such as map mode where players walk around, or battle mode where players fight against enemies
 * @author rex, Volodymyr
 */
package vooga.turnbased.src.gamecore;

import java.util.Observable;

public abstract class GameMode extends Observable {

    private final GameManager myGameManager;

    /**
     * Constructor
     * @param gm The GameManager which receive information about how sprites interact
     */
    public GameMode(GameManager gm) {
        myGameManager = gm;
        this.addObserver(myGameManager);
    }

    /**
     * Notify GameManager about the events that happened
     * @param event Event to be handled by the GameManager
     */
    private void notifyGameManager(Object event) {
        setChanged();
        notifyObservers(event);
    }

	/**
	 * Test Test Test
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
