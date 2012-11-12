package vooga.shooter.gameObjects;

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
    public void draw(Graphics g);
    public void update();
    public void delete();
    public Point getPosition();
    public void setPosition(Point position);
    public Point getDirection();
    public void setDirection(Point direction);
    public Image getImage();
    public void setImage(Image image);
    public List<Sprite> collisions();
}
