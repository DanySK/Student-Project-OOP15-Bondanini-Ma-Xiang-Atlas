package atlas.view;

import java.util.List;

import javafx.scene.control.Button;
import atlas.model.Body;

public interface View {
    
    public void render(List<Body> b);
    
    public Button getPlayButton();
    
    public Button getPauseButton();
    
//    public void notifyObservers();

}
