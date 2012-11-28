package vooga.turnbased.gamecore;

import java.awt.Graphics;


/**
 * All members of gameloop implement update () and paint (g)
 */
public interface GameLoopMember {

    public void update ();

    public void paint (Graphics g);

}
