package vooga.platformer.gameobject;

import games.platformerdemo.GravityStrategy;
import games.platformerdemo.PlayerMoveStrategy;
import games.platformerdemo.ShootingStrategy;

import java.util.Collections;

/**
 * @author Yaqi Zhang
 * @author Zach Michaelov
 */
public class Player extends MovingObject {
    /**
     * @param configString
     */
    public Player(String configString) {
        super(configString);
//        addStrategy(new PlayerMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
        addStrategy(new ShootingStrategy(this));
    }

    /**
     *
     * @return
     */
    public PlayerMoveStrategy getMovingStrategy(){

        for (UpdateStrategy s: getStrategyList()){
            if(s instanceof PlayerMoveStrategy){
                return (PlayerMoveStrategy) s;
            }
        }
        return null;
    }
    
    public ShootingStrategy getShootingStrategy(){
        for (UpdateStrategy s: getStrategyList()){
            if(s.getClass() == ShootingStrategy.class){
                return (ShootingStrategy) s;
            }
        }
        return null;
    }
}
