package atlas.controller;

import java.util.List;
import atlas.model.Body;
import atlas.view.View;

/**
 * Implementation of ControllerInterface
 * 
 * @author andrea
 *
 */
public class ControllerImpl implements Controller {

    private GameLoop gLoop;
    private static ControllerImpl ctrl = null;
    
    

    /**
     * Creation of new Controller
     * 
     * @param v
     *            ViewInterface Object
     */
    private ControllerImpl() {
        gLoop = new GameLoop();      
    }
    
    public static ControllerImpl getIstanceOf() {
        return (ControllerImpl.ctrl == null ? new ControllerImpl() : ControllerImpl.ctrl);
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

    @Override
    public void exit() {
        gLoop.setExit();
    }

    @Override
    public List<Body> getBodiesPositionToRender() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void setView (View v) {
        this.gLoop.setView(v);
    }

}
