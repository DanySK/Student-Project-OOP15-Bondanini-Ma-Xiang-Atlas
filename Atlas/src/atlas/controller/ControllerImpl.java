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
        gLoop.start();
    }
    
    public static ControllerImpl getIstanceOf() {
        return (ControllerImpl.ctrl == null ? new ControllerImpl() : ControllerImpl.ctrl);
    }

    @Override
    public void startSim() {
        gLoop.setRunning();
        
    }

    @Override
    public void stopSim() {
        gLoop.setStopped();
    }

    @Override
    public void exit() {
        gLoop.setExit();
    }

    @Override
    public List<Body> getBodiesPositionToRender() {
        return null;
    }
    
    public void setView (View v) {
        this.gLoop.setView(v);
    }

}
