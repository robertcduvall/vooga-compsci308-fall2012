package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import vooga.turnbased.gamecore.gamemodes.MapMode;


public class MapEnemyObject extends MapObject {

    public MapEnemyObject (String condition, Point location, Image mapImage) {
        super(condition, location, mapImage);
    }

    @Override
    public void interact (MapObject m) {
        super.interact(m);
        if (m instanceof MapPlayerObject) {
            List <Integer> involvedSpriteIDs = new ArrayList<Integer>();
            involvedSpriteIDs.add(m.getID());
            involvedSpriteIDs.add(getID());
            getMapMode().flagCondition(getConditionFlag(), involvedSpriteIDs);
        }
    }

}
