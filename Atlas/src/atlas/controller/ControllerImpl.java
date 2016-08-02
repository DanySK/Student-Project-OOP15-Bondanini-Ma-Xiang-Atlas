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
    private volatile Model model; //check volatile

    private static final String path = System.getProperty("user.dir") + "/res/Save/";
    double posy = 1;
    double scale = 1.4000000000000000E-9;
    boolean bool = true;
    private Status status = Status.DEFAULT;
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
    public void update(SimEvent event) {
        switch (event) {
        
        case ADD:
            this.status = Status.ADDING;
            break;
            
        case MOUSE_CLICKED:
            if(this.status.equals(Status.ADDING)) {
                Body nextBodyToAdd = view.getSelectedBody().get();
                nextBodyToAdd.setPosX(this.view.getLastMouseEvent().get().getSceneX() * scale);
                nextBodyToAdd.setPosX(this.view.getLastMouseEvent().get().getSceneY() * scale);
                this.model.addBody(nextBodyToAdd);            
            }
            break;

        case SAVE: 
            try {
                /* String name = view.getNameToSave(); 
                 * String dir = path+name*/
                this.saveConfig(dir);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;

        case LOAD:         
            try {
                /*String name = view.getNameToLoad();
                 * String dir = path+name;*/
                this.loadConfig(dir);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
            
        case ZOOMUP:
            this.zoomUp();
            break;
            
        case ZOOMDOWN:
            this.zoomDown();
            break;
            
        default:
            break;
        }
    }

    @Override
    public void setNextBody(Body body) {
        this.nextBodyToAdd = body;
    }

    @Override
    public void reset() {
        this.nextBodyToAdd = null;
        this.adding = false;
    }

    @Override
    public double getUnit() {
        return this.scale;
    }

    @Override
    public double zoomUp() {
        this.scale = scale * 1.05;
        return this.scale;

    }

    @Override
    public double zoomDown() {
        this.scale = scale * 0.95;
        return this.scale;
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
            ostream.writeDouble(this.scale);
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
            this.model = (Model) ostream.readObject();
            this.scale = ostream.readDouble();
            long ui = ostream.readLong();
            int speed = ostream.readInt();
            this.gLoop.setModel(model);
            gLoop.setValue(ui, speed);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Content of the file is not suitable.");
        }
    }

    private boolean checkFileExists(File f) {
        return f.exists() && !f.isDirectory();
    }
    
    private enum Status {
        DEFAULT, ADDING
    }

}
