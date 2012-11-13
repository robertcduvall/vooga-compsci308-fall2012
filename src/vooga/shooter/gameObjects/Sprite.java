package vooga.shooter.gameObjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;

/**
 * This class encompasses the basic layout for any sprites that appear in the
 * game. Any specific type of sprite (e.g. player, enemy, boss, etc.) will
 * extend this class and contain any additional information or behaviors
 * particular to that new type of sprite (e.g. the player could have a health
 * limit)
 * 
 * @author David Spruill
 * 
 */
public abstract class Sprite {
    public abstract void draw(Graphics g);
    public abstract void update();
    public abstract void delete();
    public abstract Point getPosition();
    public abstract void setPosition(Point position);
    public abstract Point getDirection();
    public abstract void setDirection(Point direction);
    public abstract Image getImage();
    public abstract void setImage(Image image);
    public abstract List<Sprite> collisions();
}
