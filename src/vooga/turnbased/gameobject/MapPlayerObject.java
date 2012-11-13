package vooga.turnbased.gameobject;

import java.awt.Point;
import javax.swing.ImageIcon;
import vooga.turnbased.gui.GameWindow;

public class MapPlayerObject extends MapObject {

    public MapPlayerObject (int id, Point coord) {
        super(id, coord);
        setImage(GameWindow.importImage("PlayerImage"));
    }
}
