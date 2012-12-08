package games.squareattack.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class PowerBar extends GameMenuComponent {
    
    private Rectangle myBoundingBox;
    private int powerLevel;
    
    public PowerBar(Rectangle boundingBox){
        myBoundingBox = boundingBox;
    }
    
    public void setCorner(Point p){
        myBoundingBox = new Rectangle(p.x,p.y, myBoundingBox.width, myBoundingBox.height);
    }

    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(Color.LIGHT_GRAY);
        pen.fillRect(myBoundingBox.x, myBoundingBox.y, myBoundingBox.width, myBoundingBox.height);  
        pen.setColor(Color.RED);
        pen.fillRect(myBoundingBox.x, myBoundingBox.y, myBoundingBox.width*powerLevel/100, myBoundingBox.height);
    }
    
    public void addPower(int newPower){
        powerLevel+=newPower;
        if(powerLevel>100){
            powerLevel = 100;
        }
    }
    public boolean usePower(int powerCost){
        if(powerLevel-powerCost>=0){
            powerLevel-=powerCost;
            return true;
        }
        return false;
        
    }

}
