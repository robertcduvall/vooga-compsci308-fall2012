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
 * @author David Howdyshell
 */
public class GameLogic {
    private GameManager myGameManager;
    private Map<String, List<List<String>>> myEventConditions;
    private ArrayList<String> myModeEventNames;
    private TreeSet<String> myGameConditions;

    /**
     * GameLogic class monitors conditions that game objects trigger and will
     * flag a mode transition event if the correct set of conditions has
     * occurred
     * 
     * @param gm - the game manager object
     */
    public GameLogic (GameManager gm) {
        myGameManager = gm;
        myEventConditions = new HashMap<String, List<List<String>>>();
        myModeEventNames = new ArrayList<String>();
        myGameConditions = new TreeSet<String>();
    }

    /**
     * Add the mappings of modes to the conditions required to trigger a
     * transition into that mode
     * 
     * @param eventConditions - {mode name -> list of list of conditions}
     */
    public void addEventConditions (Map<String, List<List<String>>> eventConditions) {
        myEventConditions.putAll(eventConditions);
    }

    /**
     * GameLogic stores event names in myModeEventNames until GameManager asks
     * if anything has happened. Then gives these events to GameManager,
     * clearing its own record of them simultaneously.
     * 
     * @return - list of event names since GameManager last requested them
     */
    public List<String> getNewEvents () {
        List<String> retList = new ArrayList<String>();
        while (!myModeEventNames.isEmpty()) {
            retList.add(myModeEventNames.remove(0));
        }
        return retList;
    }

    /**
     * Informs GameLogic that a new condition has occurred.
     * 
     * @param newCondition - condition that just occurred
     * @param involvedSpriteIDs - list of Sprite IDs involved in this condition
     *        occurrence
     */
    public void flagCondition (String newCondition, List<Integer> involvedSpriteIDs) {
        myGameConditions.add(newCondition);
        checkConditions(involvedSpriteIDs);
    }

    /**
     * Performs a check of all conditions to see if any mode transitions should
     * take place.
     * 
     * @param involvedSpriteIDs
     */
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
