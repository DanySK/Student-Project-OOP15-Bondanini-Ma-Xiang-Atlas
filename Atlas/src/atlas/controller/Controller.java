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

    /*public void setNewBody(BodyType type, Optional<String> name, int posX, int posY, double velX, double velY,
            double mass, Properties properties);*/

    /**
     * This method sets the view
     * @param event
     */
    public void setView(View v);
    
    /**
     * This method returns initial zoom
     * @param event
     */
    public double getUnit();
    
    /**
     * This method increases zoom
     * @param event
     */
    public void zoomUp();
    
    /**
     * This method decreases zoom
     * @param event
     */
    public void zoomDown();
    
    /**
     * Set the UI and Speed of simulation
     * @param path the path of the file
     * @throws IllegalArgumentException if speed value is wrong
     */
    public void setSpeed(Units unit, int speed) throws IllegalArgumentException;
    
    /**
     * Saves the current state of the simulation into a file.
     * @param path the path of the file
     * @throws IOException any IOException
     * @throws IllegalArgumentException if the file doesn't exits
     */
    public void saveConfig(String path) throws IOException, IllegalArgumentException;
    

    /**
     * Loads a configuration from a file.
     * @param path
     * @throws IOException any IOException
     * @throws IllegalArgumentException if file doesn't exits or cannot be deserialized
     */
    public void loadConfig(String path) throws IOException, IllegalArgumentException;

}
