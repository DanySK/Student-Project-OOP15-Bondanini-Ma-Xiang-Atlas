package atlas.controller;

import java.util.List;

import atlas.model.Body;
import atlas.view.ViewInterface;

public class Controller implements ControllerInterface {
    
    private GameLoop gLoop;
    
    public Controller(ViewInterface v){ 
        gLoop = new GameLoop(v);
    }


    @Override
    public void startSim() {
        // TODO Auto-generated method stub   
        gLoop.setRunning();
        gLoop.start();
    }

    @Override
    public void stopSim() {
        // TODO Auto-generated method stub
        gLoop.setStopped();

    }
    
    public void exit() {
        gLoop.setExit();
    }

    /*@Override
    public void resumeSim() {
        // TODO Auto-generated method stub

    }*/

    @Override
    public List<Body> getBodiesPositionToRender() {
        // TODO Auto-generated method stub
        return null;
    }

}
