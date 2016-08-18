package atlas.view;

import java.util.List;

import atlas.model.Body;
import atlas.utils.Pair;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SceneMain extends Scene {
	
	protected BorderPane root = new BorderPane();
	protected CruiseControl cruise;
	protected RenderScreen renderPanel;
	protected Button left,right;
	protected OptionMenu optionMenu;
	protected Image background;
	
	private View view;
	
	public SceneMain(View v) {
		super(new Pane());
		this.view = v;
		this.renderPanel = new RenderScreen();
		this.cruise = new CruiseControl(v);
		this.left = new Button("|||");
		this.right = new Button("|||");
		this.optionMenu = new OptionMenu();
		this.optionMenu.setRight(left);
		
		this.root.setMinSize(0, 0);
		this.root.setCenter(renderPanel);
		this.root.setBottom(cruise);
		this.root.setLeft(optionMenu);
		this.root.setStyle("-fx-border-color: aqua;");
		
		left.setMaxHeight(Double.MAX_VALUE);
		right.setMaxHeight(Double.MAX_VALUE);
		
		this.setRoot(root);
		
		this.setCommands();
	}
	
	private void setCommands() {
		this.setKeyboardCommands();
		this.setScrollCommands();
		this.showLateral();
	}
	
	private void showLateral() {
		this.left.setOnAction(e -> {
			if(this.optionMenu.isShown()) {
				this.optionMenu.hideContent();
			} else {
				this.optionMenu.showContent();
			}
		});
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
		this.cruise.labelTime.setText(time);
		this.renderPanel.render(bodies, scale, translate, fps);
	}
}
