package atlas.view;

import java.util.List;
import java.util.Optional;

import atlas.controller.Controller;
import atlas.controller.ControllerImpl;
import atlas.model.Body;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class View2 implements View {
	
	private Optional<Body> selectedBody;
	private ViewImpl view = new ViewImpl();
	
	@Override
	public void render(List<Body> b) {
		this.view.render(b);
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
