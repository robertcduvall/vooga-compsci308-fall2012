package games.trexgame;

import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import vooga.turnbased.gameobject.battleobject.BattleObject;

/**
 * new battle mechanism
 * 
 * @author Tony
 * 
 */
public class TRexMonster extends BattleObject {
	private final String HEALTH_STAT = "health";
	private final String MAX_HEALTH_STAT = "maxHealth";
	// private final String MANA_STAT = "MP";
	// private final String MAX_MANA_STAT = "maxMP";
	private final String ATTACK_STAT = "atk";
	private final String DEFENSE_STAT = "def";
	private final String EVASION_STAT = "eva";
	private final String ACCURACY_STAT = "acc";
	private final String CRITICAL_RATE_STAT = "crit";
	private final String USED = " used ";

	private final double OPTION1_BOUND = .7;
	private final double OPTION2_BOUND = .8;
	private final double OPTION3_BOUND = .9;
	private final double OPTION4_BOUND = 1.0;

	public TRexMonster(Set<String> allowableModes, String condition,
			Map<String, Number> stats, String name, Image image) {
		super(allowableModes, condition, stats, name, image);
	}

	public TRexMonster(Set<String> allowableModes, String condition,
			Image image, List<String> stats) {
		super(allowableModes, condition, image, stats);
	}

	@Override
	public void doRandomOption(BattleObject target, List<String> battleMessages) {
		Random randomGenerator = new Random();
		double random = randomGenerator.nextDouble();
		if (random >= 0 && random < OPTION1_BOUND) {
			doOption1(target, battleMessages);
		}
		if (random >= OPTION1_BOUND && random < OPTION2_BOUND) {
			doOption2(target, battleMessages);
		}
		if (random >= OPTION2_BOUND && random < OPTION3_BOUND) {
			doOption3(target, battleMessages);
		}
		if (random >= OPTION3_BOUND && random < OPTION4_BOUND) {
			doOption4(target, battleMessages);
		}
	}

	@Override
	public void doOption(int MenuOptionSelected, BattleObject target,
			List<String> battleMessages) {
		switch (MenuOptionSelected) {
		case 1:
			doOption1(target, battleMessages);
			break;
		case 2:
			doOption2(target, battleMessages);
			break;
		case 3:
			doOption3(target, battleMessages);
			break;
		case 4:
			doOption4(target, battleMessages);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doOption1(BattleObject target, List<String> battleMessages) {
		battleMessages.add(getName() + USED + getOptions()[0]);
		attackEnemy(target, battleMessages);
	}

	@Override
	protected void doOption2(BattleObject target, List<String> battleMessages) {
		battleMessages.add(getName() + USED + getOptions()[1]);
		resetStats();
		changeStat(DEFENSE_STAT, getStat(DEFENSE_STAT).intValue() * 1.5);
		changeStat(ATTACK_STAT, getStat(ATTACK_STAT).intValue() * 0.6);
		changeStat(EVASION_STAT, getStat(EVASION_STAT).intValue() * 0.5);
	}

	@Override
	protected void doOption3(BattleObject target, List<String> battleMessages) {
		battleMessages.add(getName() + USED + getOptions()[2]);
		resetStats();
		changeStat(DEFENSE_STAT, getStat(DEFENSE_STAT).intValue() * 0.5);
		changeStat(ATTACK_STAT, getStat(ATTACK_STAT).intValue() * 1.5);
		changeStat(ACCURACY_STAT, getStat(ACCURACY_STAT).intValue() * 1.3);
	}

	@Override
	protected void doOption4(BattleObject target, List<String> battleMessages) {
		battleMessages.add(getName() + USED + getOptions()[3]);
		resetStats();
		changeStat(EVASION_STAT, getStat(EVASION_STAT).intValue() * 1.8);
		changeStat(ACCURACY_STAT, getStat(ACCURACY_STAT).intValue() * 0.5);
		changeStat(ACCURACY_STAT, getStat(ATTACK_STAT).intValue() * 0.8);
	}

	@Override
	public void takeDamage(int damageDone, List<String> battleMessages) {
		if (damageDone > 0) {
			changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue()
					- damageDone);
		}
	}

	@Override
	protected void attackEnemy(BattleObject enemy, List<String> battleMessages) {
		int atk = getStat(ATTACK_STAT).intValue();
		int eDef = enemy.getStat(DEFENSE_STAT).intValue();
		int damage = (atk * atk) / (atk + eDef);

		Random randomGenerator = new Random();
		double random = randomGenerator.nextDouble();
		if (random <= getStat(CRITICAL_RATE_STAT).doubleValue()) {
			damage *= 2;
			battleMessages.add("A critial hit!!");
		} else {
			random = randomGenerator.nextDouble();
			double hitRate = (getStat(ACCURACY_STAT).intValue() - enemy
					.getStat(EVASION_STAT).intValue()) / 100.0;
			if (random > hitRate) {
				damage = 0;
				battleMessages.add(enemy.getName() + " evaded the attack");
			}
		}
		enemy.takeDamage(damage, battleMessages);
	}

	@Override
	public String getDeathMessage() {
		return this.getName() + " fainted.";
	}

	@Override
	public String getStartFightingMessage(boolean isPlayerControlled) {
		String name = this.getName();
		Random randomGenerator = new Random();
		int i = randomGenerator.nextInt(2);

		if (isPlayerControlled) {
			String[] messages = { name + " sent out.", name + " GO!" };
			return messages[i];
		} else {
			String[] messages = { name + " encountered.", name + " appeared." };
			return messages[i];
		}
	}

	@Override
	public String[] getOptions() {
		String[] ret = { "Attack", "Barrier", "Sword Dance", "Agility" };
		return ret;
	}

	@Override
	protected String[] getStatLines() {
		String[] ret = new String[5];
		int myHP = getStat(HEALTH_STAT).intValue();
		int myMaxHP = getStat(MAX_HEALTH_STAT).intValue();
		// int myMP = getStat(MANA_STAT).intValue();
		// int myMaxMP = getStat(MAX_MANA_STAT).intValue();
		int myAtk = getStat(ATTACK_STAT).intValue();
		int myDef = getStat(DEFENSE_STAT).intValue();
		int myAcc = getStat(ACCURACY_STAT).intValue();
		int myEva = getStat(EVASION_STAT).intValue();
		// double myCrit = getStat(CRITICAL_RATE_STAT).doubleValue();

		ret[0] = "HP: " + myHP + "/" + myMaxHP;
		// ret[1] = "MP: " + myMP + "/" + myMaxMP;
		ret[1] = "Attack: " + myAtk;
		ret[2] = "Defense: " + myDef;
		ret[3] = "Accuracy: " + myAcc;
		ret[4] = "Evasion: " + myEva;
		// ret[6] = "Crit Rate: " + myCrit;
		return ret;
	}

}
