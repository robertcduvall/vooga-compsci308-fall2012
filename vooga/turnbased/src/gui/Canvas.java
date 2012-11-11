/**
 * Abstract Canvas
 * GameCanvas, MenuCanvas and EditorCanvas share some simular methods
 * @author Volodymyr
 */
package vooga.turnbased.src.gui;

import javax.swing.JPanel;

public abstract class Canvas extends JPanel {
    abstract public void initialize ();
}
