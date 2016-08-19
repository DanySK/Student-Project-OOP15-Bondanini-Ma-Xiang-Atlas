package atlas.view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InputDialog extends Application {

	public static Optional<String> getSaveName(String defaultName) {
		TextInputDialog input = new TextInputDialog(defaultName);
		input.setTitle("Save configuration");
		input.setContentText("File name");
		input.initStyle(StageStyle.UTILITY);
		input.setHeaderText(null);
		input.setGraphic(null);
		return input.showAndWait();
	}

	// prototype
	public static Optional<String> getLoadName(Map<String, List<String>> files) {
		Dialog<String> d = new Dialog<>();

		d.setTitle("Load configuration");
		ButtonType loadBtn = new ButtonType("Load", ButtonData.OK_DONE);
		d.getDialogPane().getButtonTypes().addAll(loadBtn, ButtonType.CANCEL);

		LoadScreen ls = new LoadScreen(files);
		d.getDialogPane().setContent(ls);

		d.setResultConverter(b -> {
			return b.equals(loadBtn) ? ls.getSelected() : null;
		});
		return d.showAndWait();
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Hello World!");
		Button btn = new Button();
		/*Test LOAD*/
//		btn.setText("Load");
//		btn.setOnAction(e -> {
//			Map<String, List<String>> map = new HashMap<>();
//			map.put("Presets", Arrays.asList("Solar System", "Alpha centauri", "Andromeda"));
//			map.put("Custom", Arrays.asList("Save #1", "Save #2", "Save #3"));
//			Optional<String> result = getLoadName(map);
//			System.out.println(result);
//		});
		
//		/*Test SAVE*/
//		btn.setText("Save");
//		btn.setOnAction(e -> {
//			Optional<String> result = getSaveName("Save " + new Date());
//			System.out.println(result);
//		});

		StackPane root = new StackPane();
		root.getChildren().add(btn);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}