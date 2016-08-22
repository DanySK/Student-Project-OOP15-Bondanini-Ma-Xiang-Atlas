package atlas.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import atlas.model.Body;
import atlas.model.BodyImpl;
import atlas.model.BodyType;
import atlas.utils.Units;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MenuInfo extends MenuHidable {

	private static final ImageView IMAGE_404 = new ImageView(new Image("images/image404.png"));
	private static final String DEFAULT_INFO = "Unknown";
	private static final int GAP = 10;
	private static final double BUTTON_HEIGHT = 50;
	private static final double BUTTON_WIDTH = 100;

	private ScrollPane center;
	private GridPane contentPane;

	private Optional<Long> bodyId = Optional.empty();
	private ImageView bodyImage = IMAGE_404;
	private Button updateBtn = new Button("Update");
	private Button saveBtn = new Button("Save body");

	private Label lName = new Label("Name");
	private Label lType = new Label("Type");
	private Label lMass = new Label("Mass");
	private Label lRadius = new Label("Radius");
	private Label lTemperature = new Label("Temperature");
	private Label lTotVel = new Label("Total velocity");
	private Label lRotPer = new Label("Rotation period");
	private Label lRotAng = new Label("Rotation angle");
	private Label lOrbParent = new Label("Orbital perent");

	private TextField tName = new TextField();
	private ChoiceBox<BodyType> tType = new ChoiceBox<>();
	private TextField tMass = new TextField();
	private TextField tRadius = new TextField();
	private TextField tTemperature = new TextField();
	private TextField tTotVel = new TextField();
	private TextField tRotPer = new TextField();
	private TextField tRotAng = new TextField();
	private TextField tOrbParent = new TextField();

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
		
		this.tType.getItems().addAll(BodyType.values());
		this.setupGrid();
		this.update(Optional.empty());
		this.setButtonAction();
	}

	private void setupGrid() {
		List<Label> labList = Arrays.asList(this.lName, this.lType, this.lMass, this.lRadius, this.lTemperature, this.lTotVel,
				this.lRotPer, this.lRotAng, this.lOrbParent);

		List<Node> tfList = Arrays.asList(this.tName, this.tType, this.tMass, this.tRadius, this.tTemperature, this.tTotVel,
				this.tRotPer, this.tRotAng, this.tOrbParent);

		int labelColumn = 0;
		int textColumn = 1;
		int offset = 1;
		int maxSize = labList.size() < tfList.size() ? labList.size() : tfList.size();

		for (int i = 0; i < maxSize; i++) {
			GridPane.setConstraints(labList.get(i), labelColumn, i + offset);
			GridPane.setConstraints(tfList.get(i), textColumn, i + offset);
		}
		/* Adding buttons */
		int lastRow = maxSize + offset;
		GridPane.setConstraints(this.saveBtn, labelColumn, lastRow);
		GridPane.setConstraints(this.updateBtn, textColumn, lastRow++);
		this.saveBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		this.updateBtn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);

		/* Adding image */
		this.swapImage(IMAGE_404);

		contentPane.getChildren().addAll(labList);
		contentPane.getChildren().addAll(tfList);
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
				this.bodyId = Optional.ofNullable(b.get().getId());
				this.swapImage(new ImageView(new Image(b.get().getImagePath())));
				this.setDisableAll(false);
			}
			this.bodyImage.setRotate(b.get().getProperties().getRotationAngle());
			/* updating info */
			this.insertInfo(b.get());
		}
	}

	private void setDisableAll(boolean b) {
		List<TextField> tf = Arrays.asList(this.tName, this.tMass, this.tRadius, this.tTemperature, this.tTotVel,
				this.tRotPer, this.tRotAng, this.tOrbParent);
		List<Button> btn = Arrays.asList(this.saveBtn, this.updateBtn);
		tf.forEach(i -> {
			if (b) {
				i.setPromptText(DEFAULT_INFO);
			}
			i.setDisable(b);
		});
		this.tType.setDisable(b);
		
		btn.forEach(i -> i.setDisable(b));
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
	private void insertInfo(Body target) {
		this.tName.setPromptText(target.getName());
//		this.tName.setText(target.getName());
		this.tType.getSelectionModel().select(target.getType());
		this.tMass.setPromptText(target.getMass() + " kg");
		this.tRadius.setPromptText(target.getProperties().getRadius() / 1000 + " km");
		this.tTemperature.setPromptText(target.getProperties().getTemperature().isPresent()
				? target.getProperties().getTemperature().get().toString().concat(" K") : DEFAULT_INFO);
		this.tTotVel.setPromptText((int) target.getTotalVelocity() + " m/2");
		this.tRotPer.setPromptText(target.getProperties().getRotationPeriod() / Units.HOUR_SEC.getValue() + " hours");
		this.tRotAng.setPromptText((int) target.getProperties().getRotationAngle() + " /360 deg");
		this.tOrbParent.setPromptText(target.getProperties().getParent().isPresent()
				? target.getProperties().getParent().get().getName() : DEFAULT_INFO);
	}

	public Optional<Body> extractInfo() {
		if (ViewImpl.getView().getSelectedBody().isPresent()) {
			return Optional.ofNullable(new BodyImpl.Builder().name(this.extractData(tName))
					.mass(Double.parseDouble(this.extractData(tMass)))
					.type(this.tType.getSelectionModel().getSelectedItem())
					.properties(new Body.Properties(Double.parseDouble(this.extractData(tRadius)),
							Long.parseLong(this.extractData(tRotPer)),
							Double.parseDouble(this.extractData(tRotAng)), null, null,
							Double.parseDouble(this.extractData(tTemperature))))
					.build());
		} else {
			return Optional.empty();
		}
	}
	
	private String extractData(TextField tf) {
		String text = tf.getText().length() > 0 ? tf.getText() : tf.getPromptText();
		System.out.println("text = " + tf.getText());
		System.out.println("promt text = " + tf.getPromptText());
		if (tf == tMass) {
			System.out.println(Double.parseDouble(tMass.getPromptText().split(" ")[0] + 1E23));
		}
		return text.split(" ")[0];
	}
	
	private void setButtonAction() {
		this.saveBtn.setOnAction( e -> {
			ViewImpl.getView().notifyObservers(SimEvent.SAVE_BODY);
		});;
		
		this.updateBtn.setOnAction( e -> {
			ViewImpl.getView().notifyObservers(SimEvent.UPDATE_BODY);
			this.setDisableAll(true);
		});
	}

	@Override
	public void showContent() {
		this.setCenter(center);
	}
}