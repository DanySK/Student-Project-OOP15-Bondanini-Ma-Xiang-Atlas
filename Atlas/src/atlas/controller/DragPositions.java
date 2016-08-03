package atlas.controller;

import java.awt.MouseInfo;

import atlas.model.Body;

public class DragPositions extends Thread {

    private Body body;
    private double scale;
    private static final int step = 33;

    public DragPositions(Body bodyToDrag, double scale) {
        this.body = bodyToDrag;
        this.scale = scale;
    }

    public void run() {
        while (true) {
            long last = System.currentTimeMillis();
            this.body.setPosX(MouseInfo.getPointerInfo().getLocation().getX() * this.scale);
            this.body.setPosY(MouseInfo.getPointerInfo().getLocation().getY() * this.scale);
            while(System.currentTimeMillis()-last < step) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
           
        }
    }

}
