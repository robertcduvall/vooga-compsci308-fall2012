package vooga.platformer.collision;

/**
 * This class is used to scan the screen and return a list of CollisionEvents
 * for each collision detected on the screen
 * @author Bruce
 *
 */
public class CollisionChecker {
    
    static Iterable<CollisionEvent> checkCollisions(Level level);
}
