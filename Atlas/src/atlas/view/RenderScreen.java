package atlas.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import atlas.model.Body;
import atlas.utils.Pair;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * This pane serves as the render screen, which displays all the bodies for each
 * frame. It consists of multiple layers (from lower to higher): - background
 * image - effects, such as trails - bodies images - bodies labels
 * 
 * @author Cap
 *
 */
public class RenderScreen extends StackPane {

	private final static String DEFAULT_BACKGROUND = "/images/" + "star.png";
	private final static int LABEL_OFFSET = 10;
	private final static int CANVAS_BORDER = 0;

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
	public RenderScreen(final Pane root) {
		this.setBackgroundImage(DEFAULT_BACKGROUND);
		
		DoubleBinding y = this.heightProperty().subtract(CANVAS_BORDER);
		this.lBottom.widthProperty().bind(this.widthProperty());
		this.lBottom.heightProperty().bind(y);
		this.lMid1.widthProperty().bind(this.widthProperty());
		this.lMid1.heightProperty().bind(y);
		this.lMid2.prefWidthProperty().bind(this.widthProperty());
		this.lMid2.prefHeightProperty().bind(this.heightProperty());
		this.lTop.prefWidthProperty().bind(this.widthProperty());
		this.lTop.prefHeightProperty().bind(this.heightProperty());
		
		this.prefHeightProperty().bind(root.heightProperty());
		this.prefWidthProperty().bind(root.widthProperty());
		this.getChildren().addAll(this.lBottom, this.lMid1);
		this.getChildren().addAll(this.lMid2, this.lTop);

		Double inTran = new Double(0);
		this.currentTranlate = new Pair<>(inTran, inTran);
	}

	public void setBackgroundImage(String imageUrl) {
		this.setStyle("-fx-background-image: url('" + imageUrl + "'); " + "-fx-background-position: center center; "
				+ "-fx-background-repeat: stretch;");
	}

	public void render(List<Body> bodies, double scale, Pair<Double, Double> translate) {
		/* Preliminary actions */
		this.adjustScreen(scale, translate);
		this.clearScreen();
		/* Drawing the new frame */

		bodies.forEach(b -> {
			final ImageView img = this.getOrCreatePlanetImage(b, scale);

			// If not present, create new entry
			if (!this.bMap.keySet().contains(b.getName())) {
				Label lab = new Label(b.getName());
				lab.setTextFill(Color.WHITESMOKE);
				bMap.put(b.getName(), new Pair<>(img, lab));

				lMid2.getChildren().add(img);
				lTop.getChildren().add(lab);
			}

			this.drawTrail(b, scale);

			// System.out.println(b.getName() + " X = " + b.getPosX() + " Y = "
			// + b.getPosY());
			bMap.get(b.getName()).getX().relocate(this.calcPosX(b.getPosX()), this.calcPosY(b.getPosY()));
			bMap.get(b.getName()).getY().relocate(this.calcPosX(b.getPosX()) + LABEL_OFFSET,
					this.calcPosY(b.getPosY()));
		});
	}

	private double calcPosX(double realX) {
		return this.getWidth()/2 + this.currentTranlate.getX() + realX * this.currentScale;
	}

	private double calcPosY(double realY) {
		return this.getHeight()/2 + this.currentTranlate.getY() - realY * this.currentScale;
	}

	private void drawTrail(Body b, double scale) {
		int minPointsTodraw = 2;
		if (b.getTrail().size() < minPointsTodraw) {
			return;
		}
		int arraySize = b.getTrail().size();
		double[] pointsX = new double[arraySize];
		double[] pointsY = new double[arraySize];
		Iterator<Pair<Double, Double>> it = b.getTrail().iterator();
		int x = 0;
		int y = 0;
		while (it.hasNext() && x < arraySize) {
			Pair<Double, Double> p = it.next();
			pointsX[x++] = this.calcPosX(p.getX());
			pointsY[y++] = this.calcPosY(p.getY());
		}
		GraphicsContext gc = this.lMid1.getGraphicsContext2D();
		gc.setStroke(Color.AQUA);
		gc.setLineWidth(5);
		gc.strokePolyline(pointsX, pointsY, arraySize);
	}

	private ImageView getOrCreatePlanetImage(Body b, double scale) {
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
		radiusScaled *= 2;
		// System.out.println("radius" + radiusScaled);
		img.setFitHeight(10);
		img.setFitWidth(10);

		return img;
	}

	private void adjustScreen(double scale, Pair<Double, Double> translate) {
		if (this.currentScale != scale || !translate.equals(currentTranlate)) {
			// this.setScaleX(scale);
			// this.setScaleY(scale);
			// this.setTranslateX(translate.getX());
			// this.setTranslateY(translate.getY());
			this.currentScale = scale;
			this.currentTranlate = translate;
		}
	}

	private void clearScreen() {
		this.lBottom.getGraphicsContext2D().clearRect(0, 0, lBottom.getWidth(), lBottom.getHeight());
		this.lMid1.getGraphicsContext2D().clearRect(0, 0, lMid1.getWidth(), lMid1.getHeight());
	}
}
