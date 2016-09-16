package atlas.view;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuOption extends MenuHidable {

	private static final int BORDER = 10;
	private static final int VGAP = 15;
	private static final int HGAP = 15;
	private static final double BUTTON_HEIGHT = 100;
	private static final double BUTTON_WIDTH = 100;

	private ImageView logo = SceneLoading.LOGO;
	private ScrollPane container = new ScrollPane();
	private VBox root = new VBox();
	private GridPane grid = new GridPane();
	private Button newSim = new Button("New");
	private Button save = new Button("Save");
	private Button load = new Button("Load");
	private Button credits = new Button("Credits");
	private Button exit = new Button("Exit");

	private CheckBox collisionOne = new CheckBox("Fragments");
	private CheckBox collisionTwo = new CheckBox("Absorb");

	private CheckBox nBodyOne = new CheckBox("Brute force - n^2");
	private CheckBox nBodyTwo = new CheckBox("Bernes Hut - nlogn");
	
	private CheckBox fullScreen = new CheckBox("Full screen mode");

	// private Alert creditsDialog = new Alert(AlertType.INFORMATION);

	public MenuOption() { // try tilepane
		super();
		super.setRight(super.btn);
		this.container.setContent(root);
		root.getChildren().add(grid);
		Insets in = new Insets(BORDER, BORDER, BORDER, BORDER);
		root.setPadding(in);
		root.setSpacing(VGAP);

		root.getChildren().addAll(new Text("ADDITIONAL SETTINGS"), fullScreen);	
		root.getChildren().addAll(new Text("Collision system: "), collisionOne,
				collisionTwo);
		root.getChildren().addAll(new Text("N-Body algorithm: "), nBodyOne, nBodyTwo);

		// this.setStyle("-fx-background-color: black;");
		// root.setGridLinesVisible(true);

		this.logo = new ImageView(SceneLoading.LOGO.getImage());
		logo.setPreserveRatio(true);
		logo.setFitHeight(TOP_HEIGHT);
		logo.setFitWidth(TOP_WIDTH);

		this.setupGrid();

		// actions
		View view = ViewImpl.getView();
		this.newSim.setOnAction(e -> view.notifyObservers(SimEvent.NEW_SIM));
		this.save.setOnAction(e -> view.notifyObservers(SimEvent.SAVE_SIM));
		this.load.setOnAction(e -> view.notifyObservers(SimEvent.LOAD));
		this.exit.setOnAction(e -> view.notifyObservers(SimEvent.EXIT));
		
		this.fullScreen.setOnAction(e -> {
			view.setFullScreen(fullScreen.isSelected());
		});

		this.collisionOne.setOnAction(e -> {
			view.notifyObservers(SimEvent.COLLISION_ONE);
			collisionOne.setSelected(true);
			collisionTwo.setSelected(false);
		});
		this.collisionTwo.setOnAction(e -> {
			view.notifyObservers(SimEvent.COLLISION_TWO);
			collisionOne.setSelected(false);
			collisionTwo.setSelected(true);
		});
		this.nBodyOne.setOnAction(e -> {
			view.notifyObservers(SimEvent.NBODY_ONE);
			nBodyOne.setSelected(true);
			nBodyTwo.setSelected(false);
		});
		this.nBodyTwo.setOnAction(e -> {
			view.notifyObservers(SimEvent.NBODY_TWO);
			nBodyOne.setSelected(false);
			nBodyTwo.setSelected(true);
		});

		collisionOne.setSelected(true);
		nBodyOne.setSelected(true);
	}

	public void showContent() {
		this.setCenter(container);
	}

	private void setupGrid() {
		grid.setVgap(VGAP);
		grid.setHgap(HGAP);

		/* Setting up each node */
		List<Node> nodes = Arrays.asList(this.logo, this.newSim, this.save, this.load, this.credits, this.exit);
		Arrays.asList(this.newSim, this.save, this.load, this.credits, this.exit).forEach(i -> {
			i.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			GridPane.setHalignment(i, HPos.CENTER);
		});
		for (int r = 0, i = 0; i < nodes.size(); r++, i++) {
			GridPane.setConstraints(nodes.get(i), 0, r);
			if (r != 0 && ++i < nodes.size()) {
				GridPane.setConstraints(nodes.get(i), 1, r);
			}
		}
		GridPane.setColumnSpan(this.logo, 2);

		// Creates 2 columns with equal width
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(50);
		grid.getColumnConstraints().addAll(col);

		grid.getChildren().addAll(this.logo, this.newSim, this.save, this.load, this.credits, this.exit);
	}

}