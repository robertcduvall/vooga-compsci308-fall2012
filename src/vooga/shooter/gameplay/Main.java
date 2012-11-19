package vooga.shooter.gameplay;

import vooga.shooter.gameplay.Game;

public class Main {

    public static void main(String [] args) {
        Game myGame = new Game(new Applet());
        myGame.runGame("", null);
    }
    
}
