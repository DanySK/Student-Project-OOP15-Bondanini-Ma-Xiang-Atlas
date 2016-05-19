package atlas.model;

/**
 * A generic celestial body interface.
 *
 */
public interface Body {
    
    /**
     * 
     * @return which type is the body
     */
    public BodyType getType();
    
    /**
     * 
     * @return the body's name
     */
    public String getName();
    
    /**
     * 
     * @return the body's mass in kilograms
     */
    public double getMass();
    
    /**
     * Adds the force relative to an another body using Newton's law of gravity.
     * @param b the body taken into consideration
     */
    public void addForce(Body b);
    
    /**
     * Resets the total force applied to this body.
     */
    public void resetForce();
    
    /**
     * Updates the position of the body in the simulation.
     * @param dt time-stamp, determines both accuracy and speed, smaller dt 
     * gives more accuracy but slower speed.
     */
    public void updatePos(double dt);
    
    /**
     * Calculates the distance from an another body.
     * @param b the target body
     * @return the distance between this body and a body b
     */
    public double distanceTo(Body b);
    
    /**
     * Returns the x coordinates from the origin.
     * @return the x coordinates in meters
     */
    public double getPosX();
    
    /**
     * Returns the y coordinates from the origin.
     * @return the y coordinates in meters
     */
    public double getPosY();
    
    /**
     * Returns the velocity in the x-axis
     * @return the horizontal velocity x
     */
    public double getVelX();
    
    /**
     * Returns the velocity in the y-axis
     * @return the vertical velocity y
     */
    public double getVelY();
    
    /**
     * Sets the total velocity of the body, maintaining the same direction. 
     * @param vt total velocity
     */
    public void setTotalVelocity(double vt);
    
    
    
}