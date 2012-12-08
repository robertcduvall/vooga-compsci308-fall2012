package games.robsgame;
import java.awt.Dimension;
import java.awt.Point;
/**
 * 
 * @author Robert Bruce
 * This code isn't the best & is from the first assignment.
 * I just wanted to add it to the arcade so we had more
 * games to play.
 */
public class Projectile extends Sprite implements Settings {

	public Projectile(Point center, Dimension size, double angle) {
		super(center, size, angle);
		setImage("blueBall.png");
		setVelocity();
		setDamage(25);
		setHealth(100);
	}

	private void setVelocity() {
		dx = (int) (Math.cos(myAngle) * PROJECTILE_SPEED);
		dy = (int) (Math.sin(myAngle) * PROJECTILE_SPEED);
	}

	public void update(TGame game) {
		look(getAngle());
		move();
		for (Sprite s : game.gameSprites) {
			if (WINDOW_SIZE.height < getCenter().y || 0 > getCenter().y
					|| WINDOW_SIZE.width < getCenter().x || 0 > getCenter().x)
				die(game);
			if (s.equals(this) || s.equals(game.user))
				continue;
			if (s.intersects(myCenter))
				s.setHealth(s.getHealth() - getDamage());
		}
	}

}
