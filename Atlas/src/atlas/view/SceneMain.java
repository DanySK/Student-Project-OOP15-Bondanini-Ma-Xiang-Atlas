package atlas.view;

import java.util.List;

import atlas.model.Body;
import atlas.utils.Pair;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SceneMain extends Scene {
	
	protected BorderPane root = new BorderPane();
	protected CruiseControl cruise;
	protected RenderScreen renderPanel;
	protected Image background;
	private Label fpsCounter;
	
//	private Pair<DoubleProperty, DoubleProperty> screenCenter;
	
	private View view;
	
	public SceneMain(View v, double width, double height) {
		super(new Pane());
		this.view = v;
		this.renderPanel = new RenderScreen(root);
		this.cruise = new CruiseControl(v);
		this.fpsCounter = new Label("FPS : ");
		this.fpsCounter.setTextFill(Color.BLACK);
		this.fpsCounter.relocate(5, 5);
		
		this.root.setCenter(renderPanel);
		this.root.setBottom(cruise);
		this.root.setTop(fpsCounter);
		this.root.setStyle("-fx-border-color: aqua;");
		
		this.setRoot(root);
		
		this.setCommands();
	}
	
	private void setCommands() {
		this.setKeyboardCommands();
		this.setScrollCommands();
	}
	
	private void setScrollCommands() {
		this.renderPanel.setOnScroll( e-> {
	    	if (e.getDeltaY() > 0) {
	    		System.out.println("------------------------UP-----------------------------");
	    		this.view.notifyObservers(SimEvent.MOUSE_WHEEL_UP);
	    	} else {
	    		System.out.println("------------------------Down-----------------------------");
	    		this.view.notifyObservers(SimEvent.MOUSE_WHEEL_DOWN);
	    	}
	    });
	}
	
	private void setKeyboardCommands() {
		root.setOnKeyPressed(k -> {
			switch(k.getCode()) {
			case W: 
				view.notifyObservers(SimEvent.W);
				break;
			case A: 
				view.notifyObservers(SimEvent.A);
				break;
			case S: 
				view.notifyObservers(SimEvent.S);
				break;
			case D: 
				view.notifyObservers(SimEvent.D);
				break;
			case SPACE:
				view.notifyObservers(SimEvent.SPACEBAR_PRESSED);
				break;
			default:
				break;
			}
		});
	}
	
	protected void draw(List<Body> bodies, double scale, Pair<Double, Double> translate, String time, int fps) {
		this.fpsCounter.setText("FPS: " + fps);
		this.cruise.labelTime.setText(time);
		this.renderPanel.render(bodies, scale, translate);
	}
}
