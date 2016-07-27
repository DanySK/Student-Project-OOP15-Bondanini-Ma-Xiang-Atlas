package atlas.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import atlas.model.*;
import atlas.model.Body.Properties;
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

    private boolean adding = false; // If not work try to set True
    private Body nextBody = null;

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

    public void setView(View v) {
        this.view = v;
        this.gLoop.setView(v);
    }

    @Override
    public void update(final EventType event, String path, Optional<Double> posX, Optional<Double> posY)
            throws IllegalArgumentException, IOException {
        switch (event) {
        case ADDING_BODY:
            System.out.println("PosX: " + posX + " PosY:" + posY);
            this.nextBody.setPosX(posX.get());
            this.nextBody.setPosY(posY.get());
            this.model.addBody(nextBody);
            this.adding = false;
            this.nextBody = null;

        case SAVE:
            this.model.saveConfig(path);

        case LOAD:
            this.model.loadConfig(path);

        default:
        }
    }

    @Override
    public void setAdding() {
        this.adding = true;
    }

    @Override
    public void setNotAdding() {
        this.adding = false;
    }

    @Override
    public boolean isAdding() {
        return this.adding;
    }

    @Override
    public void setNextBody(Body body) {
        this.nextBody = body;
    }

    @Override
    public void reset() {
        this.nextBody = null;
        this.adding = false;

    }

}
