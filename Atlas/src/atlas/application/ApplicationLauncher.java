package atlas.application;

import atlas.controller.ControllerImpl;
import atlas.controller.Controller;
import atlas.view.View;
import atlas.view.ViewImpl;
import atlas.view.ViewNewImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * 
 * This class contains the main method that is used to launch the application.
 *
 */
public class ApplicationLauncher extends Application{
	
	private static int HEIGHT = 720;
	private static int WIDTH = 1280;

    public static void main(String[] args) {
        //this method must create the controller and the view
        System.out.println("fuck her right in the pussy!");
        
        javafx.application.Application.launch();
    }

    @Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("ATLAS");
		primaryStage.setX(0);
		primaryStage.setY(0);
		primaryStage.setWidth(WIDTH);
		primaryStage.setHeight(HEIGHT);

		Controller c = ControllerImpl.getIstanceOf();
        
        View v = new ViewNewImpl(c, primaryStage);
        System.out.println("view = " + v);
        c.setView(v);
        c.startSim();
		this.setOnClose(primaryStage);
		primaryStage.show();
	}
    
    private void setOnClose(Stage primaryStage) {
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
	}


}
