package atlas.view;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import atlas.model.Body;
import atlas.utils.Pair;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This pane serves as the render screen, which displays all the bodies for each
 * frame. It consists of multiple layers (from lower to higher): - background
 * image - effects, such as trails - bodies images - bodies labels
 * 
 * @author Cap
 *
 */
public class RenderScreen extends StackPane {

	private static String DEFAULT_BACKGROUND = "/images/" + "star.png";
	private static int LABEL_OFFSETX = 10;

	private Canvas lBottom = new Canvas(); // the bottom layer
	private Canvas lMid1 = new Canvas(); // first intermediate layer
	private Pane lMid2 = new Pane();
	private Pane lTop = new Pane(); // the top layer -> labels

	private Map<String, Pair<ImageView, Label>> bMap = new HashMap<>();

	private double currentScale;
	private Pair<Double, Double> currentTranlate;

	/**
	 * Constructor
	 */
	public RenderScreen(double width, double height) {
		this.setBackgroundImage(DEFAULT_BACKGROUND);

		this.lBottom.setWidth(width);
		this.lBottom.setHeight(height);
		this.lMid1.setWidth(width);
		this.lMid1.setHeight(height);

		// this.getChildren().addAll(this.lBottom, this.lMid1);
		this.getChildren().addAll(this.lMid2, this.lTop);
		// Hopefully sets the position to center
		// this.setTranslateX(this.getWidth() / 2);
		// this.setTranslateY(this.getHeight() / 2);

		Double inTran = new Double(0);
		this.currentTranlate = new Pair<>(inTran, inTran);
	}

	public void setBackgroundImage(String imageUrl) {
		this.setStyle("-fx-background-image: url('" + imageUrl + "'); " + "-fx-background-position: center center; "
				+ "-fx-background-repeat: stretch;");
	}

	public void render(List<Body> bodies, double scale, Pair<Double, Double> translate) {
		/* Preliminary actions */
		// this.adjustScreen(scale, translate);
		// this.clearScreen();
		/* Drawing the new frame */

		bodies.forEach(b -> {
			String path = "/planet_images/";
			ImageView img = null;
			try {
				if (bMap.containsKey(b.getName())) {
					img = bMap.get(b.getName()).getX();
				} else {
					img = new ImageView(new Image(path.concat(b.getName().toLowerCase() + ".png")));
				}
			} catch (Exception e) {
				img = new ImageView(new Image(path.concat("mars.png")));
			}
			double radiusScaled = b.getProperties().getRadius() * scale;
			radiusScaled *= 20;
			System.out.println("radius" + radiusScaled);
			img.setFitHeight(50);
			img.setFitWidth(50);
			// if (!this.lMid2.getChildren().contains(img)) {
			// lMid2.getChildren().add(img);
			// }

			/* Labels */// might work mate
			ImageView m = img;
			// String key = this.bMap.keySet().stream().filter(p ->
			// p.equals(b.getName())).findFirst().orElseGet(() -> {
			// // If not present, create new label
			// Label lab = new Label(b.getName());
			// lab.setTextFill(Color.WHITESMOKE);
			// bMap.put(b.getName(), new Pair<>(m, lab));
			// lMid2.getChildren().add(m);
			// lTop.getChildren().add(lab);
			// // assign action to the label
			// // assign
			// return b.getName();
			// });
			if (!this.bMap.keySet().contains(b.getName())) {
				Label lab = new Label(b.getName());
				lab.setTextFill(Color.WHITESMOKE);
				bMap.put(b.getName(), new Pair<>(m, lab));
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						lMid2.getChildren().add(m);
						lTop.getChildren().add(lab);
					}
				});
			}
			Label l = this.bMap.get(b.getName()).getY();
			bMap.get(b.getName()).getX().relocate(translate.getX() + b.getPosX() * scale,
					translate.getY() + b.getPosY() * scale);
			bMap.get(b.getName()).getY().relocate(translate.getX() + b.getPosX() * scale,
					translate.getY() + b.getPosY() * scale + b.getProperties().getRadius());

			System.out.println("trans  = " + translate + " posx = " + (translate.getX() + b.getPosX() * scale)
					+ " posy = " + (translate.getY() + b.getPosY() * scale) + "  label " + b.getName() + " x = "
					+ l.getLayoutX() + " y = " + l.getLayoutY());
		});
	}

	private void adjustScreen(double scale, Pair<Double, Double> translate) {
		if (this.currentScale != scale || !this.currentTranlate.equals(translate)) {
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
	}
}
