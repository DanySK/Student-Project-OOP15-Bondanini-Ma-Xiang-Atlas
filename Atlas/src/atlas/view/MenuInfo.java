package atlas.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import atlas.model.Body;
import atlas.model.BodyImpl;
import atlas.utils.Units;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MenuInfo extends MenuHidable {

	private static final ImageView ERROR_IMAGE = new ImageView(new Image("images/image404.png"));
	private static final String DEFAULT_INFO = "Unknown";
	private static final int GAP = 10;
	private ScrollPane center;
	private GridPane contentPane;

	private ImageView bodyImage = ERROR_IMAGE;
	private Button updateBtn = new Button("Update");

	private Label lName = new Label("Name");
	private Label lMass = new Label("Mass");
	private Label lRadius = new Label("Radius");
	private Label lTemperature = new Label("Temperature");
	private Label lTotVel = new Label("Total velocity");
	private Label lRotPer = new Label("Rotation period");
	private Label lRotAng = new Label("Rotation angle");
	private Label lOrbParent = new Label("Orbital perent");

	private TextField tName = new TextField();
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
		this.setStyle("-fx-background-color: black;");

		this.center = new ScrollPane();
		this.contentPane = new GridPane();

		// contentPane.setGridLinesVisible(true);
		Insets in = new Insets(GAP, GAP, GAP, GAP);
		contentPane.setPadding(in);
		contentPane.setVgap(GAP);
		contentPane.setHgap(GAP);

		this.setStyle("-fx-background-color: grey;");
		this.center.setContent(contentPane);
		this.setupGrid();
		this.update(Optional.empty(), Optional.empty());
	}

	private void setupGrid() {
		List<Label> labList = Arrays.asList(this.lName, this.lMass, this.lRadius, this.lTemperature, this.lTotVel,
				this.lRotPer, this.lRotAng, this.lOrbParent);

		List<TextField> tfList = Arrays.asList(this.tName, this.tMass, this.tRadius, this.tTemperature, this.tTotVel,
				this.tRotPer, this.tRotAng, this.tOrbParent);

		int labelColumn = 0;
		int textColumn = 1;
		int offset = 1;
		int maxSize = labList.size() < tfList.size() ? labList.size() : tfList.size();

		for (int i = 0; i < maxSize; i++) {
			GridPane.setConstraints(labList.get(i), labelColumn, i + offset);
			GridPane.setConstraints(tfList.get(i), textColumn, i + offset);
		}
		GridPane.setConstraints(this.updateBtn, textColumn, maxSize + offset);

		GridPane.setConstraints(this.bodyImage, 0, 0);
		GridPane.setColumnSpan(this.bodyImage, textColumn + offset);

		contentPane.getChildren().addAll(labList);
		contentPane.getChildren().addAll(tfList);
		contentPane.getChildren().add(this.updateBtn);
		contentPane.getChildren().add(this.bodyImage);
	}

	public void update(Optional<Body> b, Optional<ImageView> img) {
		this.bodyImage = !img.isPresent() ? ERROR_IMAGE : new ImageView(img.get().getImage());
		this.bodyImage.setFitHeight(TOP_HEIGHT);
		this.bodyImage.setFitWidth(TOP_WIDTH);

		if (!b.isPresent()) {
			List<TextField> tfList = Arrays.asList(this.tName, this.tMass, this.tRadius, this.tTemperature,
					this.tTotVel, this.tRotPer, this.tRotAng, this.tOrbParent);
			tfList.forEach(i -> {
				i.setText(DEFAULT_INFO);
				i.setDisable(true);
			});
			updateBtn.setDisable(true);
		} else {
			this.insertInfo(b.get());
		}
	}
	
	private void insertInfo(Body target) {
		this.tName.setText(target.getName());
		this.tMass.setText("" + target.getMass());
		this.tRadius.setText("" + target.getProperties().getRadius());
		this.tTemperature.setText(target.getProperties().getTemperature().isPresent()
				? target.getProperties().getTemperature().get().toString() : DEFAULT_INFO);
		this.tTotVel.setText("" + target.getTotalVelocity());
		this.tRotPer.setText(target.getProperties().getRotationPeriod() / Units.DAY_SEC.getValue() + " days");
		this.tRotAng.setText(target.getProperties().getRotationAngle() + "°");
		this.tOrbParent.setText(target.getProperties().getParent().isPresent()
				? target.getProperties().getParent().get().getName() : DEFAULT_INFO);	
	}
	
	private Body extractInfo() {
		
		return null;
	}

	@Override
	public void showContent() {
		this.setCenter(center);
	}
}