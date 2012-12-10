package games.squareattack.controllers;

import games.squareattack.sprites.Square;

public abstract class ControllerStrategy {
    
    private Square myTarget;

    public ControllerStrategy () {
       
    }
    
    public abstract void setControls();

    
    
    public Square getTarget(){
        return myTarget;
    }
    public void setTarget(Square target){
        System.out.println("resseting target..");
        myTarget = target;
        setControls();
    }



   

}
