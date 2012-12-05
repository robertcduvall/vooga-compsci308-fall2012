package vooga.platformer.core.inputinitializer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.gameobject.Player;

/**
 * Use KeyListeners to implement user input. Deprecated, but useful as an example if you
 * want to set up event listeners yourself.
 * @author original code probably by Yaqi
 * @author refactored into an InputInitializer class by Niel Lebeck
 * @deprecated
 *
 */
public class SimpleOnePlayerInputInitializer implements InputInitializer {

    Player myPlayer;

    
    /**
     * Assumes there is exactly one Player instance in the list of GameObjects.
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
        
        KeyListener kl = new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_LEFT){
                    myPlayer.fireControlStrategy("GoLeft");
                }
                if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                    myPlayer.fireControlStrategy("GoRight");
                }
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    myPlayer.fireControlStrategy("Jump");
                }
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    myPlayer.fireControlStrategy("Shoot");
                }
            }

            public void keyReleased (KeyEvent e) {
                myPlayer.fireControlStrategy("Stop");

            }
        };
        MouseMotionListener mml = new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                // not used so far
            }
        };
        platformControl.addKeyListener(kl);
    }

}
