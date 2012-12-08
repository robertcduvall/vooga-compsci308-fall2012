package games.squareattack.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ben Schwab
 *
 */
public class MenuBar {
    
    private Rectangle myBounds;
    private List<GameMenuComponent> myComponents;
    
    public MenuBar(Rectangle bounds){
        myComponents = new ArrayList<GameMenuComponent>();
        myBounds = bounds;
    }
    
    public void draw(Graphics2D pen){
        pen.setColor(Color.WHITE);
        pen.fillRect(myBounds.x, myBounds.y, myBounds.width, myBounds.height);
        for(GameMenuComponent g: myComponents){
            g.paint(pen);
        }
    }
    
    public void addComponent(GameMenuComponent componentToAdd){
        myComponents.add(componentToAdd);
    }
    

}
