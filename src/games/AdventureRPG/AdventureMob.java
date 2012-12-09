package games.AdventureRPG;

import java.awt.Image;
import java.util.List;
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

    public AdventureMob (Set<String> allowableModes, String condition,
            Image image, List<String> stats) {
        super(allowableModes, condition, image, stats);
    }

    @Override
    public void doRandomOption(BattleObject target, List<String> battleMessages) {
        Random randomGenerator = new Random();
        double random = randomGenerator.nextDouble();
        String message = null;
        if (random >= OPTION1_LOWER_BOUND && random < OPTION1_UPPER_BOUND) {
            doOption1(target, battleMessages);
        }
        if (random >= OPTION2_LOWER_BOUND && random < OPTION2_UPPER_BOUND) {
            doOption2(target, battleMessages);
        }
        if (random >= OPTION3_LOWER_BOUND && random < OPTION3_UPPER_BOUND) {
            doOption3(target, battleMessages);
        }
        if (random >= OPTION4_LOWER_BOUND && random < OPTION4_UPPER_BOUND) {
            doOption4(target, battleMessages);
        }

    }

    @Override
    public void doOption (int MenuOptionSelected, BattleObject target, List<String> battleMessages) {
        String message = getName() + " didn't do anything";
        switch (MenuOptionSelected){
            case 1: doOption1(target, battleMessages);
            break;
            case 2: doOption2(target, battleMessages);
            break;
            case 3: doOption3(target, battleMessages);
            break;
            case 4: doOption4(target, battleMessages);
            break;
            default: 
                battleMessages.add(message);
                break;
        }
    }

    @Override
    protected void doOption1 (BattleObject target, List<String> battleMessages) {
        //Quick attack
        healMe(10);
        attackEnemy(target, battleMessages);
        battleMessages.add((getName() + USED + getOptions()[0]));
    }

    @Override
    protected void doOption2 (BattleObject target, List<String> battleMessages) {
        //Concentrate
        healMe(10);
        int oldEnergy = getStat(ENERGY_STAT).intValue();
        if (oldEnergy <  4) {
            changeStat(ENERGY_STAT, oldEnergy + 2);
        }
        else {
            int newEnergy = oldEnergy * 5 / 4 + 1;
            changeStat(ENERGY_STAT, newEnergy);
        }
        battleMessages.add((getName() + USED + getOptions()[1]));
    }

    @Override
    protected void doOption3 (BattleObject target, List<String> battleMessages) {
        // Powerful Attack
        int damage = getStat(ENERGY_STAT).intValue() + getStat(ATTACK_STAT).intValue();
        attackEnemy(target, damage);
        battleMessages.add((getName() + USED + getOptions()[2]));
        changeStat(ENERGY_STAT, 1);
    }

    @Override
    protected void doOption4 (BattleObject target, List<String> battleMessages) {
        // Steal Energy
        healMe(5);
        AdventureMob enemy = (AdventureMob) target;
        int energyReceived = enemy.loseEnergy();
        changeStat(ENERGY_STAT, getStat(ENERGY_STAT).intValue() + energyReceived - 2);
        battleMessages.add (getName() + USED + getOptions()[3]);
    }

    @Override
    public void takeDamage (int damageDone, List<String> battleMessages) {
        int healthLost = damageDone - getStat(DEFENSE_STAT).intValue();
        if (healthLost > 0) {
            changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue() - healthLost);
        }
    }

    @Override
    protected void attackEnemy (BattleObject enemy, List<String> battleMessages) {
        AdventureMob target = (AdventureMob) enemy;
        target.takeDamage(getStat(ATTACK_STAT).intValue(), battleMessages);
    }
    
    protected void attackEnemy (BattleObject enemy, int damage){
        AdventureMob target = (AdventureMob) enemy;
        target.takeDamage(damage, null);
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
        return getName() + " has engaged!";
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

