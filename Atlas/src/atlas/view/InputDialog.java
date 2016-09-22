package atlas.view;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class just provides two types of pop-up windows: save and load.
 * 
 * @author MaXX
 *
 */
public class InputDialog {

	/**
	 * When this method is called a window pops out, asking the user to input a
	 * string. Once the user confirms, it returns the input to the caller.
	 * 
	 * @param owner
	 *            the stage that calls this method
	 * @param defaultName
	 *            the save file default name
	 * @return the designated save name by the user
	 */
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

	/**
	 * When this method is called a window pops out, which contains a series of
	 * tabs with the relative list of files available for selection. Once the
	 * user confirms, it returns the selected file.
	 * 
	 * @param owner
	 *            the stage that calls this method
	 * @param title
	 *            window title
	 * @param action
	 *            the text on the confirm button
	 * @param files
	 *            the files to load from
	 * @return the selected file
	 */
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
}