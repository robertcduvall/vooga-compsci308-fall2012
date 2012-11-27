package vooga.turnbased.gamecore;

import java.util.ArrayList;
import java.util.List;

public class GameEvent {
    private final String myName;
    private final List<Integer> myInvolvedIDs;

    public GameEvent (String eventName, List<Integer> involvedIDs) {
        myName = eventName;
        myInvolvedIDs = new ArrayList<Integer>(involvedIDs);
    }

    public String getModeEventName () {
        return myName;
    }

    public List<Integer> getEventInvolvedIDs () {
        return myInvolvedIDs;
    }
}
