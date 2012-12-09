package games.squareattack;

import games.squareattack.gui.GameFrame;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * 
 * @author Ben Schwab
 * 
 *         So, I see you are looking at the Source Code for Square Attack. It's
 *         a pretty cool game if I do say so myself - but, unless you have a mac
 *         and android phone, and configure your eclipse to run a 32 bit JVM
 *         your going to be out of luck playing it.
 * 
 *         Now as you will notice - this game has a lot of classes. 28 to be
 *         exact. So I am sure you are wondering, what classes should I look at?
 * 
 *         Good question.
 * 
 *         This code by far isn't my cleanest code I have written, but I will
 *         draw your attention to the good stuff - and hope sweep the not so
 *         good stuff
 *         under the rug of there just-being-too-many-classes-to-track-
 *         everything-down.
 *         
 *         Yeah... maybe all those magic numbers are going to get noticed..
 * 
 *         ====================================================================F
 *         Controls:
 * 
 *         This game was built off to show the control API. (Mostly the
 *         Android).
 * 
 *         The game using the Controller API and creates ControllerStrategies
 * 
 *         There are two types of Controller strategies - Android and Keyboard.
 *         I put these guys into there own classes. for a few good reasons.
 *         First, the point of the game is that there is the nifty start menu
 *         where you can choose for up to four players to play simulatenously
 *         AND choose what controller they want to use. The input API lives on
 *         the
 *         principle of mapping a action to an object - and thus these classes
 *         automate that
 *         process by using an object as a parameter.
 * 
 *         This game shows how incredibly easy it is to change what controller
 *         moves an object -
 *         the abstract controller class even has a method to change what object
 *         a controller moves
 *         (swapping controllers after a level for example).
 * 
 *         As the game doesn't know if player 2 will be android or keyboard, it
 *         is assigned at run time.
 *         This is a major feature of the input API.
 * 
 *         Now, the star of the show here is definitely the Android Controller.
 *         Note, how the Input API is used
 *         to send rumble events to the Android Controller. And also note how I
 *         force the first player to use an android touch controller
 *         by sending a ControllerConfig parameter in the Android Controller
 *         strategy.
 * 
 *         The Android controller both maps actions to buttons, and also catches
 *         Joystick events and even
 *         touch screen events to draw barriers into the game!
 * 
 *         So in summary for controls we see :
 * 
 *         The ease of creating multiple controllers created from one or two
 *         controller strategies
 *         The ability to send rumble events from your game to an Android
 *         controller
 *         The ability to send a description of you game to to an Android
 *         controller
 *         Using advanced inputs such as joysticks and touchscreens
 * 
 *         ====================================================================
 *         Use of Util APIS
 * 
 *         This game uses the MathVector2D class (pretty well written besides
 *         lacking a few useful methods
 *         such as getting an angle from a vector). And the SoundPlayer
 *         (required a wrapper - Sound Manager - to be useful, but I appreciated
 *         having to do low level sound player creation).
 * 
 *         I really wanted to use the particle engine - but man does that thing
 *         chew the framerate ...
 *         and also not have java doc comments.
 * 
 *         ====================================================================
 *         A really flexible game engine
 * 
 *         You may be wondering where is the game engine?
 *         Your right - I smacked all my game specific code into the game
 *         engine. But I did this for the sake of time. The game engine is there
 *         underneath, and I built it to be super flexible.
 * 
 *         All it is a game manager that can switch between game states, and
 *         contains references to sprites.
 *         These sprites really don't do much besides have some behavior. The
 *         game is instead driven by game rules.
 * 
 *         This was inspired by the in class game design session - and it makes
 *         a lot of sense. If you have a game manager that knows
 *         about the all the sprites, why not have it apply rules that can be
 *         swapped out that does collisions and game over conditions.
 * 
 *         I could very easily swap out a couple rules and have a vastly
 *         different game.
 * 
 *         ================================================================
 *         Conclusion:
 *         
 *         This code may not pass checkstyle completely, but I spent about 20 hours creating it.
 *         This was mostly because the existing game engines were confusing and without a manual when 
 *         I started to make the game. They also did not allow for the flexibility to make a creative game
 *         such as square attack.
 *         
 *         On the bright side, the amount of time spent setting up controls was comparatively very small (1-2 hours).
 * 
 * 
 * 
 */
public class PlaySquareAttack implements IArcadeGame {
    private static final String GAME_DESCRIPTION =
            "Square Attack! is an intense 4 player game of keep away. It supports multiple controller types and uses features of an android touch screen and controller vibrations!"
                    + "Warning: If you do not have Android phone and the Android app, this game will be stupid. If you don't have a Mac this game will also be stupid. If you never read my manual on how to use an Android controller, this game will also be stupid."
                    + "So I expect this game will be stupid for everyone except me. (And by stupid, I mean crash.) ";

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        new GameFrame();
    }

    @Override
    public List<Image> getScreenshots () {
        Image myImage = new ImageIcon("src/games/squareattack/squareattack.png").getImage();
        List<Image> imageList = new ArrayList<Image>();
        imageList.add(myImage);
        return null;
    }

    @Override
    public Image getMainImage () {
        Image myImage = new ImageIcon("src/games/squareattack/squareattack.png").getImage();
        return myImage;
    }

    @Override
    public String getDescription () {
        return GAME_DESCRIPTION;
    }

    @Override
    public String getName () {
        return "Square Attack!";
    }
    
    //play the game without the arcade
    public static void main (String[] args) {
        GameFrame g = new GameFrame();
    }

}
