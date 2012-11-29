package util.particleEngine;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * This class wraps around a List of ParticleEngines to be tested by the
 * ParticleEngineTester. The setUpParticleEngine method needs to be
 * implemented by any class that extends ParticleEngineTetingUnit; this method
 * creates the ParticleEngine objects that are stored in the List.
 * 
 * @author Kathleen/David Spruill
 * 
 */

public abstract class ParticleSystem {

    private List<ParticleEngine> myParticleEngines;
    protected Point position = new Point(400, 400);

    public ParticleSystem () {
        myParticleEngines = new ArrayList<ParticleEngine>();
        setUpParticleEngines();
    }

    /**
     * Instantiates the ParticleEngine objects to be tested and adds them to
     * myParticleEngines.
     */
    abstract protected void setUpParticleEngines ();

    /**
     * Returns a copy of the List myParticleEngines.
     * 
     * @return
     */
    public List<ParticleEngine> getParticleEngines () {
        return new ArrayList<ParticleEngine>(myParticleEngines);
    }

    /**
     * Adds a ParticleEngine to the List myParticleEngines.
     * 
     * @param density defines the number of particles in the engine
     * @param particleImage the image to use for the particles
     * @param direction the general direction in which the particles will
     *        travel, (0,0) will travel in all directions
     * @param tolerance how much the particles can vary from the given direction
     * @param length how long the particles will exist before being reset
     */
    protected void addParticleEngine (int density, Image particleImage,
            Point position, Point velocity, int tolerance, int length,
            double angleSpan, int numberOfDirections, Boolean loop) {
        myParticleEngines.add(new ParticleEngine(density, particleImage,
                position, velocity, tolerance, length, angleSpan,
                numberOfDirections, loop));
    }

    protected void addParticleEngine (Image particleImage, Point position,
            Boolean loop) {
        myParticleEngines
                .add(new ParticleEngine(particleImage, position, loop));
    }

    protected void addParticleEngine (Image particleImage, Point position,
            double angleSpan, int numberOfDirections, Boolean loop) {
        myParticleEngines.add(new ParticleEngine(particleImage, position,
                angleSpan, numberOfDirections, loop));
    }

    public void update () {
        Stack<ParticleEngine> remove = new Stack<ParticleEngine>();
        for (ParticleEngine p : myParticleEngines) {
            if(!p.stillExists())  remove.add(p);
            p.update();
        }
        for(ParticleEngine p:remove) {
            myParticleEngines.remove(p);
        }
    }

    /**
     * Moves the creation point (where new particles begin life) by a vector
     * (implemented as a Point here)
     * 
     * @param moveBy the amount to move the creation point by
     */
    public void move (Point moveBy) {
        for (ParticleEngine p : myParticleEngines) {
            p.getStartingPosition().x += moveBy.x;
            p.getStartingPosition().y += moveBy.y;
        }
    }

    public Boolean stillExists() {
        return myParticleEngines.size()>0;
    }
    
    public void draw (Graphics2D g) {
        for (ParticleEngine p : myParticleEngines)
            p.draw(g);
    }

    public void setVelocity (Point velocity) {
        for (ParticleEngine p : myParticleEngines)
            p.setVelocity(velocity);
    }
    
    public void setPosition (Point position) {
        for (ParticleEngine p : myParticleEngines)
            p.setStartingPosition(position);
    }
}
