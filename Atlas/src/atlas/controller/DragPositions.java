package atlas.controller;

import java.awt.MouseInfo;

import atlas.model.Body;
import atlas.utils.Pair;
import atlas.view.ViewImpl;

/**
 * This thread sets the position of body to move. 
 * 
 * @author andrea
 */

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
        	System.out.println("DragLoop");
            double actualScale = this.scale;
            long last = System.currentTimeMillis();
            while (System.currentTimeMillis() - last < step) {
                ViewImpl.getView().getSelectedBody().get().setPosX((MouseInfo.getPointerInfo().getLocation().getX()
                        - ViewImpl.getView().getRenderScreenOrig().getX() - this.reference.getX()) / actualScale);
                ViewImpl.getView().getSelectedBody().get().setPosY((MouseInfo.getPointerInfo().getLocation().getY()
                        - ViewImpl.getView().getRenderScreenOrig().getY() - this.reference.getY()) / -actualScale);
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
