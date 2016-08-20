package atlas.view;

import java.util.List;
import java.util.Optional;

import atlas.controller.Controller;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewImpl implements View {
	
	private static View view; 
	// Visualization fields
	private double scale = 1.4000000000000000E-9;
	private Pair<Double, Double> translate = new Pair<>(new Double(0), new Double(0));
	private SceneMain mainScene;
	private SceneLoading loadingScene;

	// inputs fields
	private Optional<Body> selectedBody;
	private Optional<MouseEvent> lastMouseEvent;
	private List<Body> bodyList;

	private Controller ctrl;
	private Stage stage;

	public ViewImpl(Controller c, Stage primaryStage) {
		view = this;
		this.ctrl = c;
		this.stage = primaryStage;
		this.mainScene = new SceneMain();
		this.loadingScene = new SceneLoading();
		this.stage.setScene(loadingScene);
		this.stage.getIcons().add(SceneLoading.LOGO.getImage());

		primaryStage.setOnCloseRequest(e -> {
			View.onClose();
			e.consume();
		});
		primaryStage.show();
	}

    public static View getView() {
        if (view == null) {
            throw new IllegalStateException("View not initialized! ERROR");
        }
        return view;
    }

    @Override
    public void render(List<Body> b, String time, int fps) {
        this.bodyList = b;
        if (mainScene != null) {
            Platform.runLater(() -> {
                mainScene.draw(b, scale, translate, time, fps);
                if (!isMainScene()) {
                    this.switchToMainScene();
                }
            });
        }
    }

    @Override
    public void notifyObservers(SimEvent event) {
        this.ctrl.update(event);
    }

    @Override
    public Controller getController() {
        if (this.ctrl == null) {
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
        Pair<Integer, Units> p = new Pair<>(Integer.parseInt(c.textSpeed.getText()), c.choiceSpeedUnit.getValue());
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
    public synchronized void updateReferce(Pair<Double, Double> translate, double scale) {
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

    @Override
    public void setNextBodyToAdd(Body body) { // ToChangeName
        System.out.println("Step 2 nextbody toadd");
        this.selectedBody = Optional.of(body);

    }

    @Override
    public List<Body> getBodies() {
        return this.bodyList;
    }

    @Override
    public void deleteNextBody() {
        this.selectedBody = Optional.empty();

    }

    @Override
    public Pair<Double, Double> getWindow() {
        return new Pair<>(this.mainScene.renderPanel.getWidth() / 2, this.mainScene.renderPanel.getHeight() / 2);
    }
}
