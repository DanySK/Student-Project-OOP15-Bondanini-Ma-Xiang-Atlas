package atlas.controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Optional;

import atlas.model.Model;

public interface InputManager {
	
	public void lockVenuse(); //provvisorio... da eliminare

    /**
     * This method is invocated when left mouse button is clicked
     */
    public void mouseClicked();
    
    /**
     * This method is invocated when left mouse button is clicked > 2
     */
    public void mouseMultiClick();

    /**
     * This method is invocated when left mouse button is pressed
     */
    public void mousePressed();

    /**
     * This method is invocated when left mouse button is released
     */
    public void mouseReleased();

    /**
     * This method is invocated when ESC is pressed
     */
    public void ESC();

    /**
     * This method is invocated when the Spacebar is pressed
     */
    public void spaceBar();

    /**
     * Changes the status of InputManager
     */
    public void changeStatus(Status status);

    /**
     * Changes Speed and Unit of the simulation
     */
    public void changeSpeed();

    /**
     * Sets the status of InputManager to Adding, when the user want add a new
     * body
     */
    public void addMode();

    /**
     * Saves the current state of the simulation into a file.
     * 
     * @throws IOException
     *             any IOException
     * @throws IllegalArgumentException
     *             if the file doesn't exits
     */
    public void saveConfig() throws IOException, IllegalArgumentException;
    
    /**
     * Saves a body into a file.
     * 
     * @throws IOException
     *             any IOException
     * @throws IllegalArgumentException
     *             if the file doesn't exits
     */
    public void saveBody() throws IOException, IllegalArgumentException;

    /**
     * Loads a configuration from a file.
     * 
     * @throws IOException
     *             any IOException
     * @throws IllegalArgumentException
     *             if file doesn't exits or cannot be deserialized
     */
    public Optional<Model> loadConfig() throws IOException, IllegalArgumentException;

    /**
     * This method increases zoom
     * 
     */
    public void zoomUp();

    /**
     * This method decreases zoom
     * 
     */
    public void zoomDown();

    /**
     * Slide simulation when user press W
     * 
     */
    public void wSlide();

    /**
     * Slide simulation when user press S t
     */
    public void sSlide();

    /**
     * Slide simulation when user press A
     * 
     */
    public void aSlide();

    /**
     * Slide simulation when user press D
     * 
     */
    public void dSlide();
    
    
    public void initialReference();

}
