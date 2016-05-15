package atlas.model;

/**
 * A generic body interface.
 *
 */
public interface Body {
    
	public String getName();
	public double getMass();
	
	public void addForce(Body b);
	public void resetForce();
	public void updatePos(double dt);
	
	public double distanceTo(Body b);
	
	public double getPosX();
	public double getPosY();
	
	public double getVelX();
	public double getVelY();
}