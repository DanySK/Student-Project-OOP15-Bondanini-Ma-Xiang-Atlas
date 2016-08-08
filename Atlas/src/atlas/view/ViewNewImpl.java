package atlas.view;

import java.util.List;
import java.util.Optional;

import atlas.controller.ControllerImpl;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewNewImpl extends Application implements View {

	// Visualization fields
	private static int HEIGHT = 720;
	private static int WIDTH = 1280;
	private double scale = 1.4000000000000000E-9;
	private Pair<Double, Double> translate = new Pair<>(new Double(WIDTH / 2), new Double(HEIGHT / 2));
	private static SceneMain mainScene;

	// inputs fields
	private Optional<Body> selectedBody;
	private Optional<MouseEvent> lastMouseEvent;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Testing UI");
		primaryStage.setX(0);
		primaryStage.setY(0);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);

		SceneMain scene = new SceneMain(WIDTH, HEIGHT);
		primaryStage.setScene(scene);
		setSceneMain(scene);

		this.setOnClose(primaryStage);
		primaryStage.show();
	}

	private static void setSceneMain(SceneMain s) {
		ViewNewImpl.mainScene = s;
		mainScene.renderPanel.setOnScroll( e-> {
    	if (e.getDeltaY() > 0) {
    		System.out.println("------------------------UP-----------------------------");
    		View.notifyObservers(SimEvent.MOUSE_WHEEL_UP);
    	} else {
    		System.out.println("------------------------Down-----------------------------");
    		View.notifyObservers(SimEvent.MOUSE_WHEEL_UP);
    	}
    });
	}

	private void setOnClose(Stage primaryStage) {
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
	}

	@Override
	public void render(List<Body> b) {
		if (ViewNewImpl.mainScene != null) {
			Platform.runLater(() -> ViewNewImpl.mainScene.draw(b, scale, translate));
		}
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
		CruiseControl c = ViewNewImpl.mainScene.cruise;
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
}
