package atlas.view;

import java.util.List;
import java.util.Optional;

import atlas.controller.Controller;
import atlas.controller.ControllerImpl;
import atlas.model.Body;
import javafx.scene.control.Button;

public class View2 implements View {
	
	private Optional<Body> selectedBody; 
	
	@Override
	public void render(List<Body> b) {
		
	}

	@Override
	public void notifyObservers(SimEvent event) {
		ControllerImpl.getIstanceOf().update(event);
	}

	@Override
	public Optional<Body> getSelectedBody() {
		return this.selectedBody;
	}

	
	
}
