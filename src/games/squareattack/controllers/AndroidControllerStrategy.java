package games.squareattack.controllers;

import games.squareattack.objects.WallBuilder;
import games.squareattack.sprites.Square;
import util.input.android.events.AndroidButtonEvent;
import util.input.android.events.AndroidSensorEvent;
import util.input.android.events.AndroidServerMessage;
import util.input.android.events.AndroidVibration;
import util.input.android.events.JoyStickEvent;
import util.input.android.events.LineSegment;
import util.input.core.AndroidController;
import util.input.interfaces.listeners.AndroidListener;
import util.mathvector.MathVector2D;

/**
 * 
 * @author Ben Schwab
 *
 */
public class AndroidControllerStrategy extends ControllerStrategy implements AndroidListener {
    private Square mySquare;
    private AndroidController myAndroidController;
    private WallBuilder myWallBuilder;
    
    public AndroidControllerStrategy(Square target, int playerNumber, boolean isDefender, WallBuilder wallBuilder){
        mySquare = target;
        myAndroidController = new AndroidController(playerNumber);
        myWallBuilder = wallBuilder;
        AndroidServerMessage msg = new AndroidServerMessage();
        if(isDefender){
            msg.putControllerConfigMessage(false, false, true, false);
        }
        else{
            msg.putControllerConfigMessage(true, true, false, false);
        }
        myAndroidController.messageServer(msg);
        myAndroidController.subscribe(this);
        setControls();
        System.out.println("server should be starting");
    }
    
    @Override
    public void setControls () {
        setPlaystationControls();
        setGameboyControls();
        setTouchScreenControls();
    }

    private void setTouchScreenControls () {
       
        
    }
    
    
    private void setGameboyControls () {
        try {
            myAndroidController.setControl(AndroidButtonEvent.GameBoy.UP,
                                           AndroidButtonEvent.BUTTON_PRESSED, mySquare,
                    "enableMoveUp");
            myAndroidController.setControl(AndroidButtonEvent.GameBoy.DOWN,
                                           AndroidButtonEvent.BUTTON_PRESSED, mySquare,
                    "enableMoveDown");
            myAndroidController.setControl(AndroidButtonEvent.GameBoy.LEFT,
                                           AndroidButtonEvent.BUTTON_PRESSED, mySquare,
                    "enableMoveLeft");
            myAndroidController.setControl(AndroidButtonEvent.GameBoy.RIGHT,
                                           AndroidButtonEvent.BUTTON_PRESSED, mySquare,
                    "enableMoveRight");
            myAndroidController.setControl(AndroidButtonEvent.GameBoy.UP,
                                           AndroidButtonEvent.BUTTON_RELEASED, mySquare,
                    "disableMoveUp");
            myAndroidController.setControl(AndroidButtonEvent.GameBoy.DOWN,
                                           AndroidButtonEvent.BUTTON_RELEASED, mySquare,
                    "disableMoveDown");
            myAndroidController.setControl(AndroidButtonEvent.GameBoy.LEFT,
                                           AndroidButtonEvent.BUTTON_RELEASED, mySquare,
                    "disableMoveLeft");
            myAndroidController.setControl(AndroidButtonEvent.GameBoy.RIGHT,
                                           AndroidButtonEvent.BUTTON_RELEASED, mySquare,
                    "disableMoveRight");
           

        }
        catch (NoSuchMethodException e) {

            e.printStackTrace();
        }
        catch (IllegalAccessException e) {

            e.printStackTrace();
        }
        
    }

    private void setPlaystationControls () {
        try {
            myAndroidController.setControl(AndroidButtonEvent.Playstation.UP,
                                           AndroidButtonEvent.BUTTON_PRESSED, mySquare,
                    "enableMoveUp");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.DOWN,
                                           AndroidButtonEvent.BUTTON_PRESSED, mySquare,
                    "enableMoveDown");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.LEFT,
                                           AndroidButtonEvent.BUTTON_PRESSED, mySquare,
                    "enableMoveLeft");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.RIGHT,
                                           AndroidButtonEvent.BUTTON_PRESSED, mySquare,
                    "enableMoveRight");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.UP,
                                           AndroidButtonEvent.BUTTON_RELEASED, mySquare,
                    "disableMoveUp");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.DOWN,
                                           AndroidButtonEvent.BUTTON_RELEASED, mySquare,
                    "disableMoveDown");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.LEFT,
                                           AndroidButtonEvent.BUTTON_RELEASED, mySquare,
                    "disableMoveLeft");
            myAndroidController.setControl(AndroidButtonEvent.Playstation.RIGHT,
                                           AndroidButtonEvent.BUTTON_RELEASED, mySquare,
                    "disableMoveRight");
           

        }
        catch (NoSuchMethodException e) {

            e.printStackTrace();
        }
        catch (IllegalAccessException e) {

            e.printStackTrace();
        }
        
    }

    @Override
    public void onScreenPress (AndroidButtonEvent b) {
       
        
    }

    @Override
    public void onJoyStickMove (JoyStickEvent j) {
       double angle = j.getMyAngle();
        double mag =j.getMyMagnitude();
        MathVector2D vector = new MathVector2D(Math.cos(Math.toRadians(angle))*mag,-Math.sin(Math.toRadians(angle))*mag);
        mySquare.setMovementVector(vector);
        
    }

    @Override
    public void onControllerDisconnect () {
        
        
    }

    @Override
    public void onTouchMovement (LineSegment l) {
       
       myWallBuilder.buildWall(l);
        
    }

    @Override
    public void onAccelerometerEvent (AndroidSensorEvent e) {
        
        
    }
    
    public void postVibration (AndroidVibration vib) {
        AndroidServerMessage msg = new AndroidServerMessage();
        msg.setVibration(vib);
        myAndroidController.messageServer(msg);
        
    }

 
    
    

}
