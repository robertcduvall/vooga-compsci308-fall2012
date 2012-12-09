package games.squareattack.controllers;

import games.squareattack.sprites.Square;

public abstract class ControllerStrategy {
    
    private Square myTarget;

    public ControllerStrategy () {
       
    }
    
    public abstract void setControls();

    public void replaceTarget (Square s) {
       
        
    }
    
    
    public Square getTarget(){
        return myTarget;
    }
    public void setTarget(Square target){
        myTarget = target;
        setControls();
    }



   

}
