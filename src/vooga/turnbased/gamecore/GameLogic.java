package vooga.turnbased.gamecore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * maps conditions to mode events to see if a mode event needs to be flagged
 * 
 */
public class GameLogic {
    private GameManager myGameManager;
    private final Map<String, List<List<String>>> myEventConditions;
    private ArrayList<String> myModeEventNames;
    private ArrayList<String> myGameConditions;

    public GameLogic (GameManager gm, Map<String, List<List<String>>> eventConditions) {
        myGameManager = gm;
        myEventConditions = eventConditions;
        myModeEventNames = new ArrayList<String>();
        myGameConditions = new ArrayList<String>();

    }

    public List<String> getNewEvents () {
        List<String> retList = new ArrayList<String>();
        while (!myModeEventNames.isEmpty()) {
            retList.add(myModeEventNames.remove(0));
        }
        return retList;
    }

    public void flagCondition (String newCondition, List<Integer> involvedSpriteIDs) {
        myGameConditions.add(newCondition);
        checkConditions(involvedSpriteIDs);
    }

    private void checkConditions (List<Integer> involvedSpriteIDs) {
        Set<String> usedConditions = new TreeSet<String>();
        for (String eventName : myEventConditions.keySet()) {
            for (List<String> requiredConditions : myEventConditions.get(eventName)) {
                if (myGameConditions.containsAll(requiredConditions)) {
                    myGameManager.flagEvent(eventName, involvedSpriteIDs);
                    usedConditions.addAll(requiredConditions);
                }
            }
        }
        myGameConditions.removeAll(usedConditions);
    }
}
