package atlas.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import atlas.model.Body;
import atlas.model.BodyImpl;
import atlas.model.BodyType;
import atlas.utils.Pair;
import atlas.utils.Units;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MenuInfo extends MenuHidable {

	private static final ImageView IMAGE_404 = new ImageView(new Image("images/image404.png"));
	private static final String DEFAULT_INFO = "Unknown";
	private static final String NON_EDITABLE_INFO = "[non-editable]";
	private static final int GAP = 10;
	private static final double BUTTON_HEIGHT = 50;
	private static final double BUTTON_WIDTH = 100;

	private ScrollPane center;
	private GridPane contentPane;

	private Optional<Long> bodyId = Optional.empty();
	private ImageView bodyImage = IMAGE_404;
	private Button updateBtn = new Button("Update");
	private Button saveBtn = new Button("Save body");

	private Pair<Label, TextField> name;
	private Pair<Label, ChoiceBox<BodyType>> type;
	private Pair<Label, TextField> mass;
	private Pair<Label, TextField> radius;
	private Pair<Label, TextField> temperature;
	private Pair<Label, TextField> totVelocity;
	private Pair<Label, TextField> rotPeriod;
	private Pair<Label, TextField> rotAng;
	private Pair<Label, TextField> orbPeriod;
	private Pair<Label, TextField> orbParent;

	Double masss;
	private Body currentBody;

	public MenuInfo() {
		super();
		super.setLeft(super.btn);

		this.center = new ScrollPane();
		this.contentPane = new GridPane();

		// contentPane.setGridLinesVisible(true);
		Insets in = new Insets(GAP, GAP, GAP, GAP);
		contentPane.setPadding(in);
		contentPane.setVgap(GAP);
		contentPane.setHgap(GAP);

		this.setStyle("-fx-background-color: black;");
		this.center.setContent(contentPane);

		this.setupGrid();
		this.update(Optional.empty());
		this.setButtonAction();
	}

	private void setupGrid() {
		/* Initialing */
		this.name = new Pair<>(new Label("Name"), new TextField());
		this.type = new Pair<>(new Label("Type"), new ChoiceBox<>());
		this.mass = new Pair<>(new Label("Mass"), new TextField());
		this.radius = new Pair<>(new Label("Radius"), new TextField());
		this.temperature = new Pair<>(new Label("Temperature"), new TextField());
		this.totVelocity = new Pair<>(new Label("Total velocity"), new TextField());
		this.rotPeriod = new Pair<>(new Label("Rotation perdiod"), new TextField());
		this.rotAng = new Pair<>(new Label("Rotation angle"), new TextField());
		this.orbPeriod = new Pair<>(new Label("Orbital perdiod"), new TextField());
		this.orbParent = new Pair<>(new Label("Orbital parent"), new TextField());

		this.type.getY().getItems().addAll(BodyType.values());
		/* Adding node into the gridpane */
		List<Pair<Label, ? extends Control>> nodes = Arrays.asList(name, type, mass, radius, temperature, totVelocity,
				rotPeriod, rotAng, orbPeriod, orbParent);

		int labelColumn = 0;
		int textColumn = 1;
		int offset = 1; // image on the 1st row
		int maxSize = nodes.size();
		for (int i = 0; i < maxSize; i++) {
			contentPane.add(nodes.get(i).getX(), labelColumn, i + offset);
			contentPane.add(nodes.get(i).getY(), textColumn, i + offset);
		}

		/* Adding buttons */
		int lastRow = maxSize + offset;
		GridPane.setConstraints(this.saveBtn, labelColumn, lastRow);
		GridPane.setConstraints(this.updateBtn, textColumn, lastRow++);
		this.saveBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		this.updateBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

		/* Adding image */
		this.swapImage(IMAGE_404);

		contentPane.getChildren().addAll(this.updateBtn, this.saveBtn);
	}

	/**
	 * 
	 * @param b
	 * @param img
	 */
	public void update(Optional<Body> b) {

		if (!b.isPresent()) {
			this.bodyId = Optional.empty();
			this.swapImage(IMAGE_404);
			this.setDisableAll(true);
		} else {
			/* Setting the image */
			// if new body, I need to reload the image
			if (!this.bodyId.isPresent()
					|| (this.bodyId.isPresent() && this.bodyId.get().longValue() != b.get().getId())) {
				this.currentBody = new BodyImpl(b.get());
				this.bodyId = Optional.ofNullable(b.get().getId());
				this.swapImage(new ImageView(new Image(b.get().getImagePath())));
				this.setDisableAll(false);

				this.type.getY().getSelectionModel().select(b.get().getType());
			}
			this.bodyImage.setRotate(b.get().getProperties().getRotationAngle());
			/* updating info */
			this.insertInfo(b.get());
		}
	}

	private List<TextField> getAllTextField() {
		return Arrays.asList(name.getY(), mass.getY(), radius.getY(), temperature.getY(), totVelocity.getY(),
				rotPeriod.getY(), rotAng.getY(), orbPeriod.getY(), orbParent.getY());
	}

	private void setDisableAll(boolean b) {
		List<Button> btn = Arrays.asList(this.saveBtn, this.updateBtn);
		this.getAllTextField().forEach(i -> {
			if (b) {
				i.setPromptText(DEFAULT_INFO);
			}
			if(i == orbParent.getY() || i == orbPeriod.getY()) {
				i.setDisable(true);
			} else {
				i.setDisable(b);
			}
		});
		this.type.getY().setDisable(b);

		btn.forEach(i -> i.setDisable(b));
	}

	private void resetAllTextField() {
		this.getAllTextField().forEach(i -> {
			i.setText("");
		});
	}

	private void swapImage(ImageView img) {
		if (!contentPane.getChildren().contains(img)) {
			contentPane.getChildren().remove(this.bodyImage);
			img.setPreserveRatio(true);
			GridPane.setConstraints(img, 0, 0);
			GridPane.setColumnSpan(img, 2);
			GridPane.setHalignment(img, HPos.CENTER);
			this.bodyImage = img;
			this.bodyImage.setFitHeight(TOP_HEIGHT);
			this.bodyImage.setFitWidth(TOP_WIDTH);
			contentPane.getChildren().add(this.bodyImage);
		}
	}

	/* Updates the panel info of the selected body */
	private void insertInfo(Body b) {
		this.currentBody.updateInfo(b);
		this.name.getY().setPromptText(currentBody.getName());
		this.mass.getY().setPromptText(currentBody.getMass() + " kg");
		this.radius.getY().setPromptText(currentBody.getProperties().getRadius() / 1000 + " km");
		this.temperature.getY().setPromptText(currentBody.getProperties().getTemperature().isPresent()
				? currentBody.getProperties().getTemperature().get().toString().concat(" K") : DEFAULT_INFO);
		this.totVelocity.getY().setPromptText(currentBody.getTotalVelocity() + " m/2");
		this.rotPeriod.getY()
				.setPromptText(currentBody.getProperties().getRotationPeriod() / Units.HOUR_SEC.getValue() + " hours");
		this.rotAng.getY().setPromptText(currentBody.getProperties().getRotationAngle() + " /360 deg");
		this.orbParent.getY().setText(currentBody.getProperties().getParent().isPresent()
				? currentBody.getProperties().getParent().get().getName() + " ".concat(NON_EDITABLE_INFO): DEFAULT_INFO);
		this.orbPeriod.getY()
				.setText(currentBody.getProperties().getOrbitalPeriod().isPresent()
						? currentBody.getProperties().getOrbitalPeriod().get() / Units.DAY_SEC.getValue() + " days " + NON_EDITABLE_INFO
						: DEFAULT_INFO);
	}

	private Optional<Double> getNumber(String text) {
		if (text == null || text.length() < 1) {
			return Optional.empty();
		} else {
			double number;
			try {
				number = Double.parseDouble(text);
			} catch (NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR,
						"Format invalid!\nOnly numeric and exponetial formats are accepted.", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return Optional.empty();
			}
			return Optional.ofNullable(number);
		}
	}

	public Optional<Body> extractInfo() {
		if (ViewImpl.getView().getSelectedBody().isPresent()) {
			if (name.getY().getText().length() >= 1) {
				this.currentBody.setName(name.getY().getText());
			}
			this.currentBody.setType(type.getY().getValue());

			this.getNumber(mass.getY().getText()).ifPresent(i -> this.currentBody.setMass(i));

			this.getNumber(totVelocity.getY().getText()).ifPresent(i -> this.currentBody.setTotalVelocity(i));

			this.getNumber(radius.getY().getText()).ifPresent(i -> this.currentBody.getProperties().setRadius(i));

			this.getNumber(temperature.getY().getText())
					.ifPresent(i -> this.currentBody.getProperties().setTemperature(i));

			this.getNumber(rotPeriod.getY().getText()).ifPresent(
					i -> this.currentBody.getProperties().setRotationPeriod(i.longValue() * Units.HOUR_SEC.getValue()));

			this.getNumber(rotAng.getY().getText())
					.ifPresent(i -> this.currentBody.getProperties().setRotationAngle(i));

			this.getNumber(orbPeriod.getY().getText()).ifPresent(
					i -> this.currentBody.getProperties().setOrbitalPeriod(i.longValue() * Units.DAY_SEC.getValue()));

			return Optional.ofNullable(this.currentBody);
		} else {
			return Optional.empty();
		}
	}

	private void setButtonAction() {
		this.saveBtn.setOnAction(e -> {
			ViewImpl.getView().notifyObservers(SimEvent.SAVE_BODY);
		});
		;

		this.updateBtn.setOnAction(e -> {
			ViewImpl.getView().notifyObservers(SimEvent.UPDATE_BODY);
			this.resetAllTextField();
		});
	}

	@Override
	public void showContent() {
		this.setCenter(center);
	}
}