package games.platformerdemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.PlayState;


public class TestLevel extends Level {
    private int numEnemies;
    private Player myPlayer;

    public TestLevel(Dimension dim) {
        super(dim);
        numEnemies = 0;
        myPlayer = null;
    }

    @Override
    public void addGameObject(GameObject go) {
        super.addGameObject(go);
        if (go instanceof Enemy) {
            numEnemies++;
        }
        if (go instanceof Player) {
            myPlayer = (Player) go;
        }
    }

    @Override
    public void paintBackground(Graphics pen) {
        pen.setColor(Color.WHITE);
    }

    @Override
    public PlayState getLevelStatus() {
        if (myPlayer.checkForRemoval()) return PlayState.GAME_OVER;
        if (numEnemies == 0) return PlayState.NEXT_LEVEL;
        return PlayState.IS_PLAYING;
    }
}
