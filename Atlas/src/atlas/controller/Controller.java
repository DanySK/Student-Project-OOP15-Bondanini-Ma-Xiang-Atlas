package atlas.controller;

import java.io.IOException;
import java.util.List;
/**
 * Interface for Controller
 * @author andrea
 */

import atlas.model.Body;
import atlas.utils.EventType;
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
    
    /**
     * This method captures events from View
     * @param event
     */
    public void update(final EventType event, final String path) throws IllegalArgumentException, IOException;
    
    public List<Body> getBodiesPositionToRender();
    
    /**
     * This method sets the view
     * @param event
     */
    public void setView(View v);

}
