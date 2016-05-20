package atlas.application;

import atlas.controller.Controller;
import atlas.controller.ControllerInterface;
import atlas.view.ViewInterface;

/**
 * 
 * This class contains the main method that is used to launch the application.
 *
 */
public class ApplicationLauncher {

    public static void main(String[] args) {
        //this method must create the controller and the view
        System.out.println("fuck her right in the pussy!");
        
        ViewInterface v = new ViewTest();
        ControllerInterface c = Controller.getIstanceOf();
        c.setView(v);
        c.startSim();
    }

}
