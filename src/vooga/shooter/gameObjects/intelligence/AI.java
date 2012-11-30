package vooga.shooter.gameObjects.intelligence;

import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameplay.Game;

/**
 * Controls the movement of a Sprite, such as an Enemy or Bullet.
 * The calculate method is called during each update cycle for
 * each Sprite with an AI attached. The AI will then analyze the
 * current game state and determine what the Sprite should do next.
 * Different types of AIs will look at different aspects of the
 * game and respond in different ways.
 * 
 * @author Stephen Hunt
 */
public abstract class AI {

    private Sprite mySprite;
    private Game myGame;
    private Player myPlayer;
    
    public AI(Sprite owner, Game theGame, Player thePlayer) {
        mySprite = owner;
        myGame = theGame;
        myPlayer = thePlayer;
    }
    
    /**
     * Passes mySprite and myGame to subclass.
     */
    public void calculate() {
        subCalculate(mySprite, myPlayer, myGame);
    }
    
    /**
     * Looks at the game state, determines a course of action, and updates the
     * Sprite accordingly.
     * @param mySprite the Sprite the AI is acting on.
     * @param myGame the Game the AI is running in.
     */
    public abstract void subCalculate(Sprite mySprite, Player myPlayer, Game myGame);
}
