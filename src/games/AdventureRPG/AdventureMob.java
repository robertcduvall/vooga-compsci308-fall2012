package games.AdventureRPG;

import java.awt.Image;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import vooga.turnbased.gameobject.battleobject.BattleObject;

public class AdventureMob extends BattleObject {
    private final String HEALTH_STAT = "health";
    private final String ENERGY_STAT = "energy";
    private final String ATTACK_STAT = "attack";
    private final String DEFENSE_STAT = "defense";
    private final String MAX_HEALTH_STAT = "maxHealth";
    private final String USED = " used "; 
    
    
    private final double OPTION1_LOWER_BOUND = .0;
    private final double OPTION1_UPPER_BOUND = .5;
    private final double OPTION2_LOWER_BOUND = .5;
    private final double OPTION2_UPPER_BOUND = .7;
    private final double OPTION3_LOWER_BOUND = .7;
    private final double OPTION3_UPPER_BOUND = .9;
    private final double OPTION4_LOWER_BOUND = .9;
    private final double OPTION4_UPPER_BOUND = 1;

    public AdventureMob (Set<String> allowableModes, String condition,
            Map<String, Number> stats, String name, Image image) {
        super(allowableModes, condition, stats, name, image);
    }

    @Override
    public String doRandomOption (BattleObject target) {
        Random randomGenerator = new Random();
        double random = randomGenerator.nextDouble();
        String message = null;
        if (random >= OPTION1_LOWER_BOUND && random < OPTION1_UPPER_BOUND) {
            message = doOption1(target);
        }
        if (random >= OPTION2_LOWER_BOUND && random < OPTION2_UPPER_BOUND) {
            message = doOption2(target);
        }
        if (random >= OPTION3_LOWER_BOUND && random < OPTION3_UPPER_BOUND) {
            message = doOption3(target);
        }
        if (random >= OPTION4_LOWER_BOUND && random < OPTION4_UPPER_BOUND) {
            message = doOption4(target);
        }
        return message;
    }

    @Override
    public String doOption (int MenuOptionSelected, BattleObject target) {
        String message = getName() + " didn't do anything";
        switch (MenuOptionSelected){
            case 1: message = doOption1(target);
            break;
            case 2: message = doOption2(target);
            break;
            case 3: message = doOption3(target);
            break;
            case 4: message = doOption4(target);
            break;
            default: break;
        }
        return message;
    }

    @Override
    protected String doOption1 (BattleObject target) {
        //Quick attack
        healMe(4);
        attackEnemy(target);
        return (getName() + USED + getOptions()[0]);
    }

    @Override
    protected String doOption2 (BattleObject target) {
        //Concentrate
        healMe(4);
        int oldEnergy = getStat(ENERGY_STAT).intValue();
        if (oldEnergy <  4) {
            changeStat(ENERGY_STAT, oldEnergy + 2);
        }
        else {
            int newEnergy = oldEnergy * 5 / 4;
            changeStat(ENERGY_STAT, newEnergy);
        }
        return (getName() + USED + getOptions()[1]);
    }

    @Override
    protected String doOption3 (BattleObject target) {
        // Powerful Attack
        int damage = getStat(ENERGY_STAT).intValue() + getStat(ATTACK_STAT).intValue();
        attackEnemy(target, damage);
        return (getName() + USED + getOptions()[2]);
    }

    @Override
    protected String doOption4 (BattleObject target) {
        // Steal Energy
        AdventureMob enemy = (AdventureMob) target;
        int energyReceived = enemy.loseEnergy();
        changeStat(ENERGY_STAT, getStat(ENERGY_STAT).intValue() + energyReceived - 2);
        return (getName() + USED + getOptions()[3]);
    }

    @Override
    protected void takeDamage (int damageDone) {
        int healthLost = damageDone - getStat(DEFENSE_STAT).intValue();
        if (healthLost > 0) {
            changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue() - healthLost);
        }

    }

    @Override
    protected void attackEnemy (BattleObject enemy) {
        AdventureMob target = (AdventureMob) enemy;
        target.takeDamage(getStat(ATTACK_STAT).intValue());
    }
    
    protected void attackEnemy (BattleObject enemy, int damage){
        AdventureMob target = (AdventureMob) enemy;
        target.takeDamage(damage);
    }
    
    protected int loseEnergy () {
        int energyChange = 0;
        int oldEnergy = getStat(ENERGY_STAT).intValue();
        if (oldEnergy <  3) {
            changeStat(ENERGY_STAT, 0);
            energyChange = 2;
        }
        else {
            int newEnergy = oldEnergy * 2 / 3;
            energyChange = oldEnergy * 1 / 3;
            changeStat(ENERGY_STAT, newEnergy);
        }
        return energyChange;
    }

    @Override
    public String getDeathMessage () {
        
        return getName() + " has been beaten.";
    }

    @Override
    public String getStartFightingMessage (boolean isPlayerControlled) {
        return getName() + " has engaged in battle!";
    }

    @Override
    public String[] getOptions () {
        String[] ret = {"QUICK ATTACK", "CONCENTRATE", "POWER ATTACK", "STEAL ENERGY"};
        return ret;
    }

    @Override
    protected String[] getStatLines () {
        String[] ret = new String[2];
        int myHealth = getStat("health").intValue();
        int myMaxHealth = getStat("maxHealth").intValue();
        int myEnergy = getStat(ENERGY_STAT).intValue();

        ret[0] = "Health: " + myHealth + "/" + myMaxHealth;
        ret[1] = "Energy: " + myEnergy;

        return ret;
    }
    
    private void healMe(int amount){
        changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue() + amount);
        if (getStat(HEALTH_STAT).intValue() > getStat(MAX_HEALTH_STAT).intValue()) {
            changeStat(HEALTH_STAT, getStat(MAX_HEALTH_STAT).intValue());
        }
    }

}
