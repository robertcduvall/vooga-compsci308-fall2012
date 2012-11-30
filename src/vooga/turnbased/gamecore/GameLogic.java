package vooga.turnbased.gamecore;

import java.util.List;
import vooga.turnbased.gameobject.battleobject.BattleObject;
import vooga.turnbased.gameobject.mapobject.MapObject;

/**
 * handles game events
 *
 */
public class GameLogic {

    private GameManager myGameManager;
    private int myPlayerSpriteID;
    
    public GameLogic (GameManager gameManager) {
        myGameManager = gameManager;
    }

    public void processEvents (List<GameEvent> events) {
        while (!events.isEmpty()) {
            GameEvent m = events.remove(0);
            handleEvent(m);
        }
    }
    
    /**
     * @param event
     * this was supposed to be handled by a map: event -> destination game mode
     * but due to the complexity of the program (multiple active modes support)
     * i no longer see a way to implement it with a map. ideas are welcome
     */
    private void handleEvent (GameEvent event) {
        String eventName = event.getModeEventName();
        List<Integer> myInvolvedIDs = event.getEventInvolvedIDs();
        if ("NO_ACTION".equals(eventName)) {
            // do nothing
        }
        else if ("BATTLE_START".equals(eventName)) {
            BattleMode battleMode = new BattleMode(myGameManager, BattleObject.class, myInvolvedIDs);
            myGameManager.getActiveModes().add(battleMode);
            myGameManager.changeCurrentMode(battleMode);
        }
        else if ("BATTLE_OVER".equals(eventName)) {
            if(event.getEventInvolvedIDs().get(0) == myGameManager.getPlayerSpriteID()) {
                myGameManager.processGameOver();
            }
            myGameManager.removeInactiveModes();
            myGameManager.resumeModes();
        }
        else if ("SWITCH_LEVEL".equals(eventName)) {
            MapObject enteringMapObject = myGameManager.findSpriteWithID(myInvolvedIDs.get(0)).getMapObject();
            myGameManager.initializeGameLevel(myGameManager.getNewMapResource(), enteringMapObject);
        }
        else if ("CONVERSATION_START".equals(eventName)) {
            OptionMode optionMode =
                    new OptionMode(myGameManager, MapObject.class, myInvolvedIDs);
            // do not add the same conversation twice
            for (GameMode mode : myGameManager.getActiveModes()) {
                if ((mode instanceof OptionMode) &&
                    (optionMode.equalsTo((OptionMode) (mode)))) { return; }
            }
            myGameManager.getActiveModes().add(optionMode);
        }
        else if ("CONVERSATION_OVER".equals(eventName)) {
            myGameManager.removeInactiveModes();
        }
        else if ("INTERACTION_COMPLETED".equals(eventName)) {
            // to win, need to interact with specific (or all) sprites, i.e.
            // NPCs, Enemies, Pickup-able items, teleports, etc.
            /*
             * myGameLogic.processInteraction(event);
             * if(myGameLogic.winningConditionsAreMet()) { processGameWin(); }
             */
        }
        else {
            // System.err.println("Unrecognized mode event requested.");
        }
    }
}
