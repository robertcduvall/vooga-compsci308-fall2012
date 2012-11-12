package vooga.turnbased.gameobject.map;

import java.awt.Point;
import javax.swing.ImageIcon;

public class MapPlayer extends MapSprite {

    public MapPlayer (Point coord) {
        super(coord);
        setImage(new ImageIcon("src/vooga/turnbased/resources/image/player.png").getImage());
    }
}
