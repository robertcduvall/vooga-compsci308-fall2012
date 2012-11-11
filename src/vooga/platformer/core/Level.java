package vooga.platformer.core;

import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;
import java.awt.Graphics;
import java.util.List;

public abstract class Level {
    private List<GameObject> objectList;
    
    public void paint(Graphics pen) {
        for (GameObject go : objectList) {
            go.paint(pen, getCamera());
        }
    }
    
    public void update(long elapsedTime) {
        for (GameObject go : objectList) {
            go.update(this, elapsedTime);
        }
    }
    
    public abstract Camera getCamera();
    
    public abstract String getLevelStatus();
}
