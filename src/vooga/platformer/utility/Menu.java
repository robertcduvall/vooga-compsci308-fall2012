package src.vooga.platformer.utility;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import javax.swing.JComponent;


/**
 * @author Yaqi Zhang
 * @author Xi Du
 */
public class Menu extends JComponent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final double MENU_SIZE_RATIO = 0.66;
    private Map<String, GameButton> myButtonMap;

    /**
     * @param gameCanvas 
     */
    public Menu (JComponent gameCanvas) {
        Dimension gameSize = gameCanvas.getSize();
        Dimension size = new Dimension(
                (int) (gameSize.width * MENU_SIZE_RATIO),
                (int) (gameSize.height * MENU_SIZE_RATIO));
    }

    /**
     * @param command name of this button
     * @param ml MouseListener to listen to this button.
     */
    public void addButtons (String command, MouseListener ml) {

    }

    /**
     * @param ratio of the size of the menu to that of game canvas.
     */
    public void setRatio (double ratio) {

    }

    /**
     * @param keyValue the key associate to the button. eg. KeyEvent.VK_N gives
     *        the int number of key N.
     * @param button will be counted as pressed when player pressing this key
     */
    public void addHotKey (int keyValue, Button button) {

    }
}
