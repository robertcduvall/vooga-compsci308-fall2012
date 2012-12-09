package games.game.gameobject;

import java.util.List;


/**
 * This object represents the actual player. This class's
 * methods will be called when user input triggers the player
 * to perform actions on the screen.
 * 
 * @author Paul Dannenberg
 * 
 */
public class Player {

    private List<RangedWeapon> myWeapons;
    private RangedWeapon myCurrentWeapon;

    /**
     * Creates a new player to take part in the game.
     * @param weapons The weapons available to the player.
     */
    public Player(List<RangedWeapon> weapons) {
        myWeapons = weapons;
        myCurrentWeapon = weapons.get(0);
    }

    /**
     * Fires a shot.
     */
    public void shoot() {
        myCurrentWeapon.shoot();
    }

    /**
     * Reloads the player's current weapon.
     */
    public void reload() {
        myCurrentWeapon.reload();
    }

    /**
     * Changes the player's current weapon if the player has more than
     * one weapon. Repeatedly calling this method will cause the player
     * to cycle through all his available weapons.
     */
    public void switchWeapon() {
        int indexOfCurrentWeapon = myWeapons.indexOf(myCurrentWeapon);
        if (indexOfCurrentWeapon == myWeapons.size() - 1) {
            myCurrentWeapon = myWeapons.get(0);
        } else {
            myCurrentWeapon = myWeapons.get(indexOfCurrentWeapon + 1);
        }
    }

}
