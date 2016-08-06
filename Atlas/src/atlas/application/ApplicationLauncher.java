package atlas.application;

import atlas.controller.ControllerImpl;
import atlas.controller.Controller;
import atlas.view.TestUI;
import atlas.view.View;
import atlas.view.ViewImpl;

/**
 * 
 * This class contains the main method that is used to launch the application.
 *
 */
public class ApplicationLauncher {

    public static void main(String[] args) {
        //this method must create the controller and the view
        System.out.println("fuck her right in the pussy!");
        
        View v = new TestUI();
        Controller c = ControllerImpl.getIstanceOf();
        c.setView(v);
        c.startSim();
        javafx.application.Application.launch(TestUI.class);
    }

}
