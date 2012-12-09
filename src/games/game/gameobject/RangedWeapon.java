package games.game.gameobject;

import util.sound.SoundPlayer;
import games.game.levels.ProjectileManager;


/**
 * Represents a ranged weapon that can be used to
 * shoot things in the game.
 * 
 * @author Paul Dannenberg
 * 
 */
public class RangedWeapon {

    // Ideally these would not live here. If I were to add more weapons this
    // class would need to be majorly refactored.
    private final int myRateOfFire = 1000;
    private final int myDamage = 30;
    private final int myAmmoCapacity = 30;
    private final SoundPlayer mySoundPlayer;
    private final String mySoundFilePath = "/sounds/gunshotsound.mp3";
    private long myLastShotTime;
    private ProjectileManager myManager;
    private int myAmmo;

    /**
     * Creates a new object whose projectiles will be simulated
     * by a particular projectile manager.
     * 
     * @param manager The object that will simulate the projectiles
     *        once they have been fired.
     */
    public RangedWeapon(ProjectileManager manager) {
        myManager = manager;
        myAmmo = myAmmoCapacity;
        mySoundPlayer = new SoundPlayer(mySoundFilePath);
    }

    /**
     * Causes this object to fire a shot.
     */
    public void shoot() {
        if (canShoot()) {
            myManager.simulateShot(myDamage);
        }
        myLastShotTime = System.currentTimeMillis();
        myAmmo--;
        playSound();
    }

    /**
     * Plays the sound corresponding to the firing of the ranged
     * weapon.
     */
    public void playSound() {
        mySoundPlayer.playOnce();
    }

    /**
     * Determines whether or not the weapon is ready to fire. This is
     * dependent on the weapon's rate of fire and ammo reserves.
     * 
     * @return True if the weapon is ready to fire, false otherwise.
     */
    private boolean canShoot() {
        return myLastShotTime - System.currentTimeMillis() > myRateOfFire
                && myAmmo > 0;
    }

    /**
     * Reloads the weapon.
     */
    public void reload() {
        myAmmo = myAmmoCapacity;
    }

}
