package atlas.view;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

public class InputDialog {
	
	public static Optional<String> getSaveName(String defaultName) {
		TextInputDialog input = new TextInputDialog(defaultName);
		input.setTitle("Save configuration");
		input.setContentText("File name");
		return input.showAndWait();
	}
	
	//prototype
	public static Optional<String> getLoadName(List<String> presets, List<String> user) {
		Dialog<String> d = new Dialog<>();
		
		d.setTitle("Load configuration");
		ButtonType loadBtn = new ButtonType("Load", ButtonData.OK_DONE);
		d.getDialogPane().getButtonTypes().addAll(loadBtn, ButtonType.CANCEL);
		
		LoadScreen ls = new LoadScreen();
		ls.update(presets, user);
		d.getDialogPane().setContent(ls);
		
		d.setResultConverter(b -> {
			return b == loadBtn ? "loaded" : "not loaded";
		});
		return d.showAndWait();
	}
} 