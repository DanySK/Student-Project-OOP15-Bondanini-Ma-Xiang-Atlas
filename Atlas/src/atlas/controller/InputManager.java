package atlas.controller;

import java.io.IOException;

import atlas.controller.InputManagerImpl.Status;

public interface InputManager {

    public void mouseClicked();

    public void mousePressed();

    public void mouseReleased();

    public void ESC();

    public void spaceBar();

   // public void changeStatus(Status edit);
    
    public void changeSpeed();

    public void addMode();

    /**
     * Saves the current state of the simulation into a file.
     * 
     * @param path
     *            the path of the file
     * @throws IOException
     *             any IOException
     * @throws IllegalArgumentException
     *             if the file doesn't exits
     */
    public void saveConfig() throws IOException, IllegalArgumentException;

    /**
     * Loads a configuration from a file.
     * 
     * @param path
     * @throws IOException
     *             any IOException
     * @throws IllegalArgumentException
     *             if file doesn't exits or cannot be deserialized
     */
    public void loadConfig() throws IOException, IllegalArgumentException;

    /**
     * This method increases zoom
     * 
     * @param event
     */
    public void zoomUp();

    /**
     * This method decreases zoom
     * 
     * @param event
     */
    public void zoomDown();

    /**
     * Slide simulation when user press W
     * 
     * @param event
     */
    public void wSlide();

    /**
     * Slide simulation when user press S
     * 
     * @param event
     */
    public void sSlide();

    /**
     * Slide simulation when user press A
     * 
     * @param event
     */
    public void aSlide();

    /**
     * Slide simulation when user press D
     * 
     * @param event
     */
    public void dSlide();

}
