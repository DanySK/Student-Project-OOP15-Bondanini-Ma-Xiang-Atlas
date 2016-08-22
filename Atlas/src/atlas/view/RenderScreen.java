package atlas.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
	private final static float TRAIL_OPACITY = 0.4f;
	private final static int TRAIL_WIDTH = 5;
	private final static int MIN_IMAGE_SIZE = 1;

	private Canvas lBottom = new Canvas(); // the bottom layer
	private Canvas lMid1 = new Canvas(); // first intermediate layer
	private Pane lMid3 = new Pane();// for now
	private Pane lMid2 = new Pane();
	private Pane lTop = new Pane(); // the top layer -> labels
	private Label fpsCounter = new Label();

	private Map<Long, Pair<Pair<ImageView, Label>, Color>> bMap = new HashMap<>();
	private Map<Long, Boolean> secondChanceMap = new HashMap<>();//second chance
	
//	private Optional<Long> lock = Optional.empty();

	private double currentScale;
	private Pair<Double, Double> currentTranlate;

	/**
	 * Constructor
	 */
	public RenderScreen() {
		this.setBackgroundImage(DEFAULT_BACKGROUND);

		/* Resizable canvas */
		DoubleBinding x = this.widthProperty().subtract(0);
		DoubleBinding y = this.heightProperty().subtract(0);
		this.lBottom.widthProperty().bind(x);
		this.lBottom.heightProperty().bind(y);
		this.lMid1.widthProperty().bind(x);
		this.lMid1.heightProperty().bind(y);

		/* FPS counter */
		this.lTop.getChildren().add(fpsCounter);
		fpsCounter.setTextFill(Color.MAGENTA);
		fpsCounter.setFont(Font.font("Roboto Thin", FontWeight.BOLD, 20));

		/* Resizable pane */
		this.maxHeight(Double.MAX_VALUE);
		this.maxWidth(Double.MAX_VALUE);
		this.setMinSize(0, 0);

		/* Adding children/layers */
		this.getChildren().addAll(this.lBottom, this.lMid1);
		this.getChildren().addAll(this.lMid2, this.lMid3, this.lTop);

		/* Default translate */
		Double defTran = new Double(0);
		this.currentTranlate = new Pair<>(defTran, defTran);
	}

	public void setBackgroundImage(String imageUrl) {
		this.setStyle("-fx-background-image: url('" + imageUrl + "'); " + "-fx-background-position: center center; "
				+ "-fx-background-repeat: stretch;");
	}

	public void render(List<Body> bodies, double scale, Pair<Double, Double> translate, int fps) {
		/* Preliminary actions */
		this.adjustScreen(scale, translate);
		this.clearScreen();
		this.fpsCounter.setText("FPS: " + fps);

		this.secondChanceMap.replaceAll((k, v) -> Boolean.FALSE);
		/* Drawing the new frame */
		bodies.forEach(b -> {
			final ImageView img = this.getBodyImage(b, scale);

			// If not present, create new entry
			if (!this.bMap.keySet().contains(b.getId())) {
				Label lab = new Label(b.getName());
				this.setLableOnMultiClick(lab, b);
				lab.setTextFill(Color.WHITESMOKE);
				bMap.put(b.getId(), new Pair<>(new Pair<>(img, lab), this.pickColor()));
				secondChanceMap.put(b.getId(), true);

				lMid2.getChildren().add(img);
				lTop.getChildren().add(lab);
			}

			Pair<Pair<ImageView, Label>, Color> entry = bMap.get(b.getId());
			this.secondChanceMap.put(b.getId(), true);

			this.drawTrail(b, scale, entry.getY());

			/* updates the label name if it has been changed */
			entry.getX().getY().setText(b.getName());

			/*
			 * Place the image centered to the body point. Labels are placed
			 * next to the image
			 */
			entry.getX().getX().relocate(this.calcPosX(b.getPosX()) - entry.getX().getX().getFitWidth() / 2,
					this.calcPosY(b.getPosY()) - entry.getX().getX().getFitHeight() / 2);
			entry.getX().getY().relocate(this.calcPosX(b.getPosX() + b.getProperties().getRadius()),
					this.calcPosY(b.getPosY()));
		});
		//remove all non used bodies
		this.secondChanceMap.entrySet().stream().filter(i -> !i.getValue()).forEach(i -> {
			System.out.println("Removing " + i.getKey());
			//remove image
			this.lMid2.getChildren().remove(bMap.get(i.getKey()).getX().getX());
			//remove label
			this.lTop.getChildren().remove(bMap.get(i.getKey()).getX().getY());
			bMap.remove(i.getKey());
		});
		this.secondChanceMap = this.secondChanceMap.entrySet().stream()
															.filter(i -> i.getValue())
															.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	private double calcPosX(double realX) {
		return this.getWidth() / 2 + this.currentTranlate.getX() + realX * this.currentScale;
	}

	private double calcPosY(double realY) {
		return this.getHeight() / 2 + this.currentTranlate.getY() - realY * this.currentScale;
	}

	private Color pickColor() {
		Random r = new Random();
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), TRAIL_OPACITY);
	}

	private void drawTrail(Body b, double scale, Color color) {
		int minPointsTodraw = 2;
		if (b.getTrail().size() < minPointsTodraw) {
			return;
		}
		int arraySize = b.getTrail().size();
		double[] pointsX = new double[arraySize];
		double[] pointsY = new double[arraySize];
		double[] points = new double[arraySize * 2];
		Iterator<Pair<Double, Double>> it = b.getTrail().iterator();
		int x = 0;
		int y = 0;
		int i = 0;
		while (it.hasNext() && x < arraySize) {
			Pair<Double, Double> p = it.next();
			pointsX[x++] = this.calcPosX(p.getX());
			pointsY[y++] = this.calcPosY(p.getY());
			points[i++] = this.calcPosX(p.getX());
			points[i++] = this.calcPosY(p.getY());
		}
		/* POLY MODE */
		// Polyline pl = new Polyline(points);
		// pl.setStroke(color);
		// pl.setStrokeWidth(TSystem.out.println("Suca");RAIL_WIDTH);
		// this.lMid3.getChildren().add(pl);
		/* CANVAS MODE */
		GraphicsContext gc = this.lMid1.getGraphicsContext2D();
		gc.setStroke(color);
		gc.setLineWidth(TRAIL_WIDTH);
		gc.strokePolyline(pointsX, pointsY, arraySize);
	}

	private ImageView getBodyImage(Body b, double scale) {
		ImageView img = null;
		try {
			if (bMap.containsKey(b.getId())) {
				img = bMap.get(b.getId()).getX().getX();
			} else {
				img = new ImageView(new Image(b.getImagePath()));
			}
		} catch (Exception e) {
			throw new IllegalStateException("body image path can't be found : " + b.getImagePath());
		}
		double diamScaled = b.getProperties().getRadius() * 2 * scale;
		img.setPreserveRatio(true);
		img.setFitHeight(diamScaled >= MIN_IMAGE_SIZE ? diamScaled : MIN_IMAGE_SIZE);
		img.setFitWidth(diamScaled >= MIN_IMAGE_SIZE ? diamScaled : MIN_IMAGE_SIZE);
		img.setRotate(b.getProperties().getRotationAngle());

		return img;
	}

	private void adjustScreen(double scale, Pair<Double, Double> translate) {
		if (this.currentScale != scale || !translate.equals(currentTranlate)) {
			this.currentScale = scale;
			this.currentTranlate = translate;
		}
	}

	private void clearScreen() {
		this.lBottom.getGraphicsContext2D().clearRect(0, 0, lBottom.getWidth(), lBottom.getHeight());
		this.lMid1.getGraphicsContext2D().clearRect(0, 0, lMid1.getWidth(), lMid1.getHeight());
		this.lMid3.getChildren().removeAll(this.lMid3.getChildren());
	}
	
	private void setLableOnMultiClick(Label lab, Body body) {
		lab.setOnMouseClicked(e -> {
            ViewImpl.getView().setSelectedBody(body);
            System.out.println("Selected body");
			if (e.getClickCount() > 1) {
			    ViewImpl.getView().setSelectedBody(body);
				ViewImpl.getView().notifyObservers(SimEvent.LOCK);
				System.out.println("Step1");
			}
		});
	}
}
