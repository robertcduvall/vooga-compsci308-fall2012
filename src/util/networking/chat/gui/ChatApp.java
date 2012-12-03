package util.networking.chat.gui;

import javax.swing.JFrame;

public class ChatApp {
    public static void main(String[] args){
        JFrame frame = new JFrame("Welcome to the World of Chatting!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ExampleGUI());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
    }
}
