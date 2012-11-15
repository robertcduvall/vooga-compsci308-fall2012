package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import vooga.turnbased.gamecore.GameManager;

/**
 * This is a test battle monster that will be used to see if the BattleMode functions.
 * Extends the abstract class BattleObject.
 * @author Michael Elgart
 *
 */
public class TestMonster extends BattleObject {

    public TestMonster (int id, GameManager.GameEvent event, int defense, int attack, int health) {
        super(id, event, defense, attack, health);
    }

    @Override
    public void takeDamage (int damageDone) {
        int healthLost =  (damageDone)-  getMyDefense();
        setMyHealth(getMyHealth() - healthLost);
    }

    @Override
    public void attackEnemy (BattleObject enemy) {
        enemy.takeDamage(getMyAttack());
    }

    @Override
    public void handleKeyReleased (KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleKeyPressed (KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void paint (Graphics g, int x, int y, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update (int delayTime) {
        // TODO Auto-generated method stub

    }

}
