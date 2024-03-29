package util.input.configurecontrollergui.view;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import util.input.configurecontrollergui.controller.RadioButtonController;

/**
 * View focused on providing radio button
 * style flexibility. This class was primarily taken
 * from the official Java example website.
 * 
 * @author Lance, Amay
 *
 */
public class RadioButtonView extends JPanel
                             implements ActionListener {

    private static RadioButtonController myRadioController;
    private static String radiobuttonPressed;

    /**
     * Initializing a radio button view.
     * @param position - position of the view.
     * @param size - size of the view.
     * @param radioButtonController - radio button controller.
     */
    public RadioButtonView(RadioButtonController rbc) {
        super(new BorderLayout());
        myRadioController = rbc;
        
        ButtonGroup group = new ButtonGroup();
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        
        //Create the radio buttons.
        for(String item: myRadioController.getData() ) {
            JRadioButton rButton = new JRadioButton(item);
            rButton.setActionCommand(item);
            group.add(rButton);
            rButton.addActionListener(this);
            radioPanel.add(rButton);
        }
        
 
        add(radioPanel, BorderLayout.LINE_START);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }
 
    /** Listens to the radio buttons. */
    public void actionPerformed(ActionEvent e) {
        for(String buttonDesc: myRadioController.getButtonDescriptions()) {
            if(e.paramString().contains(buttonDesc)) {
                radiobuttonPressed = buttonDesc;
            }
        }
        System.out.println(radiobuttonPressed);
    }
    
    public static String getRadioButtonPressed() {
        return radiobuttonPressed;
    }
 
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Available Control Buttons");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        JComponent newContentPane = new RadioButtonView(myRadioController);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
}