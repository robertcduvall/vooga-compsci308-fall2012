package vooga.platformer.collision;

import vooga.platformer.level.Level;

/**
 * CollisionChecker is used to scan the screen and return a list of CollisionEvents
 * for each collision detected on the screen
 * @author Bruce
 *
 */
public interface CollisionChecker {
    
    Iterable<CollisionEvent> checkCollisions(Level level);
}
