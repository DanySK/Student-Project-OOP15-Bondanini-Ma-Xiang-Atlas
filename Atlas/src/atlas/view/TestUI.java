package atlas.view;

import java.util.List;
import java.util.Optional;

import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TestUI extends Application implements View{
	
	private static int HEIGHT = 720;
	private static int WIDTH = 1280;
	
	private double scale;
	private Pair<Double, Double> translate;
	
	private SceneMain scene;

	public TestUI() {
		Double initialTran = new Double(0);
		this.translate = new Pair<>(initialTran, initialTran);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 primaryStage.setTitle("Testing UI");
		 primaryStage.setWidth(WIDTH);
		 primaryStage.setHeight(HEIGHT);
		 
		 
		 this.scene = new SceneMain(WIDTH, HEIGHT);
		 primaryStage.setScene(this.scene);		 
	     primaryStage.show();	       
	     this.setOnClose(primaryStage);
	}
	
	private void setOnClose(Stage primaryStage) {
		primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        });
	}
	
	public static void main(String s[]) {
		launch(TestUI.class);
	}

	@Override
	public void render(List<Body> b) {
//		b.forEach(i -> {
//			System.out.println(i);
//		});
		System.out.println("Scene = " + scene);
		System.out.println("scale = " + scale);
		System.out.println("translate = " + translate);
		if(scene == null){
			scene = new SceneMain(WIDTH, HEIGHT);
		}
		this.scene.draw(b, scale, translate);
	}

	@Override
	public void notifyObservers(SimEvent event) {
		// TODO Auto-generated method stub
		
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
		this.scale = newScale;
		this.translate = newReference;
	}

	@Override
	public Pair<Double, Double> getReference() {
		return this.translate;
	}
}
