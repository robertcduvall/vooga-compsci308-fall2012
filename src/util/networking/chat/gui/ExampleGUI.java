package util.networking.chat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import util.networking.chat.ChatClient;


public class ExampleGUI extends JPanel {
    
    
    
    private ChatClient myChatClient; 
    private String myUser;
    private JTabbedPane tabbedPane;
    private JTextArea buddyList;
    private JTextArea userInput;
    private Map<String, JTextArea> recipientsToTextArea;
    
    public ExampleGUI(/*ChatClient c,*/ String userName){
        myChatClient = null;
        myUser = userName;
        recipientsToTextArea = new TreeMap<String, JTextArea>();
        setUpChatGUI();
    }
        
    private void setUpChatGUI () {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Welcome", getWelcomePage());
        add(tabbedPane, BorderLayout.CENTER);
        add(initBuddyList(), BorderLayout.AFTER_LINE_ENDS);
        add(initChatInput(), BorderLayout.AFTER_LAST_LINE);
    }

     private JTextArea initBuddyList () {
        buddyList = new JTextArea("Buddy List", 5, 15);
        buddyList.setEditable(false);
        buddyList.setWrapStyleWord(true);
        buddyList.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        return buddyList;
    }
     
    protected void newConversation(String userName){
        if (userName != null) {
            tabbedPane.addTab(userName, createImageIcon("images/chat-icon.png"),
                              new ChatDialog(""));
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        }    
    }
    
    protected void closeConversation(){
        if (tabbedPane.getTabCount() == 1) return;
        tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
    }
    
    private JScrollPane initChatInput(){
        userInput = new JTextArea(3, 40);
        userInput.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        userInput.setLineWrap(true);
        userInput.setWrapStyleWord(true);
        JScrollPane chatInput = new JScrollPane(userInput);
        chatInput.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatInput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatInput.setMaximumSize(new Dimension(3, 10));
        chatInput.setFocusable(true);
        return chatInput;
    }
    
    private JPanel getWelcomePage(){
        JPanel jp = new JPanel();
        JEditorPane textArea = new JEditorPane("text/html", "");
        textArea.setPreferredSize(new Dimension(450, 275));
        textArea.setText("\t<b>Welcome to the Gordon-Bukspan Chat Center</b><br><br>To begin chatting, simply select <i>File -> New Conversation</i>. A list of all users online has been provided for you in the far right text pane. When prompted, enter the name of the user you would like to converse with. <br><br> When you are finished, please <b>log out</b> (<i>File -> Logout</i>).<br><br> <u>Authors:</u> Oren Bukspan and Connor Gordon");
        jp.add(textArea);
        return jp;
    }


    static class ChatDialog extends JPanel {
        private JTextArea textArea;

        public ChatDialog (String text) {
            textArea = new JTextArea(17, 40);
            textArea.setText(text);
            textArea.setEditable(false);
            textArea.setBackground(new Color(220, 226, 255));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setMaximumSize(new Dimension(17, 40));
            add(scrollPane);
        }

        public JTextArea getTextArea () {
            return textArea;
        }
    }
     

    public ImageIcon createImageIcon (String path) {
        URL imgURL = ExampleGUI.class.getResource(path);
        if (imgURL != null)
            return new ImageIcon(imgURL);
        else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
    