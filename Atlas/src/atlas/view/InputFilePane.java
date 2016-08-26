package atlas.view;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * Generic file tab pane, files are organized in different sections, separated by tabs.
 * @author maxx
 *
 */
public class InputFilePane extends TabPane{
	
	private Optional<String> selectedPath = Optional.empty();
//	private Map<String, List<File>> files = new HashMap<>();
	
	/**
	 * Creates a tab pane using a map, each tab represents a folder and its the content the files
	 * @param path the root path from which to search the files
	 */
	public InputFilePane(Map<File, List<File>> files) {
	    
        //creates the tabs and buttons
		files.entrySet().forEach(i -> {
			Tab tab = new Tab(i.getKey().getName());
			VBox content = new VBox();
			i.getValue().forEach(j -> {
				Button btn = new Button(j.getName());
				btn.setOnAction(e -> {
					selectedPath = Optional.ofNullable(j.getAbsolutePath());
					//Create lateral pane
				});
				content.getChildren().add(btn);
			});
			tab.setContent(content);
			this.getTabs().add(tab);
		});
		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	}
	
	public Optional<String> getSelectedPath() {
		return selectedPath;
	}
}