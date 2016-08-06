package atlas.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import atlas.model.Body;
import atlas.utils.Pair;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * This pane serves as the render screen, which displays all the bodies for each frame.
 * It consists of multiple layers (from lower to higher):
 * - background image
 * - effects, such as trails
 * - bodies images
 * - bodies labels
 * @author Cap
 *
 */
public class RenderScreen extends StackPane {
	
	private static String DEFAULT_BACKGROUND = "/images/" + "star.png";
	private static int LABEL_OFFSETX = 10;
	
	private Canvas lBottom = new Canvas(); // the bottom layer 
	private Canvas lMid1 = new Canvas(); // firt intermediate layer
	private Canvas lMid2 = new Canvas();
	private Pane lTop = new Pane(); // the top layer -> labels
	
	private Map<String, Pair<Image,Label>> labels = new HashMap<>(); 
	
	private double currentScale;
	private Pair<Double, Double> currentTranlate;
	
	/**
	 * Constructor
	 */
	public RenderScreen() {
		this.setBackgroundImage(DEFAULT_BACKGROUND);
		
		this.getChildren().addAll(this.lBottom, this.lMid1, this.lMid2, this.lTop);
		
		//Hopefully sets the position to center
		this.setTranslateX(this.getWidth()/2);
		this.setTranslateY(this.getHeight()/2);
		
		Double inTran = new Double(0);
		this.currentTranlate = new Pair<>(inTran, inTran);
	}
	
	public void setBackgroundImage(String imageUrl) {
		this.setStyle("-fx-background-image: url('" + imageUrl + "'); " +
		           "-fx-background-position: center center; " +
		           "-fx-background-repeat: stretch;");
	}
	
	public void render(List<Body> bodies, double scale, Pair<Double, Double> translate) {
		/*Preliminary actions*/
		System.out.println("Preliminary check1");
		this.adjustScreen(scale, translate);
		this.clearScreen();
		System.out.println("Preliminary check2");
		/*Drawing the new frame*/
		GraphicsContext gcl2 = this.lMid2.getGraphicsContext2D();  
		bodies.forEach(b -> {
			String path = "/planet_images/";
			Image img = this.labels.containsKey(b.getName()) ? this.labels.get(b.getName()).getX() : new Image(path.concat("earth.png"));
			double radiusScaled = b.getProperties().getRadius() * scale;
			gcl2.drawImage(img, b.getPosX(), b.getPosY(), radiusScaled, radiusScaled);
			
			/*Labels*///might work mate
			String key = this.labels.keySet().stream().filter(p -> p.equals(b.getName()))
								.findFirst().orElseGet(() -> {
									//If not  present, create new label
									Label lab = new Label(b.getName());
									this.labels.put(b.getName(), new Pair<>(img, lab));
									this.lTop.getChildren().add(lab);
									//assign action to the label
									//assign
									return b.getName();
								});
			Label l = this.labels.get(key).getY();
			System.out.println("label " + key + " x = " + l.getWidth() + " y = " + l.getHeight());
			this.labels.get(key).getY().relocate(b.getPosX(), b.getPosY()  + LABEL_OFFSETX);
			
			/*Removing unused labels*/
			if(!this.labels.keySet().contains(b.getName())) {
				this.lTop.getChildren().remove(this.labels.get(b.getName()));
				this.labels.remove(b.getName());
			}
		});
//		this.labels.stream().filter(l -> {
//				for(Body b : bodies) {
//					if( !b.getName().equals(l.getText()) ) {
//						return true; 
//					}
//				};
//				return false;
//			}
//		);
	}
	
	private void adjustScreen(double scale, Pair<Double, Double> translate) {
		if(this.currentScale != scale || !this.currentTranlate.equals(translate)) {
			this.setScaleX(scale);
			this.setScaleY(scale);
			this.setTranslateX(translate.getX());
			this.setTranslateY(translate.getY());
			this.currentScale = scale;
			this.currentTranlate = translate;
		}
	}
	
	private void clearScreen() {
		this.lBottom.getGraphicsContext2D().clearRect(0, 0, lBottom.getWidth(), lBottom.getHeight());
		this.lMid1.getGraphicsContext2D().clearRect(0, 0, lMid1.getWidth(), lMid1.getHeight());
		this.lMid2.getGraphicsContext2D().clearRect(0, 0, lMid2.getWidth(), lMid2.getHeight());
	}
}
