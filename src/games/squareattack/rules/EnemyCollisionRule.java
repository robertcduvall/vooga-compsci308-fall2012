package games.squareattack.rules;

import games.squareattack.GameManager;
import games.squareattack.objects.ExternalMathVector2D;
import games.squareattack.objects.SoundManager;
import games.squareattack.sprites.Square;
import java.util.List;
import util.input.android.events.AndroidVibration;
import util.mathvector.MathVector2D;

/**
 * 
 * @author Ben Schwab
 *
 */
public class EnemyCollisionRule implements GameRule {
    
    private List<Square> myAttackers;
    private Square myDefender;
    private SoundManager mySound;
    private GameManager myManager;
    
    public EnemyCollisionRule(List<Square> attackers, Square defender, GameManager manager){
        myDefender = defender;
        myAttackers = attackers;
        mySound = SoundManager.getInstance();
       myManager = manager;
    }

    @Override
    public void checkRule () {
       for(Square attacker: myAttackers){
           if(attacker.intersects(myDefender)){
               myDefender.damage();
               myDefender.addExternalForce(new ExternalMathVector2D((MathVector2D) myDefender.getLastMovementVector().scale(-3.5), .8));
               attacker.addExternalForce(new ExternalMathVector2D((MathVector2D) attacker.getLastMovementVector().scale(-5.5), .8));
               mySound.playSound(SoundManager.COLLISION_SOUND);
               myManager.vibrateController(myDefender, new AndroidVibration(450,100,50));
               myManager.vibrateController(attacker, new AndroidVibration(450,100,50));
               
           }
       }
        
    }

}
