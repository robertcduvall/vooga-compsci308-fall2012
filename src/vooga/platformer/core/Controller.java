package vooga.platformer.core;

public class Controller {
    private Level myCurrentLevel;
    
    public void update() {
        myCurrentLevel.update();
    }
}
