package util.networking.chat.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import util.encrypt.Encrypter;
import util.networking.chat.ChatClient;
import util.networking.chat.protocol.GordonBukspanProtocol;

public class ChatApp {

    private static int MAX_USER_CHAR = 12;
    private static JFrame frame; 
    
    public void run () {
        try {
            ChatClient c = new ChatClient("wl-10-190-55-243.wireless.duke.local", new GordonBukspanProtocol());
            String userName = login("", c);
            while(!c.getListUsers().contains(userName));
            frame = new JFrame("Greetings, " + userName +"! Chat. Connect. Play.");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ExampleGUI eg;
            eg = new ExampleGUI(c);
            frame.add(eg);
            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("File");
            menuBar.add(menu);
            JMenuItem newConvoItem = new JMenuItem(new NewConversationAction(eg));
            JMenuItem closeConvoItem = new JMenuItem(new CloseConversation(eg));
            JMenuItem logoutItem = new JMenuItem(new LogoutAction(eg));
            menu.add(newConvoItem);
            menu.add(closeConvoItem);
            menu.add(logoutItem);

            frame.setJMenuBar(menuBar);
            frame.pack();
            frame.setVisible(true);
        }
        catch (IOException e) {
            System.out.println("Cannot connect to server");
        }
    }
    
    private static String login (String errorMessage, ChatClient c) {
        JPanel userPassPanel = new JPanel(new GridLayout(3,2));
        JLabel userLabel = new JLabel("Username (<12 char)");
        JLabel passLabel = new JLabel("Password (>5 char)");
        JTextField userTA = new JTextField(10);
        JPasswordField pass = new JPasswordField(10);
        userPassPanel.add(userLabel);
        userPassPanel.add(userTA);
        userPassPanel.add(passLabel);
        userPassPanel.add(pass);
       
        JLabel message = new JLabel("<html>If you are a pre-existing user, select 'Login.'<br>Otherwise, register by selecting 'Register'<br><br>" + errorMessage + "</html>");
        
        JRadioButton login = new JRadioButton("Login");
        JRadioButton register = new JRadioButton("Register");
        
        JPanel buttons = new JPanel(new GridLayout(1,2));
        buttons.add(login);
        buttons.add(register);
        ButtonGroup bg = new ButtonGroup();
        bg.add(login);
        bg.add(register);
        login.setSelected(true);
        register.setSelected(false);
        buttons.setBorder(BorderFactory.createTitledBorder("Select one of the following options"));
        
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.add(message, BorderLayout.BEFORE_FIRST_LINE);
        loginPanel.add(userPassPanel, BorderLayout.CENTER);
        loginPanel.add(buttons, BorderLayout.AFTER_LAST_LINE);
        loginPanel.setPreferredSize(new Dimension(350,180));
        
        
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, loginPanel, "Please Enter a Username and Password",
                                 JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                 null, options, loginPanel.getComponent(1));
        if(option == 0) // pressing OK button
        {
            String userName = userTA.getText().trim();
            char[] password = pass.getPassword();
            if(userName.length() > MAX_USER_CHAR)
                return login("<html>Error: UserName entered is too long<br><br></html>", c);
            else if(userName.length() == 0)
                return login("<html>Error: A Zero Character UserName is NOT ALLOWED<br><br></html>", c);
            else if (password.length < 5)
                return login("<html>Error: Password must be at least 5 characters<br><br></html>",c);
            if(login.isSelected()){
                boolean status = c.loginWithTimeout(userName, Encrypter.hashCode(new String(password)), 3000);
                System.out.println("login: " + status);
                if(!status) return login("<html>Error: Could not login. Please check your server connection.<br><br>", c);
            }
            else{
                boolean status = c.registerWithTimeout(userName, Encrypter.hashCode(new String(password)), 3000);
                System.out.println("register: " + status);
                if(!status) return login("<html>Error: Could not Properly Register. Please Try Again.<br><br>",c);
            }
            return userName;
        }
        else if(option == 1)
            return login("<html>Error: Please Enter a Username and Password. Otherwise you cannot chat.<br><br></html>", c);
        System.exit(0);
        return "";
    }
    
    static class NewConversationAction extends AbstractAction {
        private ExampleGUI eg;
        public NewConversationAction (ExampleGUI exampleGUI) {
            super("New Conversation");
            this.eg = exampleGUI;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            List<String> usersOnline = eg.getUsersOnline();
            if(usersOnline.size() == 0){
                JOptionPane.showMessageDialog(frame, "No users online.");
                return;
            }
            String userName =
                    (String)JOptionPane
                            .showInputDialog(frame,
                                             "Choose the name of the user you would like to converse with.",
                                             "Start a New Conversation",
                                             JOptionPane.QUESTION_MESSAGE, null,
                                             eg.getUsersOnline().toArray(new String[0]), eg.getUsersOnline().toArray(new String[0])[0]);
            eg.newConversation(userName); 
        }
    }

    static class CloseConversation extends AbstractAction {
        private ExampleGUI eg;

        public CloseConversation (ExampleGUI exampleGUI) {
            super("Close Conversation");
            this.eg = exampleGUI;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            eg.closeConversation();
        }
    }
    
    static class LogoutAction extends AbstractAction {
        private ExampleGUI eg;

        public LogoutAction(ExampleGUI exampleGUI) {
            super("Log Out");
            this.eg = exampleGUI;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            eg.logout();
        }
    }

}
