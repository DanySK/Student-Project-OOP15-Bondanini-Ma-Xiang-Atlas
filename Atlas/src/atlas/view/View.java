package atlas.view;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import atlas.controller.Controller;
import atlas.model.Body;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

public interface View {
    
    public void render(List<Body> b, String time,int fps);
    
    public void notifyObservers(SimEvent event);
    
    public Controller getController();
    
    public Optional<Body> getSelectedBody();
    
    public void setSelectedBody(Body body); //To changeName
    
    public boolean isCameraLocked();
    
    public void setCameraLocked(boolean b);  
    
    public void deleteNextBody();
    
    public Pair<Double, Double> getWindow();
    
    public Optional<MouseEvent> getLastMouseEvent();
    
    public Optional<Pair<Integer, Units>> getSpeedInfo();
    
    public Optional<Body> getBodyUpdateInfo();
    
    public Optional<String> getSaveName();
    
    public Optional<File> getLoadFile(String title, String action, Map<File, List<File>> files);

    public void resetViewLayout();
    
    public void updateReferce(Pair<Double, Double> newReference, double newScale);
    
    public Pair<Double, Double> getReference();
    
    public boolean isMainScene();
    
    public void switchToMainScene();
    
    public void switchToLoadingScene();
    
    public List<Body> getBodies();
    
    static void onClose() {
		Alert alert = new Alert(Alert.AlertType.WARNING, "Do you really want to exit?", ButtonType.YES,
				ButtonType.NO);
		alert.setTitle("Exit ATLAS");
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			Platform.exit();
			System.exit(0);
		}
	}
}