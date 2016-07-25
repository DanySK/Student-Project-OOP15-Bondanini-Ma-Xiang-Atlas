package atlas.model;

import java.util.List;

public interface Model {
    
    /**
     * Method called used by the controller to get all the bodies to 
     * be rendered by the view.
     * @return List of bodies to be rendered
     */
    public List<Body> getBodiesToRender();
    
    /**
     * Used to advance in the simulation by a delta time.
     * @param hours hours to progress in the simulation
     */
    public void updateSim(double sec);
    
    /**
     * Adds a body to the solar system.
     * @param b the body to be added
     */
    public void addBody(Body b);
    
    /**
     * Time of the simulation, either a date (i.e. 01/01/2001) or simply a time counter (i.e. 3.25 years).
     * @return time of the simulation 
     */
    public String getTime();
}
