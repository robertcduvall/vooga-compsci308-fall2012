package vooga.platformer.gameobject;

import vooga.platformer.level.Level;


public abstract class MovingObject extends GameObject {
    private int vx;
    private int vy;

    @Override
    public void update(Level level, long elapsedTime) {
        super.update(level, elapsedTime);
        setX((int) (vx * (elapsedTime / 1000)) + getX());
        setY((int) (vy * (elapsedTime / 1000)) + getY());
    }
}
