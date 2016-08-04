package atlas.view;

import java.util.List;
import java.util.Optional;

import atlas.controller.Controller;
import atlas.controller.ControllerImpl;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class View2 extends Application implements View {
	
	private Optional<Body> selectedBody;
	private Optional<MouseEvent> lastMouseEvent;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(List<Body> b) {
	
	}

	@Override
	public void notifyObservers(SimEvent event) {
		ControllerImpl.getIstanceOf().update(event);
	}
	
	public Optional<MouseEvent> getLastMouseEvent() {
		Optional<MouseEvent> mev = this.lastMouseEvent;
		this.lastMouseEvent = Optional.empty();
		return mev;
	}
	
	public void resetViewLayout() {
		//do something
	}

	@Override
	public Optional<Body> getSelectedBody() {
		return this.selectedBody;
	}

	@Override
	public Pair<Integer, Units> getSpeedInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSaveName() {
		// TODO Auto-generated method stub
		return null;
	}
	//zoom with mouse
//	rootPane.setOnScroll( e-> {
//    	if (e.getDeltaY() > 0) {
//    		System.out.println("------------------------UP-----------------------------");
//    		this.unit = c.zoomUp();
//    	} else {
//    		System.out.println("------------------------Down-----------------------------");
//    		this.unit = c.zoomDown();
//    	}
//    });



	
	
}
