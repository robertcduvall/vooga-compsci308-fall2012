package games.squareattack.controllers;

public abstract class ControllerStrategy {

    public ControllerStrategy () {
       // setControls();
    }

    /**
     * Implement this method to automatically configure the controls of your
     * strategy on the creation of the strategy.
     */
    public abstract void setControls ();

   

}
