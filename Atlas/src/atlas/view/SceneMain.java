package atlas.view;

import java.util.List;

import atlas.controller.ControllerImpl;
import atlas.model.Body;
import atlas.utils.Pair;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SceneMain extends Scene {
	
	private static SceneMain sceneMain;
	
	protected BorderPane root = new BorderPane();
	protected CruiseControl cruise;
	protected RenderScreen renderPanel;
	protected Image background;
	
	public SceneMain(double width, double height) {
		super(new Pane());
		this.renderPanel = new RenderScreen(width, height-70);//width, height-100
		this.cruise = new CruiseControl();
		
		this.root.setCenter(renderPanel);
		this.root.setBottom(cruise);
		
		this.setRoot(root);
		
	}
	
	protected void draw(List<Body> bodies, double scale, Pair<Double, Double> translate) {
		System.out.println("inside draw");
		this.renderPanel.render(bodies, scale, translate);
	}
}
