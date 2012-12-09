package games.particleEngineMedley;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.mathvector.MathVector2D;
import util.particleEngine.Explosion;
import util.particleEngine.ParticleSystem;
import util.particleEngine.Trail;
import util.reflection.Reflection;

/**
 * Code for this applet borrowed from ParticleEngineTester (authors: Kathleen and David) 
 * which in turn borrowed code from Prof. Duvall's game applet.
 * 
 * See the README for comments. Thanks!
 * 
 * @author Kathleen
 *
 */

public class ParticleEngineApplet extends JApplet implements MouseInputListener{
	private static final long serialVersionUID = 1L;
	
	private static final Dimension defaultSize = new Dimension(800, 800);
    private static final int ONE_SECOND = 1000;
    private static final int FRAMES_PER_SECOND = 30;

    private Timer myTimer;
    private List<ParticleSystem> mySystems = new ArrayList<ParticleSystem>();
    
    private Font font;
    private long lastTime = 0;
    
    private MathVector2D myLastMouseClickPosition;
    private KeyboardController myKeyboardController;
    private MouseController myMouseController;
    
    private Image backgroundImage;
    private Point backgroundImagePosition;
    private Map<String, Point> imageNamePositionMap;
    
    /**
     * Initializes the applet --- called by the browser.
     */
    @Override
    public void init () {
        init(defaultSize);
        font = new Font("Times New Roman", Font.PLAIN, 12);
        try {
			setupInputControllers();
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
		}
        setupImageNamePositionMap();
        myLastMouseClickPosition = new MathVector2D(400,400);
    }

	private void setupInputControllers() throws NoSuchMethodException, IllegalAccessException {
		myKeyboardController = new KeyboardController(this);
        myMouseController = new MouseController(this);
        myMouseController.subscribe(this);
        configureActions();
	}
	
	/**
	 * Configures input events with method calls. (Any suggestions on how to improve the design of this method?)
	 * 
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 */
	private void configureActions() throws NoSuchMethodException, IllegalAccessException {
		myKeyboardController.setControl(KeyEvent.VK_C, KeyboardController.PRESSED, this, Constants.clearParticleSystems);
		myKeyboardController.setControl(KeyEvent.VK_B, KeyboardController.PRESSED, this, Constants.addBubbles);
		myKeyboardController.setControl(KeyEvent.VK_F, KeyboardController.PRESSED, this, Constants.addFire);
		myKeyboardController.setControl(KeyEvent.VK_I, KeyboardController.PRESSED, this, Constants.addFireworks);
		myKeyboardController.setControl(KeyEvent.VK_L, KeyboardController.PRESSED, this, Constants.addLight);
		myKeyboardController.setControl(KeyEvent.VK_S, KeyboardController.PRESSED, this, Constants.addSnow);
		myKeyboardController.setControl(KeyEvent.VK_W, KeyboardController.PRESSED, this, Constants.addWaterfall);
		myKeyboardController.setControl(KeyEvent.VK_1, KeyboardController.PRESSED, this, Constants.setBackground1);
		myKeyboardController.setControl(KeyEvent.VK_2, KeyboardController.PRESSED, this, Constants.setBackground2);
		myKeyboardController.setControl(KeyEvent.VK_3, KeyboardController.PRESSED, this, Constants.setBackground3);
	}
	
	private void setupImageNamePositionMap() {
		imageNamePositionMap = new HashMap<String, Point>();
		imageNamePositionMap.put(Constants.mountainCliffpng, new Point(0,0));
		imageNamePositionMap.put(Constants.cauldronpng, new Point(250, 300));
		imageNamePositionMap.put(Constants.DukeChapelpng, new Point(0,0));
		backgroundImagePosition = new Point(0,0);
	}
	
	public void setBackground1() {
		setBackground(Constants.mountainCliffpng);
	}
	
	public void setBackground2() {
		setBackground(Constants.cauldronpng);
	}

