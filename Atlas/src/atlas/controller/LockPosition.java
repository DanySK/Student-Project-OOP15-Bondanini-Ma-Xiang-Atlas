package atlas.controller;

import atlas.utils.Pair;
import atlas.view.View;

public class LockPosition extends Thread{
	
	private View view;
	private static final int step = 33;
	private double scale;
	
	public LockPosition(View view, double scale) {
		this.view = view;
		this.scale = scale;
	}
	
	public void run() {
		while(true) {
			System.out.println("oooo");
			double actualScale = this.scale;
			long last = System.currentTimeMillis();
			Pair<Double, Double> coordinate = new Pair<>(this.view.getSelectedBody().get().getPosX() / actualScale, this.view.getSelectedBody().get().getPosX() / actualScale);
			this.view.updateReferce(coordinate, actualScale);
            while(System.currentTimeMillis()-last < step) {    	
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

}
