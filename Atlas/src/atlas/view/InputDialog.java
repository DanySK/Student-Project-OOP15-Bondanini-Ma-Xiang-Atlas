package atlas.view;

import java.io.File;
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
import javafx.stage.Window;

public class InputDialog extends Application {    

    public static Optional<String> getSaveName(Stage owner, String defaultName) {
        TextInputDialog input = new TextInputDialog(defaultName);
        input.initOwner(owner);
        input.setTitle("Save ");
        input.setContentText("File name");
        input.initStyle(StageStyle.UTILITY);
        input.setHeaderText(null);
        input.setGraphic(null);
        Optional<String> result = input.showAndWait();
        return result.isPresent() ? Optional.ofNullable(result.get().trim()) : Optional.empty();
    }

    public static Optional<File> loadFile(Stage owner, String title, String action, Map<File, List<File>> files) {
        Dialog<String> d = new Dialog<>();
        d.initOwner(owner);
        d.setTitle(title);
        ButtonType btn = new ButtonType(action, ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().addAll(btn, ButtonType.CANCEL);

        InputFilePane pane = new InputFilePane(files);
        d.getDialogPane().setContent(pane);

        d.setResultConverter(b -> {
            return b.equals(btn) && pane.getSelectedPath().isPresent() ? pane.getSelectedPath().get() : null;
        });
        Optional<String> result = d.showAndWait();
        return result.isPresent() ? Optional.ofNullable(new File(result.get())) : Optional.empty();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        /* Test LOAD */
//        btn.setText("Load");
//        btn.setOnAction(e -> {
//            Map<String, List<String>> map = new HashMap<>();
//            map.put("Presets", Arrays.asList("Solar System", "Alpha centauri", "Andromeda"));
//            map.put("Custom", Arrays.asList("Save #1", "Save #2", "Save #3"));
//            Optional<File> result = loadBody(LOAD_DIR);
//            System.out.println(result);
//        });

        /* Test SAVE */
        // btn.setText("Save");
        // btn.setOnAction(e -> {
        // Optional<String> result = getSaveName("Save " + new Date());
        // System.out.println(result);
        // });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}