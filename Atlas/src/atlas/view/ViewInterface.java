package atlas.view;

import java.util.List;

import javafx.scene.control.Button;
import atlas.model.Body;

public interface ViewInterface {
    
    public void render(List<Body> b);
    
    public Button getPlayButton();
    
    public Button getPauseButton();

}
