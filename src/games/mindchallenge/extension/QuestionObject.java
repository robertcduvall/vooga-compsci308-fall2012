package games.mindchallenge.extension;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gameobject.GameObject;


public class QuestionObject extends GameObject {
    private final int QUESTION_FONT_SIZE = 32;
    private final int ANSWER_FONT_SIZE = 24;
    private final int MESSAGE_INDEX = 0;
    private final int ANSWER_FLAG_INDEX = 1;
    private final int MOUSE_PRESSED_CODE = 0;
    private String myMessage;
    private boolean isAnswer;
    private Rectangle myMouseTargetBox;

    public QuestionObject (Set<String> allowableModes, String condition, Image image) {
        super(allowableModes, condition, image);
    }

    public QuestionObject (Set<String> allowableModes, String condition, Image image,
                           List<String> params) {
        this(allowableModes, condition, image);
        myMessage = params.get(MESSAGE_INDEX);
        isAnswer = Integer.parseInt(params.get(ANSWER_FLAG_INDEX)) == 1;
        setMouseTargetBox(new Rectangle());
    }

    @Override
    public void update () {

    }

    @Override
    public void paint (Graphics g) {
        Font f;
        if (isAnswer) {
            f = new Font("sansserif", Font.PLAIN, ANSWER_FONT_SIZE);
        }
        else {
            f = new Font("sansserif", Font.BOLD, QUESTION_FONT_SIZE);
        }
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics(f);
        Rectangle2D rect = fm.getStringBounds(myMessage, g);
        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());
        int x = (myMouseTargetBox.width - textWidth) / 2;
        int y = (myMouseTargetBox.height - textHeight) / 2 + fm.getAscent();
        g.drawString(myMessage, myMouseTargetBox.x + x, myMouseTargetBox.y + y);
    }

    @Override
    public void clear () {

    }

    /**
     * Process mouse input
     * 
     * @param mousePressed pressed, dragged, or released
     * @param mousePosition event position
     * @param questionMode parent mode
     */
    public void processMouseInput (int mousePressed, Point mousePosition, QuestionMode questionMode) {
        if (myMouseTargetBox.contains(mousePosition) && mousePressed == MOUSE_PRESSED_CODE) {
            List<Integer> involvedIDs = new ArrayList<Integer>();
            involvedIDs.add(getID());
            involvedIDs.add(questionMode.getPlayerID());
            questionMode.flagCondition(getConditionFlag(), involvedIDs);
            questionMode.setModeIsOver();
        }
    }

    /**
     * Set where list item would be printed, and where mouse events would be caught
     * @param targetBox
     */
    public void setMouseTargetBox (Rectangle targetBox) {
        myMouseTargetBox = targetBox;
    }
}
