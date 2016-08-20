package atlas.view;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class MenuOption extends MenuHidable {

	private static final int BORDER = 10;
	private static final int VGAP = 15;
	private static final int HGAP = 15;
	private static final double BUTTON_HEIGHT = 100;
	private static final double BUTTON_WIDTH = 100;

	private ImageView logo = SceneLoading.LOGO;
	private GridPane root = new GridPane();
	private Button newSim = new Button("New");
	private Button save = new Button("Save");
	private Button load = new Button("Load");
	private Button credits = new Button("Credits");
	private Button exit = new Button("Exit");

	// private Alert creditsDialog = new Alert(AlertType.INFORMATION);

	public MenuOption() { // try tilepane
		super();
		super.setRight(super.btn);
		// this.setStyle("-fx-background-color: black;");
//		root.setGridLinesVisible(true);

		this.logo = new ImageView(SceneLoading.LOGO.getImage());
		logo.setPreserveRatio(true);
		logo.setFitHeight(TOP_HEIGHT);
		logo.setFitWidth(TOP_WIDTH);

		this.setupGrid();

		// actions
		View view = ViewImpl.getView();
		this.newSim.setOnAction(e -> view.notifyObservers(SimEvent.NEWSIM));
		this.save.setOnAction(e -> view.notifyObservers(SimEvent.SAVE));
		this.load.setOnAction(e -> view.notifyObservers(SimEvent.LOAD));
		this.exit.setOnAction(e -> view.notifyObservers(SimEvent.EXIT));

	}

	public void showContent() {
		this.setCenter(root);
	}

	private void setupGrid() {
		Insets in = new Insets(BORDER, BORDER, BORDER, BORDER);
		root.setPadding(in);
		root.setVgap(VGAP);
		root.setHgap(HGAP);

		/*Setting up each node*/
		List<Node> nodes = Arrays.asList(this.logo, this.newSim, this.save, this.load, this.credits, this.exit);
		Arrays.asList(this.newSim, this.save, this.load, this.credits, this.exit).forEach(i -> {
			i.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			GridPane.setHalignment(i, HPos.CENTER);
		});
		for (int r = 0, i = 0; i < nodes.size(); r++, i++) {
			GridPane.setConstraints(nodes.get(i), 0, r);
			if(r != 0 && ++i < nodes.size()) {
				GridPane.setConstraints(nodes.get(i), 1, r);
			}
		}
		GridPane.setColumnSpan(this.logo, 2);

		//Creates 2 columns with equal width
		ColumnConstraints col = new ColumnConstraints();
		col.setPercentWidth(50);
		root.getColumnConstraints().addAll(col);
		
		root.getChildren().addAll(this.logo, this.newSim, this.save, this.load, this.credits, this.exit);
	}

}