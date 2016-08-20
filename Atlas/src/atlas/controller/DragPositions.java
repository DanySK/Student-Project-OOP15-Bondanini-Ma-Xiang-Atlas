package atlas.controller;

import java.awt.MouseInfo;

import atlas.model.Body;
import atlas.utils.Pair;
import atlas.view.ViewImpl;

public class DragPositions extends Thread {

    private double scale;
    private Pair<Double, Double> reference;
    private static final int step = 33;
    private boolean bool = true;

    public DragPositions(double scale, Pair<Double, Double> reference) {
        this.scale = scale;
        this.reference = reference;
    }

    public void run() {
        while (bool) {
            double actualScale = this.scale;
            long last = System.currentTimeMillis();
            while (System.currentTimeMillis() - last < step) {
                ViewImpl.getView().getSelectedBody().get().setPosX((MouseInfo.getPointerInfo().getLocation().getX()
                        - ViewImpl.getView().getWindow().getX() - this.reference.getX() - 100) / actualScale);
                ViewImpl.getView().getSelectedBody().get().setPosY((MouseInfo.getPointerInfo().getLocation().getY()
                        - ViewImpl.getView().getWindow().getY() - this.reference.getY() - 25) / -actualScale);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

    public void stopEdit() {
        this.bool = false;
    }

}
