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
public class Enemy extends Sprite implements Settings {
	// At some point it's better to use if statements than more subclasses.
	boolean playerHoming;
	boolean pointHoming;
	Point myTargetP;
	Sprite myTargetS;

	public Enemy(Point center, Dimension size, double angle) {
		super(center, size, angle);
		setImage("ram-color.gif");
		playerHoming = false;
		pointHoming = false;
		setDamage(25);
		setHealth(100);

		// TODO Auto-generated constructor stub
	}

	public Enemy(Point center, Dimension size, double angle, Point target) {
		super(center, size, angle);
		setImage("ram-color.gif");
		playerHoming = false;
		pointHoming = true;
		myTargetP = target;
		setDamage(25);
		setHealth(100);
	}

	public Enemy(Point center, Dimension size, double angle, Sprite target) {
		super(center, size, angle);
		setImage("ram-color.gif");
		playerHoming = true;
		pointHoming = false;
		myTargetS = target;
		setDamage(25);
		setHealth(100);
	}

	private void setVelocity() {
		dx = (int) (Math.cos(myAngle) * ENEMY_SPEED);
		dy = (int) (Math.sin(myAngle) * ENEMY_SPEED);
	}

	public void update(TGame game) {
		if (getHealth() < 1)
			die(game);
		if (playerHoming)
			look(myTargetS.getCenter());
		else if (pointHoming) {
			inCameron(game);
			look(myTargetP);
		}
		setVelocity();
		move();
		for (Sprite s : game.gameSprites) {
			if (s.equals(this))
				continue;

			if (s.intersects(myCenter))
				if (!s.getClass().equals(this.getClass()))
					s.setHealth(s.getHealth() - getDamage());
				else
					s.bumpAway(getCenter());
		}
	}

	private void inCameron(TGame game) {
		if (getCenter().x > CAMERON.x && getCenter().y > CAMERON.y) {
			game.setGameState(5);
		}
	}

	@Override
	public boolean enemyInCameron() {
		if (getCenter().x > CAMERON.x && getCenter().y > CAMERON.y) {
			return true;
		}
		return false;
	}

}
