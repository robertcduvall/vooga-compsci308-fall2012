package vooga.turnbased;
/**
 * Main entrance of the game
 * @author Rex
 */
import vooga.turnbased.gui.GameWindow;

class Main {
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    /**
     * static main method
     * @param args arguments
     */
    public static void main(String[] args) {  
        GameWindow myGameWindow = new GameWindow(WIDTH, HEIGHT, "Scrolling Games");
        myGameWindow.addResourceBundle("GameSetting");
        myGameWindow.initializeCanvas();
    }
}