package atlas.view;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;

public interface View {
    
    public void render(List<Body> b);
    
    public void notifyObservers(SimEvent event);
    
    public Optional<Body> getSelectedBody();
    
    public Optional<MouseEvent> getLastMouseEvent();
    
    public Pair<Integer, Units> getSpeedInfo();
	
	public void resetViewLayout();

}