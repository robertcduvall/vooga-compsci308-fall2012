package games.game.gameobject;

import games.game.levels.ProjectileManager;


/**
 * Represents a ranged weapon that can be used to
 * shoot things in the game.
 * @author Paul Dannenberg
 *
 */
public class RangedWeapon {

    // Ideally these would not live here.
    private final int RATE_OF_FIRE = 1000;
    private final int DAMAGE = 30;
    private final int AMMO_CAPACITY = 30;
    
    private long myLastShotTime;
    private ProjectileManager myManager;
    private int myAmmo;
    

    /**
     * Creates a new object whose projectiles will be simulated
     * by a particular projectile manager.
     * @param manager The object that will simulate the projectiles
     * once they have been fired.
     */
    public RangedWeapon(ProjectileManager manager) {
        myManager = manager;
        myAmmo = AMMO_CAPACITY;
    }

    /**
     * Causes this object to fire a shot.
     */
    public void shoot() {
        if (canShoot()) {
            myManager.simulateShot(DAMAGE);
        }
        myLastShotTime = System.currentTimeMillis();
        myAmmo--;
    }

    /**
     * Determines whether or not the weapon is ready to fire. This is
     * dependent on the weapon's rate of fire and ammo reserves.
     * @return True if the weapon is ready to fire, false otherwise.
     */
    private boolean canShoot() {
        return myLastShotTime - System.currentTimeMillis() > RATE_OF_FIRE
                && myAmmo > 0;
    }

    /**
     * Reloads the weapon.
     */
    public void reload() {
        myAmmo = AMMO_CAPACITY;
    }

}
