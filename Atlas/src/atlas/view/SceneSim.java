package atlas.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class SceneSim extends Scene {

	protected BorderPane root = new BorderPane();
	protected CruiseControl cruise;
	protected Canvas renderPanel;
	protected GraphicsContext graphics;
	protected Image background;
	
	public SceneSim(double width, double height) {
		super(new Pane());
		this.renderPanel = new Canvas(width, height-100);
		this.graphics = renderPanel.getGraphicsContext2D();
		this.cruise = new CruiseControl();
		
		this.root.setCenter(renderPanel);
		this.root.setBottom(cruise);
		
		this.setRoot(root);
		
	}
	
	public Image getBackgroundImage() {
		return background != null ? background : new Image("/images/" + "star.png");
	}
	
	public void setBackgroundImage(Image background) {
		this.background = background;
	}
	
	protected void draw() {
		
	}
}
