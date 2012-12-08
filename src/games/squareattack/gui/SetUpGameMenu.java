package games.squareattack.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;


/**
 * 
 * @author Ben Schwab
 * 
 */
@SuppressWarnings("serial")
public class SetUpGameMenu extends JPanel implements ActionListener {

    private GameFrame myParent;
    JComboBox controllerListOne;
    JComboBox controllerListTwo;
    JComboBox controllerListThree;
    JComboBox controllerListFour;
    Image myBackgroundImage;

    public SetUpGameMenu (GameFrame parent) {
        myParent = parent;
        ImageIcon myImage = new ImageIcon("src/games/squareattack/squareattack.png");
        myBackgroundImage = myImage.getImage();
        String[] controllerOptionsStringsAttacker = { "Android" };
        String[] controllerOptionsStringsDefender = { "Android", "Keyboard_WASD","Keyboard_Arrows" };
        String[] controllerOptionsStringsDefenderExtra = { "Android", "Keyboard_Arrows", "Keyboard_WASD", "None" };

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel buttonPane = new JPanel(){
        
        @Override
        public void paintComponent (Graphics g) {
            super.paintComponent(g);
            if (myBackgroundImage != null) {
                g.drawImage(myBackgroundImage, 0, 0, GameFrame.GameWidth, GameFrame.GameHeight, 0, 0,
                           myBackgroundImage.getWidth(null), myBackgroundImage.getHeight(null), null);
                //g.drawImage(myBackgroundImage, 0, 0, this);
            }

        }};
        
        controllerListOne = new JComboBox(controllerOptionsStringsAttacker);
        controllerListOne.setSelectedIndex(0);

        buttonPane.add(controllerListOne);
        controllerListTwo = new JComboBox(controllerOptionsStringsDefender);
        controllerListTwo.setSelectedIndex(0);

        buttonPane.add(controllerListTwo);
        controllerListThree = new JComboBox(controllerOptionsStringsDefenderExtra);
        controllerListThree.setSelectedIndex(3);
        buttonPane.add(controllerListThree);
        this.add(buttonPane);
        controllerListFour = new JComboBox(controllerOptionsStringsDefenderExtra);
        controllerListFour.setSelectedIndex(3);

        buttonPane.add(controllerListFour);
        //this.add(Box.createRigidArea(new Dimension(0,5)));
        this.add(buttonPane, BorderLayout.CENTER);
        JButton startButton = new JButton();
        this.add(startButton, BorderLayout.SOUTH);
        startButton.setText("Start Game");
        startButton.addActionListener(this);
        this.setFocusable(true);

    }

    @Override
    public void actionPerformed (ActionEvent arg0) {
        String[] controllerConfig = new String[4];
        controllerConfig[0] = controllerListOne.getSelectedItem().toString();
        controllerConfig[1] = controllerListTwo.getSelectedItem().toString();
        controllerConfig[2] = controllerListThree.getSelectedItem().toString();
        controllerConfig[3] = controllerListFour.getSelectedItem().toString();
        myParent.startGame(controllerConfig);

    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        if (myBackgroundImage != null) {
            g.drawImage(myBackgroundImage, 0, 0, GameFrame.GameWidth, GameFrame.GameHeight, 0, 0,
                       myBackgroundImage.getWidth(null), myBackgroundImage.getHeight(null), null);
            //g.drawImage(myBackgroundImage, 0, 0, this);
        }

    }
}
