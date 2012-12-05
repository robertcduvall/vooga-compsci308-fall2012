package vooga.platformer.core.inputinitializer;

import java.awt.event.KeyEvent;
import java.util.List;
import util.input.core.KeyboardController;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;

/**
 * Uses Input Team's API to set up user input. This code will not work unless the Player class
 * has zero-parameter methods with the names given in the keyControl.setControl() calls below.
 * @author Niel Lebeck
 *
 */
public class KeyControllerOnePlayerInputInitializer implements InputInitializer {

    Player myPlayer;
    InputTeamPlayerAdapter adapter;
    
    
    /**
     * Assumes there is exactly one Player instance in the list of GameObjects.
     * Uses KeyboardController from Input team.
     */
    @Override
    public void setUpInput (List<GameObject> objects,
            PlatformerController platformControl) {
        myPlayer = null;
        for (GameObject go : objects) {
            if (go instanceof Player) {
                myPlayer = (Player)go;
            }
        }
        adapter = new InputTeamPlayerAdapter(myPlayer);
        
        KeyboardController keyControl = new KeyboardController(platformControl);
        try {
            keyControl.setControl(KeyEvent.VK_UP, KeyboardController.PRESSED, adapter, "goUp");
            keyControl.setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED, adapter, "goLeft");
            keyControl.setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED, adapter, "goRight");
            keyControl.setControl(KeyEvent.VK_SPACE, KeyboardController.PRESSED, adapter, "shoot");
            keyControl.setControl(KeyEvent.VK_UP, KeyboardController.RELEASED, adapter, "stop");
            keyControl.setControl(KeyEvent.VK_LEFT, KeyboardController.RELEASED, adapter, "stop");
            keyControl.setControl(KeyEvent.VK_RIGHT, KeyboardController.RELEASED, adapter, "stop");
            keyControl.setControl(KeyEvent.VK_SPACE, KeyboardController.RELEASED, adapter, "stop");
        }
        catch (NoSuchMethodException e) {
            System.out.println("Error initializing KeyboardController: no such method");
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            System.out.println("Error initializing KeyboardController: illegal access");
            e.printStackTrace();
        }
    }
}