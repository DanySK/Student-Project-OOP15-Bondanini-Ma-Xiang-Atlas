package atlas.controller;

import java.io.IOException;
import java.util.Optional;
import atlas.model.Body;
import atlas.utils.Units;
import atlas.view.SimEvent;
import atlas.view.View;

/**
 * Interface for Controller
 * 
 * @author andrea
 */

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
     * 
     * @param event
     */
    public void update(SimEvent event);

    /**
     * This method sets the view
     * @param event
     */
    public void setView(View v);

    /**
     * Set the UI and Speed of simulation
     * @param path the path of the file
     * @throws IllegalArgumentException if speed value is wrong
     */
    

}
