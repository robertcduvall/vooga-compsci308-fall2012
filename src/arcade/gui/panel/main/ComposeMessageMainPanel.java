package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.components.HintTextField;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Robert Bruce
 * 
 */
public class ComposeMessageMainPanel extends AMainPanel {

    private JTextArea reviewArea;
    private HintTextField recipientField;
    private JLabel headerLabel;
    private Dimension maxMessageBoxSize = new Dimension(600, 300);
    boolean messageSent = false;

    public ComposeMessageMainPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        headerLabel = new JLabel();
        headerLabel.setText("Compose Message To: ");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setVerticalTextPosition(JLabel.CENTER);
        headerLabel.setHorizontalTextPosition(JLabel.CENTER);

        recipientField = new HintTextField("Username");
        recipientField.setPreferredSize(new Dimension(200, 20));
        if(getArcade().getVariable("UserName")!=null) {
            recipientField.setText((String) getArcade().getVariable("UserName"));
        }

        JLabel reviewPrompt = new JLabel("Write a review/comment:");
        reviewArea = new JTextArea("", 20, 60);
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        reviewArea.setMaximumSize(maxMessageBoxSize);
        JScrollPane scrollingReview = new JScrollPane(reviewArea);

        JButton submitBut = new JButton("Submit");
        submitBut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("Attempting to send message:");
                if (reviewArea.getText() != null && reviewArea.getText() != "" &&
                        recipientField.getText() != null && recipientField.getText() != "") {
                    messageSent = getArcade().getModelInterface().sendMessage(getArcade().getUsername(),
                            recipientField.getText(), reviewArea.getText());
                }
                System.out.println(recipientField.getText());
                System.out.println(reviewArea.getText());
                if (messageSent) {
                    getArcade().replacePanel("MessageCenter");
                }
                else {
                    headerLabel.setText("Compose Message To: MESSAGE SEND FAILED." +
                    		" Check the spelling of recipient's username.");
                }
            }

        });

        MigLayout layout = new MigLayout("flowy", "[]push[]");
        myPanel.setLayout(layout);

        myPanel.add(headerLabel, "span, grow, align center");
        myPanel.add(submitBut, "dock south, span, grow, align center");
        myPanel.add(reviewPrompt, "cell 2 1");
        myPanel.add(scrollingReview, "cell 2 2");

        return myPanel;
    }

}
