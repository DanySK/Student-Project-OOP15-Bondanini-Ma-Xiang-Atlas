package atlas.model.algorithms;

import java.util.ArrayList;
import java.util.List;

import atlas.model.Body;

public interface Algorithm {
    
    /**
     * It updates the simulation according to a specific n-body algorithm implementation.
     * @param sec time step of the update
     */
    public ArrayList<Body> exceuteUpdate(ArrayList<Body> bodies, double sec);
}