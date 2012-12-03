package util.networking.chat.gui;


import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.net.URL;

public class ExampleGUI {

    static class ChatDialog extends JPanel{
        private JTextArea textArea;

        public ChatDialog(String text){
            textArea = new JTextArea(17, 40);
            textArea.setText(text);
            textArea.setEditable(false);
            textArea.setBackground(new Color(220, 226, 255));
            JScrollPane scrollPane = new JScrollPane(textArea);

            add(scrollPane);
        }

        public JTextArea getTextArea() {
            return textArea;
        }
    }

    static class NewConversationAction extends AbstractAction {
        private JTabbedPane tabbedPane;

        public NewConversationAction(JTabbedPane tabbedPane){
            super("New Conversation");
            this.tabbedPane = tabbedPane;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           
            String userName = JOptionPane.showInputDialog(tabbedPane, "Please enter the name of the user you would like to converse with.", "Start a New Conversation", JOptionPane.QUESTION_MESSAGE);
            if (userName != null){
                if(userName.length() > 7){
                    userName = userName.substring(0,6) + "...";
                }
                tabbedPane.addTab(userName, createImageIcon("images/chat-icon.png"), new ChatDialog(""));
            }
        }
    }
    
    static class CloseConversation extends AbstractAction {
        private JTabbedPane tabbedPane;
        
        public CloseConversation(JTabbedPane tabbedPane){
            super("Close Conversation");
            this.tabbedPane = tabbedPane;
        }
        
        public void actionPerformed(ActionEvent e){
            if(tabbedPane.getTabCount() == 0) return;
            tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
        }
    }
    
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = ExampleGUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Welcome to the World of Chatting!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tab 1", createImageIcon("images/chat-icon2.png"), new ChatDialog("Tab 1 text"));
              
        //tabbedPane.setIconAt(1, createImageIcon("images/chat-icon2.png"));

        frame.add(tabbedPane, BorderLayout.CENTER);
        
        JTextArea buddyList = new JTextArea("Buddy List", 5, 15);
        buddyList.setEditable(false);
        buddyList.setWrapStyleWord(true);
        buddyList.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        frame.add(buddyList, BorderLayout.AFTER_LINE_ENDS);

        JTextArea userInput = new JTextArea("Enter Chat Here", 3, 40);
        userInput.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        userInput.setLineWrap(true);
        userInput.setWrapStyleWord(true);
        //buddyList2.setPreferredSize(new Dimension(3, 40));
        JScrollPane chatInput = new JScrollPane(userInput);
        chatInput.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatInput.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatInput.setAutoscrolls(true);
        chatInput.setMaximumSize(new Dimension(3,10));
        chatInput.setFocusable(true);
        frame.add(chatInput, BorderLayout.AFTER_LAST_LINE);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem newConvoItem = new JMenuItem(new NewConversationAction(tabbedPane));
        JMenuItem closeConvoItem = new JMenuItem(new CloseConversation(tabbedPane));
        menu.add(newConvoItem);
        menu.add(closeConvoItem);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}