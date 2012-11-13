package util.input.android.bluetoothserver;

import java.awt.Robot;
import java.awt.event.KeyEvent;


public class AndroidGameBoyCommandProcessor implements AndroidCommandProcessor {

    private static final int EXIT_CMD = -1;
    private static final int KEY_RIGHT = 1;
    private static final int KEY_LEFT = 2;

    @Override
    public void processCommand(int command) {
        try {
            Robot robot = new Robot();
            switch (command) {
                case KEY_RIGHT:
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    System.out.println("Right");
                    // release the key after it is pressed. Otherwise the event
                    // just keeps getting trigged
                    robot.keyRelease(KeyEvent.VK_RIGHT);
                    break;
                case KEY_LEFT:
                    robot.keyPress(KeyEvent.VK_LEFT);
                    System.out.println("Left");
                    // release the key after it is pressed. Otherwise the event
                    // just keeps getting trigged
                    robot.keyRelease(KeyEvent.VK_LEFT);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
