package atlas.controller;

import java.util.List;

import atlas.model.Body;

public interface ControllerInterface {
    
    
    public void startSim();
    
    public void stopSim();
    
    public void exit();
    
    //public void resumeSim();
    
    public List<Body> getBodiesPositionToRender();
    
    
    

}
