package atlas.view;

import java.util.List;
import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class InputFilePane extends TabPane{
	
	private String selected;
	
	public InputFilePane(Map<String, List<String>> files) {
		files.entrySet().forEach(i -> {
			Tab tab = new Tab(i.getKey());
			VBox content = new VBox();
			i.getValue().forEach(j -> {
				Button btn = new Button(j);
				btn.setOnAction(e -> selected = j);
				content.getChildren().add(btn);
			});
			tab.setContent(content);
			this.getTabs().add(tab);
		});		
		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	}
	
	public String getSelected() {
		return selected;
	}
}