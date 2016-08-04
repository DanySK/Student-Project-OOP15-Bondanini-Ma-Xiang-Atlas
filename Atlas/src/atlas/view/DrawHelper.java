package atlas.view;

import java.util.List;

import atlas.model.Body;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class DrawHelper {
	
	private static Image DEFAULT_BACKGROUND = new Image("/images/" + "star.png");
	private Image background;
	
	public DrawHelper() {
	}
	
	public Image getBackgroundImage() {
		return background != null ? background : DEFAULT_BACKGROUND;
	}
	
	public void setBackgroundImage(Image background) {
		this.background = background;
	}
	
	public void draw(Canvas canvas, List<Body> bodies, double scale) {
		
	}
}
