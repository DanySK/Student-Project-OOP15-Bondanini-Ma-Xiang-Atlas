package atlas.view;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MenuOption extends MenuHidable {

	private static final int BORDER = 10;
	private static final int VGAP = 15;
	private static final int HGAP = 15;
	
	private GridPane root = new GridPane();
	private Button newSim = new Button("New");
	private Button save = new Button("Save");
	private Button load = new Button("Load");
	private Button credits = new Button("Credits");
	private Button exit = new Button("Exit");
	
//	private Alert creditsDialog = new Alert(AlertType.INFORMATION);

	public MenuOption() { //try tilepane
		super();
		super.setRight(super.btn);

		Insets in = new Insets(BORDER, BORDER, BORDER, BORDER);
		root.setPadding(in);
		root.setVgap(VGAP);
		root.setHgap(HGAP);

		GridPane.setConstraints(SceneLoading.LOGO, 0, 6);

		GridPane.setConstraints(this.newSim, 0, 1);

		GridPane.setConstraints(this.save, 0, 2);

		GridPane.setConstraints(this.load, 0, 3);

		GridPane.setConstraints(this.credits, 0, 4);

		GridPane.setConstraints(this.exit, 0, 5);

		root.getChildren().addAll(SceneLoading.LOGO, this.newSim, this.save, this.load, this.credits, this.exit);
		
		this.setStyle("-fx-background-color: grey;");
		
		//actions
		View view = ViewImpl.getView();
		this.newSim.setOnAction(e -> view.notifyObservers(SimEvent.NEWSIM));
		this.save.setOnAction(e -> view.notifyObservers(SimEvent.SAVE));
		this.load.setOnAction(e -> view.notifyObservers(SimEvent.LOAD));
		this.exit.setOnAction(e -> view.notifyObservers(SimEvent.EXIT));
		
	}
	
	public void showContent() {
		this.setCenter(root);
	}

}