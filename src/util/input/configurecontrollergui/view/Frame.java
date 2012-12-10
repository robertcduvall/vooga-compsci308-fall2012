package util.input.configurecontrollergui.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

/**
 * This class serves as the
 * highest level JComponent holding
 * all the other portions of the
 * configure controller gui together. 
 * 
 * @author Lance Co Ting Keh
 */
public class Frame extends JComponent {
    private ResponsiveView myTopLevelView;

    /**
     * Initializes the main window screen.
     * @param size of the canvas
     */
    public Frame (Dimension size) {
        setPreferredSize(size);
        setFocusable(true);
        requestFocus();
        Point2D rootPosition = new Point2D.Double(0, 0);
        Dimension rootSize = new Dimension(getWidth() , getHeight());
        myTopLevelView = new ResponsiveView(rootPosition, rootSize);
        repaint();
        initListener();
    }

    /*
     * calls paint on the top level view entity.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D pen = (Graphics2D) g;
        myTopLevelView.paint(pen);
    }

    /**
     * Adds a view to the Top Level View.
     *
     * @param view the view to be added
     */
    public void addView(View v) {
        myTopLevelView.addViewChild(v);
    }

    /**
     * Updates the views of the Frame
     */
    public void update() {
        repaint();
    }

    /**
     * @return the root view
     */
    public View getRoot() {
        return myTopLevelView;
    }

    /**
     * Initializes a listener to listen for mouse
     * events.
     */
    private void initListener() {
        addMouseListener(new MouseListener() {
            @Override
            public void mousePressed (MouseEvent e) {
            }

            @Override
            public void mouseClicked (MouseEvent e) {
                myTopLevelView.mouseClicked(e.getPoint());
            }

            @Override
            public void mouseEntered (MouseEvent e) {
            }

            @Override
            public void mouseExited (MouseEvent e) {
            }

            @Override
            public void mouseReleased (MouseEvent e) {
            }
        });
    }
}
