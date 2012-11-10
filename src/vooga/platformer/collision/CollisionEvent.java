package vooga.platformer.collision;

/**
 * For collisions happen between any pair of game objects there would be a specific
 * collision event to deal with that. This CollisionEvent is a super class for all
 * the other collision events.
 * @author Bruce
 *
 */
public abstract class CollisionEvent {
    public CollisionEvent(GameObject a, GameObject b) {
        //Constructor with two game objects colliding on the screen
    }
    
    public void applyCollision(Level level) {
        //Apply collision to both objects
    }
}
