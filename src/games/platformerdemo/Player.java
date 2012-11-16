package games.platformerdemo;

import vooga.platformer.gameobject.UpdateStrategy;

/**
 * @author Yaqi Zhang
 *
 */
public class Player extends MovingObject {
    /**
     * @param configString
     */
    public Player (String configString) {
        super(configString, "player");
        addStrategy(new PlayerMoveStrategy(this));
        addStrategy(new GravityStrategy(this));
    }
    
//    public void moveLeft() {
//        System.out.println("detected left input");
//    }
    
    public PlayerMoveStrategy getMovingStragety(){
        for (UpdateStrategy s: getStrategyList()){
            if(s.getClass() == PlayerMoveStrategy.class){
                return (PlayerMoveStrategy) s;
            }
        }
        return null;
    }
}
