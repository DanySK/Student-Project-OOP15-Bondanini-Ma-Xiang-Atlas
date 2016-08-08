package atlas.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import atlas.model.Body;
import atlas.model.EpochJ2000;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TestUI extends Application implements View{
	
	private static int HEIGHT = 720;
	private static int WIDTH = 1280;
	
	private final double scale = 1.4000000000000000E-9;
	private final Pair<Double, Double> translate = new Pair<>(new Double(WIDTH/2), new Double(HEIGHT/2));
	
	private static SceneMain scene;
//	private static RenderScreen RENDERER = new RenderScreen(WIDTH, HEIGHT);
	
	public TestUI() {
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 primaryStage.setTitle("Testing UI");
		 primaryStage.setX(0);
	     primaryStage.setY(0);
		 primaryStage.setWidth(WIDTH);
		 primaryStage.setHeight(HEIGHT);
		 
		 
		 SceneMain s = new SceneMain(WIDTH, HEIGHT);
		 primaryStage.setScene(s);
		 
		 setRenderer(s);
		 
		 this.setOnClose(primaryStage);
		 primaryStage.show();	
	}
	
	private static void setRenderer(SceneMain r) {
		scene = r;
	}
	
	private void setOnClose(Stage primaryStage) {
		primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
	}
	
	public static void main(String s[]) {
		launch(TestUI.class);
	}

	@Override
	public void render(List<Body> b) {
		if(scene != null) {
			
			Platform.runLater(() -> TestUI.scene.draw(b, scale, translate) );
		}
	}

	@Override
	public Optional<Body> getSelectedBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MouseEvent> getLastMouseEvent() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void resetViewLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReferce(Pair<Double, Double> newReference, double newScale) {
//		this.scale = newScale;
//		this.translate = newReference;
	}

	@Override
	public Pair<Double, Double> getReference() {
		return this.translate;
	}
}
