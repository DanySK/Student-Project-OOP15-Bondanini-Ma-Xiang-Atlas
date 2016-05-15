package atlas.model;

import java.util.List;

public interface ModelInterface {
    
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
    public void updateSim(int hours);
}
