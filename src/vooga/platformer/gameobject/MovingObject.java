package vooga.platformer.gameobject;

public abstract class MovingObject extends GameObject {
    private int vx;
    private int vy;
    
    @Override
    public void update(long elapsedTime) {
        super.update(elapsedTime);
        setX((int)(vx * (elapsedTime/1000)) + getX());
        setY((int)(vy * (elapsedTime/1000)) + getY());
    }
}
