package atlas.application;

import atlas.controller.ControllerImpl;
import atlas.controller.Controller;
import atlas.view.View;
import atlas.view.ViewImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * 
 * This class contains the main method that is used to launch the application.
 *
 */
public class ApplicationLauncher extends Application{
	
	private static int HEIGHT = 700;
	private static int WIDTH = 1200;

    public static void main(String[] args) {
        //this method must create the controller and the view
        System.out.println("fhritp!");
        
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
        
        View v = new ViewImpl(c, primaryStage);
        System.out.println("view = " + v);
        c.setView(v);
        c.startSim();
	}
}
