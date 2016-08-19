package atlas.controller;

import java.awt.MouseInfo;
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
import atlas.model.Model;
import atlas.model.ModelImpl;
import atlas.utils.Pair;
import atlas.utils.Units;
import atlas.view.SimEvent;
import atlas.view.View;

public class InputManagerImpl implements InputManager {

    private View view;
    private Model model;
    private GameLoop gLoop;
    private DragPositions threadDrag;
    private LockPosition threadLock;
    private Status status = Status.DEFAULT;

    double scale = 1.4000000000000000E-9;
    Pair<Double, Double> reference;
    Pair<Double, Double> initialReference;
    private static final String path = System.getProperty("user.dir") + "/res/Save/";

    public InputManagerImpl(View view, Model model, GameLoop gLoop, Pair<Double, Double> reference) {
        this.view = view;
        this.model = model;
        this.gLoop = gLoop;
        this.initialReference = reference;
        this.reference = reference;
        this.threadLock = new LockPosition(this.view, this.scale);
    }

    @Override
    public void addMode() {
        if (!this.view.getSelectedBody().equals(Optional.empty())) {
            this.status = Status.ADDING;
        }
    }

    @Override
    public void mouseClicked() {
        if (this.status.equals(Status.ADDING)) {
            this.view.getSelectedBody().get().setPosX(
                    (MouseInfo.getPointerInfo().getLocation().getX() - this.view.getWindow().getX() - this.reference.getX() - 100) / this.scale);
            this.view.getSelectedBody().get().setPosY(
                    (MouseInfo.getPointerInfo().getLocation().getY() - this.view.getWindow().getY() - this.reference.getY() - 25) / -this.scale);
            this.gLoop.setNextBodyToAdd(this.view.getSelectedBody().get());
        }
        this.status = Status.DEFAULT;
    }

    @Override
    public void mousePressed() {
        if (this.status.equals(Status.EDIT)) {
        }
    }

    @Override
    public void mouseReleased() {
        if (this.status.equals(Status.EDIT)) {
            this.threadDrag.interrupt();
        }
        this.status = Status.DEFAULT;
    }

    @Override
    public void ESC() {
        if (this.status.equals(Status.ADDING)) {
            this.view.deleteNextBody();
        } else if (this.status.equals(Status.LOCK)) {
            this.threadLock.stopLock();
            System.out.println("STOOPP");
        }
        this.status = Status.DEFAULT;
    }

    @Override
    public void zoomUp() {
        this.scale *= 1.05;
        if (this.threadLock.isAlive()) {
            this.threadLock.setScale(this.scale);
        } else {
            this.view.updateReferce(this.reference, this.scale);
        }
    }

    @Override
    public void zoomDown() {
        this.scale *= 0.95;
        if (this.threadLock.isAlive()) {
            this.threadLock.setScale(this.scale);
        } else {
            this.view.updateReferce(this.reference, this.scale);
        }
    }

    @Override
    public void wSlide() {
        this.checkLocked();
        this.reference = new Pair<Double, Double>(this.reference.getX(), this.reference.getY() + 25);
        this.view.updateReferce(this.reference, this.scale);
        this.setDefault();
    }

    @Override
    public void sSlide() {
        this.checkLocked();
        this.reference = new Pair<Double, Double>(this.reference.getX(), this.reference.getY() - 25);
        this.view.updateReferce(this.reference, this.scale);
        this.setDefault();
    }

    @Override
    public void aSlide() {
        this.checkLocked();
        this.reference = new Pair<Double, Double>(this.reference.getX() + 25, this.reference.getY());
        this.view.updateReferce(this.reference, this.scale);
        this.setDefault();
    }

    @Override
    public void dSlide() {
        this.checkLocked();
        this.reference = new Pair<Double, Double>(this.reference.getX() - 25, this.reference.getY());
        this.view.updateReferce(this.reference, this.scale);
        this.setDefault();
        
    }

    @Override
    public void spaceBar() {
        if (this.gLoop.getStatus().equals(StatusSim.RUNNING)) {
            this.gLoop.setStopped();
        } else {
            this.gLoop.setRunning();
        }
    }

    @Override
    public void saveConfig() throws IOException, IllegalArgumentException {
        String dir = path + this.view.getSaveName();
        File f = new File(dir);
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
    public void loadConfig() throws IOException, IllegalArgumentException {
        String dir = path + this.view.getSaveName();
        File f = new File(dir);
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

    @Override
    public void changeSpeed() {
        Pair<Integer, Units> speed = this.view.getSpeedInfo();
        this.gLoop.setValue(speed.getY().getValue(), speed.getX());
    }

    @Override
    public void changeStatus(Status status) {
        this.status = status;

    }

    @Override
    public void initialReference() {
        this.reference = this.initialReference;
        this.view.updateReferce(this.reference, this.scale);
    }

    @Override
    public void lockVenuse() {
        if(!this.status.equals(Status.LOCK)) {
        this.threadLock = new LockPosition(this.view, this.scale);
        this.threadLock.start();
        }
    }

    private void checkLocked() {
        if (this.status.equals(Status.LOCK)) {
            this.threadLock.stopLock();
        }
    }
    
    private void setDefault() {
        this.status = Status.DEFAULT;
    }
}
