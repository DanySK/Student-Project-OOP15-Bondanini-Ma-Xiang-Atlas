package atlas.controller;

import java.io.IOException;
import java.util.List;
import atlas.model.*;
import atlas.view.View;
import atlas.utils.*;

/**
 * Implementation of ControllerInterface
 * 
 * @author andrea
 *
 */
public class ControllerImpl implements Controller {

    private GameLoop gLoop;
    private static ControllerImpl ctrl = null;
    private View view;
    private Model model;

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

    public void setView(View v) {
        this.view = v;
        this.gLoop.setView(v);
    }

    @Override
    public void update(final EventType event, final String path) throws IllegalArgumentException, IOException {
        switch (event) {

        case SAVE:
            this.model.saveConfig(path);

        case LOAD:
            this.model.loadConfig(path);

        default:
        }
    }

}
