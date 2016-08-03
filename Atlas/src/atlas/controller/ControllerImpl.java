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

        case EDIT:
            this.status = Status.EDIT;
            break;

        case MOUSE_CLICKED:
            break;

        case MOUSE_PRESSED:
            if (this.status.equals(Status.EDIT)) {
                if (!this.view.getSelectedBody().equals(Optional.empty())) {
                    this.thread = new DragPositions(this.view.getSelectedBody().get(), this.scale);
                    this.thread.run();
                }
            } else if (this.status.equals(Status.ADDING)) {
                if (!this.view.getSelectedBody().equals(Optional.empty())) {
                    view.getSelectedBody().get().setAttracting(false);
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
                this.view.getSelectedBody().get().setAttracting(true);
                
            } else if (this.status.equals(Status.ADDING)) {
                this.thread.interrupt();
                this.model.addBody(this.view.getSelectedBody().get());
                this.view.getSelectedBody().get().setAttracting(true);
            }
            this.status = Status.DEFAULT;
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

        case ESC:
            if (this.status.equals(Status.ADDING)) {
                this.status = Status.DEFAULT;
            }

        case SPACEBAR_PRESSED:
            if (this.gLoop.getStatus().equals(StatusSim.RUNNING)) {
                this.gLoop.setStopped();
            } else {
                this.gLoop.setRunning();
            }
            break;

        case MOUSE_WHEEL_UP:
            this.zoomUp();
            break;

        case MOUSE_WHEEL_DOWN:
            this.zoomDown();
            break;

        case SPEED_CHANGED:
            try {
                Pair<Integer, Units> speed = this.view.getSpeedInfo();
                this.setSpeed(speed.getY(), speed.getX());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
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
    public void setSpeed(Units unit, int speed) throws IllegalArgumentException {
        if (speed <= 1000 && speed > 0) {
            switch (unit) {
            case Min_Sec:
                gLoop.setValue(Units.Min_Sec.getValue(), speed);
                break;

            case Hour_Sec:
                gLoop.setValue(Units.Hour_Sec.getValue(), speed);
                break;

            case Day_Sec:
                gLoop.setValue(Units.Day_Sec.getValue(), speed);
                break;

            case Year_Sec:
                gLoop.setValue(Units.Year_Sec.getValue(), speed);
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
