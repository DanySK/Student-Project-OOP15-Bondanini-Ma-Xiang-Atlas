package atlas.view;

import java.util.List;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import atlas.controller.Controller;
import atlas.controller.ControllerImpl;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;

public interface View {
    
    public void render(List<Body> b, int fps);
    
    public void notifyObservers(SimEvent event);
    
    public Controller getController();
    
    public Optional<Body> getSelectedBody();
    
    public Optional<MouseEvent> getLastMouseEvent();
    
    public Pair<Integer, Units> getSpeedInfo();
    
    public String getSaveName();

    public void resetViewLayout();
    
    public void updateReferce(Pair<Double, Double> newReference, double newScale);
    
    public Pair<Double, Double> getReference();
    
    public Scene getCurrentScene();
}