package atlas.view;

import java.util.List;
import java.util.Optional;

import atlas.controller.Controller;
import atlas.controller.ControllerImpl;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewNewImpl implements View {

	// Visualization fields
	private static int HEIGHT = 720;
	private static int WIDTH = 1280;
	private double scale = 1.4000000000000000E-9;
	private Pair<Double, Double> translate = new Pair<>(new Double(WIDTH / 2), new Double(HEIGHT / 2));
	private SceneMain mainScene;
	private SceneLoading loadingScene;

	// inputs fields
	private Optional<Body> selectedBody;
	private Optional<MouseEvent> lastMouseEvent;
	
	private Controller ctrl;
	private Stage stage;
	
	public ViewNewImpl(Controller c, Stage primaryStage) {
		this.ctrl = c;
		this.stage = primaryStage;
		this.mainScene = new SceneMain(this, WIDTH, HEIGHT);
		this.stage.setScene(mainScene);
		setSceneMain();
	}

	private void setSceneMain() {
		mainScene.renderPanel.setOnScroll( e-> {
    	if (e.getDeltaY() > 0) {
    		System.out.println("------------------------UP-----------------------------");
    		notifyObservers(SimEvent.MOUSE_WHEEL_UP);
    	} else {
    		System.out.println("------------------------Down-----------------------------");
    		notifyObservers(SimEvent.MOUSE_WHEEL_DOWN);
    	}
    });
	}

	@Override
	public void render(List<Body> b, String time, int fps) {
		if (mainScene != null) {
			Platform.runLater(() -> mainScene.draw(b, scale, translate, time, fps));
		}
	}

	@Override
	public void notifyObservers(SimEvent event) {
		this.ctrl.update(event);
	}

	@Override
	public Controller getController() {
		if(this.ctrl == null) {
			throw new IllegalStateException();
		}
		return this.ctrl;
	}

	@Override
	public Optional<Body> getSelectedBody() {
		return this.selectedBody;
	}

	@Override
	public Optional<MouseEvent> getLastMouseEvent() {
		return this.lastMouseEvent;
	}

	@Override
	public Pair<Integer, Units> getSpeedInfo() {
		CruiseControl c = mainScene.cruise;
		Pair<Integer, Units> p = new Pair<>(Integer.parseInt(c.textSpeed.getText()),
				c.choiceSpeedUnit.getValue());
		return p;
	}

	@Override
	public String getSaveName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetViewLayout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateReferce(Pair<Double, Double> translate, double scale) {
		this.scale = scale;
		this.translate = translate;
	}

	@Override
	public Pair<Double, Double> getReference() {
		return this.translate;
	}

	@Override
	public boolean isMainScene() {
		return this.stage.getScene().equals(mainScene);
	}

	@Override
	public void switchToMainScene() {
		this.stage.setScene(mainScene);		
	}

	@Override
	public void switchToLoadingScene() {
		this.stage.setScene(loadingScene);		
	}
}
