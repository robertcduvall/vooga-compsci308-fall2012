package games.squareattack.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;


public class OnScreenText {

    private ArrayList<Text> gameText;

    public OnScreenText () {
        gameText = new ArrayList<Text>();

    }

    public void addText (String text, int duration, Point location, int size) {
        gameText.add(new Text(text, duration, location, size));
    }

    public void drawText (Graphics2D g) {
        for (int i = gameText.size() - 1; i >= 0; i--) {
            gameText.get(i).draw(g);
        }
    }

    private class Text {
        private String text;
        private int duration;
        private int curDuration = 0;
        private Point loc;
        private Font font;

        public Text (String text, int duration, Point loc, int size) {
            this.text = text;
            this.duration = duration;
            font = new Font(Font.SANS_SERIF, Font.BOLD, size);
            this.loc = loc;
        }

        public void draw (Graphics2D g) {
            g.setFont(font);
            g.setColor(Color.CYAN);
            g.drawString(text, (int) loc.getX(), (int) loc.getY());
            curDuration++;
            if (curDuration > duration) {
                gameText.remove(this);
            }

        }
    }

    public int getSize () {
        return gameText.size();
    }

}
