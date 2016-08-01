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
import java.util.Optional;
import atlas.model.*;
import atlas.view.SimEvent;
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
    private volatile Model model;
    
    private String path = System.getProperty("user.home")+System.getProperty("file.separator")+"ciao.bin";

    private boolean adding = false; // If not work try to set True
    private Body nextBody = null;
    double posy = 1;
    double unit = 1.4000000000000000E-9;
    boolean bool = true;
    private static final long MIN_UNIT = 60L;
    private static final long HOUR_UNIT = 3600L;
    private static final long DAY_UNIT = 86400L;
    private static final long YEAR_UNIT = 31536000L;

    /**
     * Creation of new Controller
     * 
     * @param v
     *            ViewInterface Object
     */
    private ControllerImpl() {
        this.model = new ModelImpl();
        gLoop = new GameLoop(model);
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
    public void update(SimEvent event)
            throws IllegalArgumentException, IOException {
        switch (event) {
        /*case ADDING_BODY:
            this.nextBody.setPosX(posX.get() * unit);
            this.nextBody.setPosY(posY.get() * unit);
            this.model.addBody(nextBody);
            this.adding = false;
            this.nextBody = null;
            break;

        case SAVE:
            this.saveConfig(path);
            break;

        case LOAD:
            this.loadConfig(path);
            break;*/

        default:
            break;
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
        this.unit = unit * 1.05;
        return this.unit;

    }

    @Override
    public double zoomDown() {
        this.unit = unit * 0.95;
        return this.unit;
    }

    @Override
    public void setSpeed(UI ui, int speed) throws IllegalArgumentException {
        if (speed <= 1000 || speed > 0) {
            switch (ui) {
            case Min_Sec:
                gLoop.setValue(MIN_UNIT, speed);
                break;

            case Hour_Sec:
                gLoop.setValue(HOUR_UNIT, speed); 
                break;

            case Day_Sec:
                gLoop.setValue(DAY_UNIT, speed);
                break;

            case Year_Sec:
                gLoop.setValue(YEAR_UNIT, speed);
                break;

            default:
                break;
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void saveConfig(String path) throws IOException, IllegalArgumentException {
        File f = new File(path);
        if (this.checkFileExists(f)) {
            throw new IllegalArgumentException();
        }
        
        try (OutputStream bstream = new BufferedOutputStream(new FileOutputStream(f));
                ObjectOutputStream ostream = new ObjectOutputStream(bstream);) {
            ostream.writeObject(this.model);
            ostream.writeDouble(this.unit);
            ostream.writeLong(this.gLoop.getUI());
            ostream.writeInt(this.gLoop.getSpeed());
        }
    }

    @Override
    public void loadConfig(String path) throws IOException, IllegalArgumentException {
        File f = new File(path);
        if (!this.checkFileExists(f)) {
            throw new IllegalArgumentException();
        }
        this.model = new ModelImpl();
        try (InputStream bstream = new BufferedInputStream(new FileInputStream(f));
                ObjectInputStream ostream = new ObjectInputStream(bstream);) {
            this.model = (Model)ostream.readObject();
            this.unit = ostream.readDouble();
            long ui = ostream.readLong();
            int speed = ostream.readInt();     
            this.gLoop.setModel(model);
            gLoop.setValue(ui, speed); 
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Content of the file is not suitable.");
        }
        
        
    }

    /* True : exists, false otherwise */
    private boolean checkFileExists(File f) {
        return f.exists() && !f.isDirectory();
    }

}
