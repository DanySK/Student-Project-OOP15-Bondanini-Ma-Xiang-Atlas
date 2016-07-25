package atlas.model;

import java.util.List;

public interface Algorithm {
    
    /**
     * It updates the simulation according to a specific n-body algorithm implementation.
     * @param sec time step of the update
     */
    public void exceuteUpdate(List<Body> bodies, double sec);
}