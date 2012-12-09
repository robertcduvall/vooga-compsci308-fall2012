package arcade.gui.panel.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
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

    private JTextArea messageArea;
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

        JLabel messagePrompt = new JLabel("Write your message here:");
        messagePrompt.setForeground(Color.WHITE);
        messageArea = new JTextArea("", 20, 60);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setMaximumSize(maxMessageBoxSize);
        JScrollPane scrollingMessageArea = new JScrollPane(messageArea);

        JButton submitBut = new JButton("Send");
        submitBut.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed (ActionEvent arg0) {
                System.out.println("Attempting to send message:");
                if (messageArea.getText() != null && messageArea.getText() != "" &&
                        recipientField.getText() != null && recipientField.getText() != "") {
                    messageSent = getArcade().getModelInterface().sendMessage(getArcade().getUsername(),
                            recipientField.getText(), messageArea.getText(), new Date());
                }
                System.out.println(recipientField.getText());
                System.out.println(messageArea.getText());
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

        myPanel.add(headerLabel, "align left, split 2");
        myPanel.add(recipientField);
        myPanel.add(messagePrompt);
        myPanel.add(scrollingMessageArea, "align left");
        myPanel.add(submitBut, "dock south, span, grow, align center");

        return myPanel;
    }

}
