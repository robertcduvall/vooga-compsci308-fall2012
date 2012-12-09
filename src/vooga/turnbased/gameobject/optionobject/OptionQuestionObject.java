package vooga.turnbased.gameobject.optionobject;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.OptionMode;


public class OptionQuestionObject extends OptionObject {
    private int FONT_SIZE = 24;
    private int QUESTION_OFFSET = 30;
    
    public OptionQuestionObject (Set<String> allowableModes, String condition, String message) {
        super(allowableModes, condition, message);
    }

    public OptionQuestionObject (Set<String> allowableModes, String condition, Image image,
                                  List<String> message) {
        super(allowableModes, condition, message.get(0));
    }
    
    @Override
    public void paint (Graphics g) {
        Font optionFont = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        g.setFont(optionFont);
        g.drawString(getMessage(), getPosition().x, getPosition().y - QUESTION_OFFSET);
    }
    
    @Override
    public void executeOption (OptionMode myOptionMode) {
        // do nothing, just display question!
    }
    
    @Override 
    public boolean highlight (Point focusPosition) {
        // dont highlight question
        return false;
    }
    
    @Override 
    public boolean isTriggered (Point focusPosition) {
        // question doesn't respond to input
        return false;
    }
}
