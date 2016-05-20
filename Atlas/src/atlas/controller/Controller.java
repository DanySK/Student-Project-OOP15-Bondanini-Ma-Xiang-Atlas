package atlas.controller;

import java.util.List;
import atlas.model.Body;
import atlas.view.ViewInterface;

/**
 * Implementation of ControllerInterface
 * 
 * @author andrea
 *
 */
public class Controller implements ControllerInterface {

    private GameLoop gLoop;
    private static Controller ctrl = null;
    
    

    /**
     * Creation of new Controller
     * 
     * @param v
     *            ViewInterface Object
     */
    private Controller() {
        gLoop = new GameLoop();      
    }
    
    public static Controller getIstanceOf() {
        return (Controller.ctrl == null ? new Controller() : Controller.ctrl);
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
    
    public void setView (ViewInterface v) {
        this.gLoop.setView(v);
    }

}
