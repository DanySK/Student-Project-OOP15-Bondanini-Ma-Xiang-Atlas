package atlas.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    double posy = 1;
    double unit = -1.4000000000000000E-9;
    boolean bool = true;

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

            this.nextBody.setPosX(posX.get() * unit);
            this.nextBody.setPosY(posY.get() * unit);
            this.model.addBody(nextBody);
            this.adding = false;
            this.nextBody = null;

        case SAVE:
            this.saveConfig(path);

        case LOAD:
            this.loadConfig(path);

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

    @Override
    public double getUnit() {
        return this.unit;
    }

    @Override
    public double zoomUp() {
        this.unit -= 0.4000000000000000E-9;
        return this.unit;

    }

    @Override
    public double zoomDown() {
        this.unit += 0.4000000000000000E-9;
        return this.unit;
    }

    @Override
    public void saveConfig(String path) throws IOException, IllegalArgumentException {
        File f = new File(path);
        if (!this.checkFileExists(f)) {
            throw new IllegalArgumentException();
        }

        try (OutputStream bstream = new BufferedOutputStream(new FileOutputStream(f));
                ObjectOutputStream ostream = new ObjectOutputStream(bstream);) {
            ostream.writeObject(this.model.getClock());
            ostream.writeObject(this.unit);
            //Speed
            ostream.writeObject(this.model.getBodiesToRender());
        }

    }

    @Override
    public void loadConfig(String path) throws IOException, IllegalArgumentException {
        File f = new File(path);
        if (!this.checkFileExists(f)) {
            throw new IllegalArgumentException();
        }

        try (InputStream bstream = new BufferedInputStream(new FileInputStream(f));
                ObjectInputStream ostream = new ObjectInputStream(bstream);) {
            this.model.setClock((SimClock) ostream.readObject());
            this.unit = (Double) ostream.readObject();
            while (true) {
                try {
                    this.model.addBody(((Body) ostream.readObject()));
                } catch (Exception e) {
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Content of the file is not suitable.");
        }
    }

    /* True : exists, false otherwise */
    private boolean checkFileExists(File f) {
        return f.exists() && !f.isDirectory();
    }

}
