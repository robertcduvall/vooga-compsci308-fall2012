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
        System.out.println("debug2");
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
        System.out.println("debug2");
        pen.setColor(Color.WHITE);
        pen.drawRect(0, 0, (int)getDimension().getWidth(), (int)getDimension().getHeight());
    }

    @Override
    public PlayState getLevelStatus() {
        if (myPlayer.checkForRemoval()) return PlayState.GAME_OVER;
        if (numEnemies == 0) return PlayState.NEXT_LEVEL;
        return PlayState.IS_PLAYING;
    }
}
