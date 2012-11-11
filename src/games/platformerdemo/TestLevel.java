package games.platformerdemo;

import java.awt.Color;
import java.awt.Graphics;
import vooga.platformer.level.Level;
import vooga.platformer.util.enums.PlayState;

public class TestLevel extends Level {

    @Override
    public void paintBackground (Graphics pen) {
        pen.setColor(Color.WHITE);
    }

    @Override
    public void initializeCamera () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public PlayState getLevelStatus () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNextLevelName () {
        // TODO Auto-generated method stub
        return null;
    }

}
