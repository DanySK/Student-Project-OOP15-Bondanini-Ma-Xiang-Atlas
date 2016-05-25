package atlas.model;

import java.util.Collection;
import java.util.Optional;

import atlas.utils.Pair;

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
    
    /**
     * 
     * @return the body's properties.
     */
    public Properties getProperties();
    
    /**
     * This method returns the current trail produced by the movement of the body.
     * @return the collection of the trail points
     */
    public Collection<Pair<Double,Double>> getTrail();
    
    /**
     * Nested class used to store a celestial body's secondary properties.
     *
     */
    public static class Properties {
        private double radius;
        private long rotationPeriod;
        private double rotationAngle;

        /* Optional properties */
        private Optional<Body> parent = Optional.empty();
        private Optional<Double> temperature = Optional.empty();

        public double getRadius() {
            return radius;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public long getRotationPeriod() {
            return rotationPeriod;
        }

        public void setRotationPeriod(long rotationPeriod) {
            this.rotationPeriod = rotationPeriod;
        }

        public double getRotationAngle() {
            return rotationAngle;
        }
        
        public void setRotationAngle(double rotationAngle) {
            this.rotationAngle = rotationAngle;
        }
        
        public void updateRotation(double dt){
            if (this.rotationPeriod != 0) {
                double currentAngle = this.rotationAngle + dt/this.rotationPeriod * 360;
                this.rotationAngle = currentAngle >= 360 ? currentAngle - 360 : currentAngle;
            }
        }

        public Optional<Body> getParent() {
            return parent;
        }

        public void setParent(Body parent) {
            this.parent = Optional.ofNullable(parent);
        }

        public Optional<Double> getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = Optional.ofNullable(temperature);
        }
    }
    
    
}