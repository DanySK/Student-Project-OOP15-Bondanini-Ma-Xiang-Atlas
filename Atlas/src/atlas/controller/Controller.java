package atlas.controller;

import java.util.List;
/**
 * Interface for Controller
 * @author andrea
 */

import atlas.model.Body;
import atlas.view.View;

public interface Controller {
  
    /**
     * This method start the simulation
     */
    public void startSim();
    
    /**
     * This method stop the simulation
     */
    public void stopSim();
    
    /**
     * This method close the simulation
     */
    public void exit();
    
    public List<Body> getBodiesPositionToRender();
 
    public void setView(View v);

}
