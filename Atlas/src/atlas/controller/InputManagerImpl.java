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

import atlas.controller.InputManagerImpl.Status;
import atlas.model.Model;
import atlas.model.ModelImpl;
import atlas.utils.Pair;
import atlas.view.View;

public class InputManagerImpl implements InputManager {

    private View view;
    private Model model;
    private GameLoop gLoop;
    private DragPositions thread;
    private Status status = Status.DEFAULT;

    double scale = 1.4000000000000000E-9;
    Pair<Double, Double> reference;
    private static final String path = System.getProperty("user.dir") + "/res/Save/";

    public InputManagerImpl(View view, Model model, GameLoop gLoop, Pair<Double, Double> reference) {
        this.view = view;
        this.model = model;
        this.gLoop = gLoop;
        this.reference = reference;
    }

    @Override
    public void changeStatus(Status status) {
        this.status = status;
    }

    @Override
    public void addMode() {
        if (!this.view.getSelectedBody().equals(Optional.empty())) {
            this.status = Status.ADDING;
            this.view.getSelectedBody().get().setAttracting(false);
            this.model.addBody(this.view.getSelectedBody().get());
            this.thread = new DragPositions(this.view.getSelectedBody().get(), this.scale);
            this.thread.run();
        }
    }

    @Override
    public void mouseClicked() {
        if (this.status.equals(Status.ADDING)) {
            this.thread.interrupt();
            this.view.getSelectedBody().get().setAttracting(true);
        }
        this.status = Status.DEFAULT;
    }

    @Override
    public void mousePressed() {
        if (this.status.equals(Status.EDIT)) {
            if (!this.view.getSelectedBody().equals(Optional.empty())) {
                this.thread = new DragPositions(this.view.getSelectedBody().get(), this.scale);
                this.thread.run();
            }
        }
    }

    @Override
    public void mouseReleased() {
        if (this.status.equals(Status.EDIT)) {
            this.thread.interrupt();
        }
        this.status = Status.DEFAULT;
    }

    @Override
    public void ESC() {
        if (this.status.equals(Status.ADDING)) {
            this.thread.interrupt();
        }
        this.status = Status.DEFAULT;
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

    @Override
    public void wSlide() {
        this.reference = new Pair<Double, Double>(this.reference.getX(), this.reference.getY() + 25);
        this.view.updateReferce(this.reference, this.scale);
    }

    @Override
    public void sSlide() {
        this.reference = new Pair<Double, Double>(this.reference.getX(), this.reference.getY() - 25);
        this.view.updateReferce(this.reference, this.scale);
    }

    @Override
    public void aSlide() {
        this.reference = new Pair<Double, Double>(this.reference.getX() + 25, this.reference.getY());
        this.view.updateReferce(this.reference, this.scale);
    }

    @Override
    public void dSlide() {
        this.reference = new Pair<Double, Double>(this.reference.getX() - 25, this.reference.getY());
        this.view.updateReferce(this.reference, this.scale);
    }

    public enum Status {
        DEFAULT, ADDING, EDIT
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
    public void saveConfig(String name) throws IOException, IllegalArgumentException {
        String dir = path + name;
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
    public void loadConfig(String name) throws IOException, IllegalArgumentException {
        String dir = path + name;
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
}
