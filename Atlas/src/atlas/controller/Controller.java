package atlas.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import atlas.model.Body;
import atlas.model.BodyType;
import atlas.model.EpochJ2000;
import atlas.model.Body.Properties;
import atlas.utils.EventType;
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
    public void update(final EventType event, String path, Optional<Double> posX,
            Optional<Double> posY) throws IllegalArgumentException, IOException;

    /*public void setNewBody(BodyType type, Optional<String> name, int posX, int posY, double velX, double velY,
            double mass, Properties properties);*/

    /**
     * This method sets the view
     * @param event
     */
    public void setView(View v);
    
    /**
     * This method sets true the add transition
     * @param event
     */
    public void setAdding();
    
    /**
     * This method sets false the add transition
     * @param event
     */
    public void setNotAdding();
    
    /**
     * This method return if transition add is true or false
     * @param event
     */
    public boolean isAdding();
    
    /**
     * This method sets the next body to add
     * @param event
     */
    public void setNextBody(Body body);
    
    /**
     * This method is called when ESC is pressed
     * @param event
     */
    public void reset();
    
    /**
     * This method returns initial zoom
     * @param event
     */
    public double getUnit();
    
    /**
     * This method increases zoom
     * @param event
     */
    public double zoomUp();
    
    /**
     * This method decreases zoom
     * @param event
     */
    public double zoomDown();
    
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
