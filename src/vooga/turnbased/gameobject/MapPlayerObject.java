package vooga.turnbased.gameobject;

import java.awt.Point;
import javax.swing.ImageIcon;

public class MapPlayerObject extends MapObject {

    public MapPlayerObject (Point coord) {
        super(coord);
        setImage(new ImageIcon("src/vooga/turnbased/resources/image/player.png").getImage());
    }
}
