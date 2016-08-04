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
    private volatile Model model; // check volatile
    private DragPositions thread;

    private static final String path = System.getProperty("user.dir") + "/res/Save/";
    double posy = 1;
    double scale = 1.4000000000000000E-9;
    boolean bool = true;
    private volatile Status status = Status.DEFAULT; // volatile?
    private Pair<Double, Double> reference;

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
        this.reference = this.view.getReference();
        this.gLoop.setView(v);
    }

    @Override
    public void update(SimEvent event) {
        switch (event) {
        case ADD:
            if (!this.view.getSelectedBody().equals(Optional.empty())) {
                this.status = Status.ADDING;
                this.view.getSelectedBody().get().setAttracting(false);
                this.model.addBody(this.view.getSelectedBody().get());
                this.thread = new DragPositions(this.view.getSelectedBody().get(), this.scale);
                this.thread.run();
            }
            break;

        case EDIT:
            this.status = Status.EDIT;
            break;

        case SAVE:
            try {
                String dir = path + view.getSaveName();
                this.saveConfig(dir);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;

        case LOAD:
            try {
                String dir = path + this.view.getSaveName();
                this.loadConfig(dir);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;

        case SPEED_CHANGED:
            try {
                Pair<Integer, Units> speed = this.view.getSpeedInfo();
                this.setSpeed(speed.getY(), speed.getX());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            break;

        case MOUSE_CLICKED:
            if (this.status.equals(Status.ADDING)) {
                this.thread.interrupt();
                this.view.getSelectedBody().get().setAttracting(true);
            }
            this.status = Status.DEFAULT;
            break;

        case MOUSE_PRESSED:
            if (this.status.equals(Status.EDIT)) {
                if (!this.view.getSelectedBody().equals(Optional.empty())) {
                    this.thread = new DragPositions(this.view.getSelectedBody().get(), this.scale);
                    this.thread.run();
                }
            } else if (this.status.equals(Status.DEFAULT)) {
                // spostamento con il mouse nella simulazione... modifica le
                // posizioni di tutti...
            }
            break;

        case MOUSE_RELEASED:
            if (this.status.equals(Status.EDIT)) {
                this.thread.interrupt();
            }
            this.status = Status.DEFAULT;
            break;

        case MOUSE_WHEEL_UP:
            this.zoomUp();
            break;

        case MOUSE_WHEEL_DOWN:
            this.zoomDown();
            break;

        case ESC:
            if (this.status.equals(Status.ADDING) || this.status.equals(Status.EDIT)) {
                this.status = Status.DEFAULT;
            }
            
        case W:
            this.wSlide();
            break;

        case A:
            this.aSlide();
            break;

        case S:
            this.sSlide();
            break;

        case D:
            this.dSlide();
            break;

        case SPACEBAR_PRESSED:
            if (this.gLoop.getStatus().equals(StatusSim.RUNNING)) {
                this.gLoop.setStopped();
            } else {
                this.gLoop.setRunning();
            }
            break;

        default:
            break;
        }
    }

    @Override
    public double getUnit() {
        return this.scale;
    }

    @Override
    public void zoomUp() {
        this.scale *= 1.05;
        this.view.updateReferce(this.reference, this.scale);
    }

    @Override
    public void zoomDown() {
        this.scale *= 0.95;
        this.view.updateReferce(this.reference, this.scale);
    }
    
    public void wSlide() {
        this.reference = new Pair<Double, Double> (this.reference.getX(), this.reference.getY()+25);
        this.view.updateReferce(this.reference, this.scale);
    }
    
    public void sSlide() {
        this.reference = new Pair<Double, Double> (this.reference.getX(), this.reference.getY()-25);
        this.view.updateReferce(this.reference, this.scale);
    }
    
    public void aSlide() {
        this.reference = new Pair<Double, Double> (this.reference.getX()-25, this.reference.getY());
        this.view.updateReferce(this.reference, this.scale);
    }
    
    public void dSlide() {
        this.reference = new Pair<Double, Double> (this.reference.getX()+25, this.reference.getY());
        this.view.updateReferce(this.reference, this.scale);      
    }

    @Override
    public void setSpeed(Units unit, int speed) throws IllegalArgumentException {
        if (speed <= 1000 && speed > 0) {
            switch (unit) {
            case MIN_SEC:
                gLoop.setValue(Units.MIN_SEC.getValue(), speed);
                break;

            case HOUR_SEC:
                gLoop.setValue(Units.HOUR_SEC.getValue(), speed);
                break;

            case DAY_SEC:
                gLoop.setValue(Units.DAY_SEC.getValue(), speed);
                break;

            case YEAR_SEC:
                gLoop.setValue(Units.YEAR_SEC.getValue(), speed);
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
            ostream.writeLong(this.gLoop.getUnit());
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
            long unit = ostream.readLong();
            int speed = ostream.readInt();
            this.gLoop.setModel(model);
            gLoop.setValue(unit, speed);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Content of the file is not suitable.");
        }
    }

    private boolean checkFileExists(File f) {
        return f.exists() && !f.isDirectory();
    }

    private enum Status {
        DEFAULT, ADDING, EDIT
    }

}
