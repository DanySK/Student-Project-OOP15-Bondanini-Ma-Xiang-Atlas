package atlas.view;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class LoadScreen extends TabPane{

	private Tab presets;
	private Tab custom;
	
	public LoadScreen() {
		this.presets = new Tab();
		this.custom = new Tab();
		this.getTabs().addAll(presets, custom);
	}
	
	public void update(List<String> presets, List<String> custom) {
		VBox pContent = new VBox();
		presets.forEach( i -> {
			Button b = new Button(i);
			pContent.getChildren().add(b);
		});
		
		VBox cContent = new VBox();
		custom.forEach( i -> {
			Button b = new Button(i);
			cContent.getChildren().add(b);
		});
		
		this.presets.setContent(pContent);
		this.custom.setContent(cContent);
	}
}
