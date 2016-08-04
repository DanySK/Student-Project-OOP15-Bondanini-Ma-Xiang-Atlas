package atlas.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestUI extends Application{
	
	private static int HEIGHT = 720;
	private static int WIDTH = 1280;
	
	private SceneSim scene = new SceneSim(WIDTH,HEIGHT);
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		 primaryStage.setTitle("Testing UI");
		 primaryStage.setScene(this.scene);
		 
		 primaryStage.setWidth(WIDTH);
		 primaryStage.setHeight(HEIGHT);
	     primaryStage.show();	       
	     
	}
	
	public static void main(String s[]) {
		launch(TestUI.class);
	}
}
