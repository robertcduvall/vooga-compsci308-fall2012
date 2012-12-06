package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import vooga.platformer.gameobject.GameObject;


/**
 * A pop up menu that allows users to edit the characteristics of GameObjects.
 * 
 * @author Sam Rang
 * 
 */
@SuppressWarnings("serial")
public class GameObjectEditor extends JPopupMenu {

    /**
     * Opens a new pop up editor for the specified GameObject.
     * 
     * @param obj GameObject to be modified
     */
    public GameObjectEditor (final GameObject obj) {
        // Top panel gets image (eventually animation?)
        JPanel myTop = new JPanel();
        myTop.setLayout(new BorderLayout());
        JLabel myImage = new JLabel();
        myImage.setIcon(new ImageIcon(obj.getCurrentImage()));
        myTop.add(myImage, BorderLayout.CENTER);
        JLabel prompt = new JLabel("Please enter Doubles:");
        myTop.add(prompt, BorderLayout.SOUTH);
        add(myTop);

        // Bottom panel has fields and buttons
        JPanel myBottom = new JPanel();
        myBottom.setLayout(new GridLayout(6, 2));
        JLabel label;
        JTextField textField;
        final List<JTextField> fields = new ArrayList<JTextField>();
        // X
        label = new JLabel("X position");
        textField = new HintTextField(((Double) obj.getX()).toString());
        fields.add(textField);
        myBottom.add(label);
        myBottom.add(textField);

        // Y
        label = new JLabel("Y position");
        textField = new HintTextField(((Double) obj.getY()).toString());
        fields.add(textField);
        myBottom.add(label);
        myBottom.add(textField);

        // Width
        label = new JLabel("Width");
        textField = new HintTextField(((Double) obj.getWidth()).toString());
        fields.add(textField);
        myBottom.add(label);
        myBottom.add(textField);

        // Height
        label = new JLabel("Height");
        textField = new HintTextField(((Double) obj.getHeight()).toString());
        fields.add(textField);
        myBottom.add(label);
        myBottom.add(textField);

        // Image
        label = new JLabel("Image");
        JButton choose = new JButton("Choose Image");
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                FileNameExtensionFilter filter =
                        new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif", "png");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(chooser);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        obj.setImage(ImageIO.read(chooser.getSelectedFile()));
                    }
                    catch (IOException e1) {
                        showError("file does not exist");
                        e1.printStackTrace();
                    }
                }
            }
        });
        myBottom.add(label);
        myBottom.add(choose);

        JButton accept = new JButton("Accept");
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                try {
                    double x = Double.parseDouble(fields.get(0).getText());
                    double y = Double.parseDouble(fields.get(1).getText());
                    double w = Double.parseDouble(fields.get(2).getText());
                    double h = Double.parseDouble(fields.get(3).getText());
                    obj.setX(x);
                    obj.setY(y);
                    obj.setSize(w, h);
                    GameObjectEditor.this.setVisible(false);
                }
                catch (NumberFormatException nf) {
                    showError("Could not parse values");
                }

            }
        });
        JButton addStrategy = new JButton("Add Strategy");
        addStrategy.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                System.out.println("add strategy");
                // TODO
            }

        });
        myBottom.add(addStrategy);
        myBottom.add(accept);
        add(myBottom);
    }

    protected void showError (String msg) {
        JLabel errormsg = new JLabel(msg);
        errormsg.setForeground(Color.RED);
        add(errormsg, FlowLayout.CENTER);
        pack();
        repaint();
    }

    class HintTextField extends JTextField implements FocusListener {

        private final String myHint;

        public HintTextField (final String hint) {
            super(hint);
            myHint = hint;
            super.addFocusListener(this);
        }

        @Override
        public void focusGained (FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText("");
            }
        }

        @Override
        public void focusLost (FocusEvent e) {
            if (this.getText().isEmpty()) {
                super.setText(myHint);
            }
        }

        @Override
        public String getText () {
            String typed = super.getText();
            System.out.println(typed.equals(myHint) ? "" : typed);
            return typed.equals(myHint) ? myHint : typed;
        }
    }
}
