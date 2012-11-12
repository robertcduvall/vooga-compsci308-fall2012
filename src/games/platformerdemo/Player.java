package games.platformerdemo;

/**
 * @author Yaqi Zhang
 *
 */
public class Player extends MovingObject {
    /**
     * @param configString
     */
    public Player (String configString) {
        super(configString, "player");
        addStrategy(new PlayerMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
    }
}
