package vooga.turnbased.gameobject.mapstrategy;




import vooga.turnbased.gameobject.mapobject.MapObject;
import vooga.turnbased.gameobject.mapobject.MapPlayerObject;

/**
 * The Conversation Strategy doesn't seem to do very much. Michael is writing this Javadoc
 * but Rex might know more about what this is supposed to do. Ask him.
 * @author Rex
 *
 */
public class ConversationStrategy extends MapStrategy {

    //private static final String MODE_EVENT = "CONVERSATION_START";
    private static final String MODE_EVENT = "optionstuff";

    /**
     * The constructor for the Conversation Strategy.
     */
    public ConversationStrategy () {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void performStrategy (MapObject target) {
        super.performStrategy(target);
        //does nothing if it is not a player
        if (target instanceof MapPlayerObject) {

        }
    }

}
