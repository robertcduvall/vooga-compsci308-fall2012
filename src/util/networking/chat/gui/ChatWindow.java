package util.networking.chat.gui;

import javax.swing.JFrame;

public class ChatWindow {
    public static void main(String[] args){
        JFrame f = new JFrame("Welcome, congo!");
        f.setSize(700,500);
        f.add(new ExampleGUI());
        f.setVisible(true);
    }
}
