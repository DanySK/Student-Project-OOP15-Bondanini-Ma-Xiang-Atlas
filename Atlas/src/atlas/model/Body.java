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
    
    public void setName(String name);

    /**
     * 
     * @return the body's mass in kilograms
     */
    public double getMass();
    
    public void setMass(double mass);

    /**
     * Adds the force relative to an another body using Newton's law of gravity.
     * 
     * @param b
     *            the body taken into consideration
     */
    public void addForce(Body b);

    /**
     * Resets the total force applied to this body.
     */
    public void resetForce();

    /**
     * Updates the position of the body in the simulation.
     * 
     * @param dt
     *            time-stamp, determines both accuracy and speed, smaller dt
     *            gives more accuracy but slower speed.
     */
    public void updatePos(double dt);

    /**
     * Calculates the distance from an another body.
     * 
     * @param b
     *            the target body
     * @return the distance between this body and a body b
     */
    public double distanceTo(Body b);

    /**
     * Returns the x coordinates from the origin.
     * 
     * @return the x coordinates in meters
     */
    public double getPosX();
    
    public void setPosX(double x);

    /**
     * Returns the y coordinates from the origin.
     * 
     * @return the y coordinates in meters
     */
    public double getPosY();
    
    public void setPosY(double y);

    /**
     * Returns the velocity in the x-axis
     * 
     * @return the horizontal velocity x
     */
    public double getVelX();
    
    /**
     * Returns the velocity in the y-axis
     * 
     * @return the vertical velocity y
     */
    public double getVelY();

    /**
     * Sets the total velocity of the body, maintaining the same direction.
     * 
     * @param vt
     *            total velocity
     */
    public void setTotalVelocity(double vt);

    /**
     * 
     * @return the body's properties.
     */
    public Properties getProperties();

    /**
     * This method returns the current trail produced by the movement of the
     * body.
     * 
     * @return the collection of the trail points
     */
    public Collection<Pair<Double, Double>> getTrail();

    /**
     * Nested class used to store a celestial body's secondary properties.
     *
     */
    public static class Properties implements java.io.Serializable {
        
		private static final long serialVersionUID = -689182940096161486L;
		private double radius;
        // Rotazione sul proprio asse
        private long rotationPeriod;
        private double rotationAngle;

        /* Optional properties */
        private Double orbitalPeriod = null;
        private Body parent = null;
        private Double temperature = null;

        public static double celsiusToKelvin(double c) {
            return c + 273.15;
        }

        public static double KelvinToCelsius(double c) {
            return c - 273.15;
        }

        public Properties() {
        }

        public Properties(double radius, long rotationPeriod, double rotationAngle, Body parent, Double temperature) {
            super();
            this.radius = radius;
            this.rotationPeriod = rotationPeriod;
            this.rotationAngle = rotationAngle;
            this.parent = parent;
            this.temperature = temperature;
        }

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

        /**
         * Returns the current angle by which the body is rotated.
         * 
         * @return the current angle in degrees
         */
        public double getRotationAngle() {
            return rotationAngle;
        }

        public void setRotationAngle(double rotationAngle) {
            this.rotationAngle = rotationAngle;
        }

        /**
         * Updates the rotation of the body, which is calculated using a
         * time-stamp (dt) and the time it takes to complete a 360 degrees
         * rotation.
         * 
         * @param dt
         *            time-stamp, in seconds
         */
        public void updateRotation(double dt) {
            if (this.rotationPeriod != 0) {
                double currentAngle = this.rotationAngle + dt / this.rotationPeriod * 360;
                this.rotationAngle = currentAngle >= 360 ? currentAngle - 360 : currentAngle;
            }
        }
        
        public Optional<Double> getOrbitalPeriod(){
            return Optional.ofNullable(this.orbitalPeriod);
        }
        
        public void setOrbitalPeriod(Double time){
            this.orbitalPeriod = time;
        }
        public Optional<Body> getParent() {
            return Optional.ofNullable(parent);
        }

        public void setParent(Body parent) {
            this.parent = parent;
        }

        public Optional<Double> getTemperature() {
            return Optional.ofNullable(temperature);
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        @Override
        public String toString() {
            return "Properties [radius=" + radius + ", rotation period=" + rotationPeriod + ", rotation angle="
                    + rotationAngle + ", parent=" + (this.getParent().isPresent() ? this.getParent().get().getName() : "none")
                    + ", temperature=" + temperature + "]";
        }
    }

}