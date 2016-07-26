package atlas.model.algorithms;

import java.util.List;

import atlas.model.Body;

public interface Algorithm {
    
    /**
     * It updates the simulation according to a specific n-body algorithm implementation.
     * @param sec time step of the update
     */
    public List<Body> exceuteUpdate(List<Body> bodies, double sec);
}