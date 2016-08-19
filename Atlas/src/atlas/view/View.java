package atlas.view;

import java.util.List;
import java.util.Optional;
import javafx.scene.input.MouseEvent;
import atlas.controller.Controller;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;

public interface View {
    
    public void render(List<Body> b, String time,int fps);
    
    public void notifyObservers(SimEvent event);
    
    public Controller getController();
    
    public Optional<Body> getSelectedBody();
    
    public void setNextBodyToAdd(Body body);
    
    public void deleteNextBody();
    
    public Pair<Double, Double> getWindow();
    
    public Optional<MouseEvent> getLastMouseEvent();
    
    public Pair<Integer, Units> getSpeedInfo();
    
    public String getSaveName();

    public void resetViewLayout();
    
    public void updateReferce(Pair<Double, Double> newReference, double newScale);
    
    public Pair<Double, Double> getReference();
    
    public boolean isMainScene();
    
    public void switchToMainScene();
    
    public void switchToLoadingScene();
    
    public List<Body> getBodies();
}