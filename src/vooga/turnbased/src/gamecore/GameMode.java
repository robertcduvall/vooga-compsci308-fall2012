package gamecore;

import java.util.Observable;

public abstract class GameMode extends Observable {

    private final GameManager myGameManager;

    public GameMode(GameManager gm) {
        myGameManager = gm;
        this.addObserver(myGameManager);
    }

    // TODO: Make event more specific
    private void notifyGameManager(Object event) {
        setChanged();
        notifyObservers(event);
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