	public void setBackground3() {
		setBackground(Constants.DukeChapelpng);
	}
	
	private void setBackground(String imageName) {
		ImageIcon temp = new ImageIcon(
				ParticleEngineApplet.class.getResource(imageName));
		backgroundImage = temp.getImage();
		backgroundImagePosition = imageNamePositionMap.get(imageName);
	}
	
    /**
     * Initilizes the applet, but is called by the main method
     * 
     * @param size the window size
     */
    public void init (Dimension size) {
        setSize(size);
        setPreferredSize(size);
        setFocusable(true);
        requestFocus();
    }
    
    /**
     * Called at regular intervals set up by the action listener instantiated in
     * the start method.
     */
    public void update () {
        Stack<ParticleSystem> remove = new Stack<ParticleSystem>();
        for (ParticleSystem p : mySystems) {
            if (!p.stillExists()) remove.add(p);
            p.update();
        }
        for (ParticleSystem p : remove) {
            mySystems.remove(p);
        }
    }
    
    /**
     * Starts the applet's action, i.e., starts the animation.
     */
    public void start () {
        // create a timer to animate the canvas
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND,
                new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent e) {
                        update();
                        repaint();
                    }
                });
        myTimer.start();
    }

    /**
     * Stops the applet's action, i.e., the animation.
     */
    @Override
    public void stop () {
        myTimer.stop();
    }

    /**
     * Called by Java to paint the frame. I've found that by
     * drawing the frame onto a buffer and then drawing that buffer
     * all at once, much smoother graphics can be obtained.
     * 
     * @param g The graphics object passed by Java, will be cast as
     *        SpecialGraphics
     */
    public void paint (Graphics g) {
        BufferedImage buff = new BufferedImage(getSize().width,
                getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) buff.getGraphics();
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        if (backgroundImage != null){
        	g2d.drawImage(backgroundImage, backgroundImagePosition.x, backgroundImagePosition.y, null);
        }
        
        g2d.setFont(font);
        g2d.setColor(Color.BLUE);
        long newTime = System.currentTimeMillis();
        g2d.drawString("fps: "
                + (1 / ((newTime - lastTime) / 1000.0f))
                , 100, 100);
        lastTime = newTime;
        
        int spriteCount=0;
        // Actually draw the particleSystems
        for (ParticleSystem e : mySystems) {
            e.draw(g2d);
            spriteCount+=e.spriteCount();
        }
        g2d.drawString("Sprites: "+ spriteCount,400,100);

        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(buff, null, 0, 0);
    }

    public void clearParticleSystems(){
    	mySystems.clear();
    }
    
    /**
     * It would be best if reflection could be used to 
     * instantiate and add new Particle Systems, but 
     * such an approach is not possible at this time since
     * the input utilities do not support configuring 
     * methods with parameters.
     */
    public void addBubbles(){
    	mySystems.add(new Bubbles(myLastMouseClickPosition));
    }
    
    public void addFire(){
    	mySystems.add(new Fire(myLastMouseClickPosition));
    }

    public void addLight(){
    	mySystems.add(new StreamingSunlight(myLastMouseClickPosition));
    }
    
    public void addWaterfall(){
    	mySystems.add(new Waterfall(myLastMouseClickPosition));
    }
    
    public void addSnow() {
		mySystems.add(new Snow(myLastMouseClickPosition));
	}

    public void addFireworks() {
    	mySystems.add(new Fireworks(myLastMouseClickPosition));
    }
    
    @Override
	public void mouseClicked(MouseEvent e) {
		storeLastMouseClickPosition(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		storeLastMouseClickPosition(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
	
	private void storeLastMouseClickPosition(MouseEvent e) {
		myLastMouseClickPosition = new MathVector2D(e.getPoint());
		//mySystems.add((ParticleSystem) Reflection.createInstance(currentParticleSystemType, new MathVector2D(e.getPoint())));
	}
}
